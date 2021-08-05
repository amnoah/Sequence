package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
<<<<<<< Updated upstream
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import org.bukkit.Bukkit;
=======
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;
>>>>>>> Stashed changes

@CheckInfo(name = "Motion", subName = "Gravity", experimental = true)
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
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isFlying()) {

<<<<<<< Updated upstream
                MovementProcessor movementProcessor = playerData.getMovementProcessor();

                double deltaY = movementProcessor.getDeltaY();
                double lastDeltaY = this.lastDeltaY;

                this.lastDeltaY = deltaY;
                double predictionY = (lastDeltaY - 0.08) * 0.9800000190734863D; //EntityLivingBase lines 1666 and 1669

                double difference = Math.abs(deltaY - predictionY);

                boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                        movementProcessor.isInWeb() || movementProcessor.isOnClimbable() || Math.abs(predictionY) < 0.05
                        || playerData.getPlayer().getFallDistance() > 12 || movementProcessor.isOnGround();

=======
            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;

            double predictionY = (lastDeltaY - 0.08) * 0.9800000190734863D; //EntityLivingBase lines 1666 and 1669

            if (Math.abs(predictionY) < 0.005) {
                predictionY = 0;
            }

            final double difference = Math.abs(deltaY - predictionY);

            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.NOTINAIR,ExemptType.SLIME,ExemptType.LIQUID,ExemptType.WEB);
>>>>>>> Stashed changes

                if (!exempt && difference > 0.01) {
                    if (++this.preVL > 2) {
                        flag();
                    }

                } else this.preVL -= this.preVL > 0 ? 0.05 : 0;


            }
        }
    }
}
