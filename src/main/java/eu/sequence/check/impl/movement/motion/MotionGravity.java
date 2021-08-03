package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;

// Call this fly coz it is a fly check

@CheckInfo(name = "Motion",subName = "Gravity",experimental = true)
public class MotionGravity extends Check {

    private double lastDeltaY;
    private int preVL;

    /**
     * @author Salers
     **/

    public MotionGravity(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {

            MovementProcessor movementProcessor = playerData.getMovementProcessor();

            double deltaY = movementProcessor.getDeltaY();
            double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;
            double predictionY = (lastDeltaY - 0.08) * 0.9800000190734863D; //EntityLivingBase lines 1666 and 1669
            if (Math.abs(predictionY) < 0.005) {
                predictionY = 0;
            }

            double difference = Math.abs(deltaY - predictionY);

            boolean exempt = movementProcessor.getAirTicks() < 15 || movementProcessor.isInLiquid() ||
                    movementProcessor.isInWeb() || movementProcessor.isOnClimbable() || movementProcessor.isOnGround();

            if (!exempt && difference > 0.01) {
                if (++this.preVL > 2) {
                    flag();
                }

            } else this.preVL -= this.preVL > 0 ? 0.5 : 0;
        }
    }
}
