package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;


import eu.sequence.exempt.ExemptType;

import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
import eu.sequence.utilities.PlayerUtils;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Motion", subName = "Jump",configPath = "motion.jump")
public class MotionJump extends Check {


    /**
     * @author Salers
     **/

    public MotionJump(PlayerData playerData) {
        super(playerData);
    }

    //skidded from mcp

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            MovementProcessor movementProcessor = playerData.getMovementProcessor();


            double deltaY = movementProcessor.getDeltaY();


            double predictionY = 0.42F + (double) ((float) (PlayerUtils.getPotionLevel(playerData.getPlayer(),
                    PotionEffectType.JUMP)) * 0.1F);


            boolean invalidY = deltaY > predictionY;

            final boolean exempt = isExempt(ExemptType.CLIMBABLE, ExemptType.WEB, ExemptType.SLIME);

            if (movementProcessor.getAirTicks() > 1 && !exempt) {
                if (invalidY) {
                    flag("deltaY=" + deltaY + " max=" + predictionY);
                }
            }


        }


    }

}




