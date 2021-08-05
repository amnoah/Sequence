package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
<<<<<<< Updated upstream
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
=======
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;
>>>>>>> Stashed changes

@CheckInfo(name = "Flight", subName = "AirJump")
public class FlightAirJump extends Check {

    private int vl;
    private double lastDeltaY;

    /**
     * @author Salers
     **/


    public FlightAirJump(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isFlying()) {

                final MovementProcessor movementProcessor = playerData.getMovementProcessor();

                final double deltaY = movementProcessor.getDeltaY();
                final double lastDeltaY = this.lastDeltaY;

<<<<<<< Updated upstream
                this.lastDeltaY = deltaY;

                boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                        movementProcessor.isInWeb() || movementProcessor.isOnClimbable();
=======
            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.NOTINAIR,ExemptType.SLIME,ExemptType.LIQUID,ExemptType.WEB);
>>>>>>> Stashed changes

                if (!exempt && deltaY > lastDeltaY) {
                    if (++this.vl > 3) {
                        flag();
                    }
                } else this.vl -= this.vl > 0 ? 0.05 : 0;

            }
        }
    }
}
