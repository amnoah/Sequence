package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
import eu.sequence.utilities.PlayerUtils;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Motion", subName = "Jump")
public class MotionJump extends Check {



    /**
     * @author Salers
     **/

    public MotionJump(PlayerData playerData) {
        super(playerData);
    }

    private float sin(float p_sin_0_) {
        return MathUtils.sin(p_sin_0_);
    }

    private float cos(float p_cos_0_) {
        return MathUtils.cos(p_cos_0_);
    }

    //skidded from mcp

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            MovementProcessor movementProcessor = playerData.getMovementProcessor();


            double deltaX = movementProcessor.getDeltaX();
            double deltaY = movementProcessor.getDeltaY();
            double deltaZ = movementProcessor.getDeltaZ();



            double predictionY = 0.42F + (double) ((float) (PlayerUtils.getPotionLevel(playerData.getPlayer(),
                    PotionEffectType.JUMP) + 1) * 0.1F);

                boolean invalidY = deltaY > predictionY;

                boolean exempt = movementProcessor.isOnClimbable() || movementProcessor.isInLiquid() ||
                        movementProcessor.isNearStairs() || movementProcessor.isNearSlabs();

                if (exempt) return;

                if(movementProcessor.getAirTicks() > 1) {
                    if(invalidY) {
                        flag("deltaY=" + deltaY + " max=" + predictionY);
                    }
                }



            }
    }



}
