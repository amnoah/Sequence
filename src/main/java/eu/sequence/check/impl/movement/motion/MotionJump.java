package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import eu.sequence.utilities.PlayerUtils;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Motion", subName = "Jump")
public class MotionJump extends Check {

    private boolean lastTickGround;
    private double lastDeltaX, lastDeltaZ;

    /**
     * @author Salers
     **/

    public MotionJump(PlayerData playerData) {
        super(playerData);
    }

    private float sin(float p_sin_0_) {
        return MathHelper.sin(p_sin_0_);
    }

    private float cos(float p_cos_0_) {
        return MathHelper.cos(p_cos_0_);
    }

    //skidded from mcp

    @Override
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isFlying()) {

                MovementProcessor movementProcessor = playerData.getMovementProcessor();
                RotationProcessor rotationProcessor = playerData.getRotationProcessor();

                boolean ground = event.getPacket().getBooleans().read(0);
                boolean lastTickGround = this.lastTickGround;
                this.lastTickGround = ground;

                double deltaX = movementProcessor.getDeltaX();
                double deltaY = movementProcessor.getDeltaY();
                double deltaZ = movementProcessor.getDeltaZ();

                double lastDeltaX = this.lastDeltaX;
                double lastDeltaZ = this.lastDeltaZ;

                this.lastDeltaX = deltaX;
                this.lastDeltaZ = deltaZ;

                double predictionY = 0.42F;
                predictionY += PlayerUtils.getPotionLevel(event.getPlayer(),
                        PotionEffectType.JUMP) * 0.1F;


                boolean exempt = movementProcessor.isOnClimbable() || movementProcessor.isInLiquid() ||
                        movementProcessor.isNearStairs() || movementProcessor.isNearSlabs();

                if (exempt) return;

                if(movementProcessor.getAirTicks() > 2 ) {
                    if(deltaY > predictionY) {
                        flag();
                    }
                }


                if (!ground && lastTickGround) {

                    //EntityLivingBase line 1556


                    if (playerData.getPlayer().isSprinting()) {


                        float f = (float) (rotationProcessor.getDeltaYaw() * 0.017453292F);
                        float friction = 0.91F;

                        double predictionX = (lastDeltaX * friction) - (double) (sin(f) * 0.2F);
                        double predictionZ = (lastDeltaZ * friction) + (double) (cos(f) * 0.2F);


                        double diffX = Math.abs(deltaX - predictionX);
                        double diffZ = Math.abs(deltaZ - predictionZ);

                        Bukkit.broadcastMessage("dX=" + diffX + " dZ=" + diffZ);

                        if (diffX > 0.01 && diffZ > 0.01) {
                            flag();
                        }

                    }
                }
            }

        }
    }



}
