package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;
import org.bukkit.Bukkit;

@CheckInfo(name = "Flight",subName = "Velocity")
public class FlightVelocity extends Check {


    public FlightVelocity(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaY = movementProcessor.getDeltaY();
            final double velocityY = playerData.getPlayer().getVelocity().getY();

            final double result = deltaY - velocityY;

            final boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                    movementProcessor.isInWeb() || movementProcessor.isOnClimbable() || movementProcessor.
                    isAtTheEdgeOfABlock() || movementProcessor.isOnGround() || playerData.getPlayer().getFallDistance() > 0.8;

            if(!exempt) {
                if(result < - 0.5D || result > 1.0D) {
                    flag("result=" + result);
                }
            }
        }
    }
}
