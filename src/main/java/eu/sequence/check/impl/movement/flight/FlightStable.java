package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;


@CheckInfo(name = "Flight", subName = "Stable")
public class FlightStable extends Check {

    //dumbass check but efficient

    /**
     * @author Salers
     **/

    private double lastDeltaY;
    private int vl;

    public FlightStable(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {

            MovementProcessor movementProcessor = playerData.getMovementProcessor();
            int streakY = 0, streakDeltaY = 0;

            boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                    movementProcessor.isInWeb() || movementProcessor.isOnClimbable();
            if (exempt) return;

            if (movementProcessor.getLastY() == movementProcessor.getY()) {
                streakY++;

            } else streakY = 0;

            double deltaY = movementProcessor.getDeltaY();
            double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;

            if (deltaY == lastDeltaY) {
                streakDeltaY++;
            } else streakDeltaY = 0;

            if (streakDeltaY > 6 || streakY > 11) {
                if (++vl > 4) {
                    flag();
                }
            } else vl -= vl > 0 ? 0.05 : 0;
        }
    }
}
