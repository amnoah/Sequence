package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.exempt.ExemptType;

import eu.sequence.packet.Packet;
import net.minecraft.server.v1_8_R3.EntityPlayer;

// Call this fly coz it is a fly check

@CheckInfo(name = "Motion",subName = "Gravity",experimental = true,configPath = "motion.gravity")
public class MotionGravity extends Check {

    private double lastDeltaY;


    @Override
    public void addExtraConfigValues(String name, Object object) {
        super.addExtraConfigValues("precision", "0.01");
    }

    /**
     * @author Salers
     **/



    public MotionGravity(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;
            double predictionY = (lastDeltaY - 0.08) * 0.9800000190734863D; //EntityLivingBase lines 1666 and 1669
            if (Math.abs(predictionY) < 0.005) {
                predictionY = 0;
            }

            final double difference = Math.abs(deltaY - predictionY);

            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.WEB,ExemptType.LIQUID,ExemptType.NOTINAIR);


            if (!exempt && difference > 0.01) {
                if (++this.preVL > 2) {
                    flag("diff=" + difference);
                }

            } else this.preVL -= this.preVL > 0 ? 0.5 : 0;
        }
    }
}
