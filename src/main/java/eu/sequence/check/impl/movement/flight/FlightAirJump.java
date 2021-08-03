package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;

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
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;

            boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                    movementProcessor.isInWeb() || movementProcessor.isOnClimbable();

            if (!exempt && deltaY > lastDeltaY) {
                if (++this.vl > 3) {
                    flag();
                }
            } else this.vl -= this.vl > 0 ? 0.05 : 0;
        }
    }
}
