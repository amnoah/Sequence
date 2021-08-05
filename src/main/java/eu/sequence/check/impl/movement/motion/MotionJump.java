package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
<<<<<<< Updated upstream
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
=======
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
>>>>>>> Stashed changes
import eu.sequence.utilities.PlayerUtils;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Motion", subName = "Jump")
public class MotionJump extends Check {

<<<<<<< Updated upstream
    private boolean lastTickGround;
    private double lastDeltaX, lastDeltaZ;
=======
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
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
=======
    public void handle(Packet packet) {
        if (packet.isPosition()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();


            double deltaY = movementProcessor.getDeltaY();
>>>>>>> Stashed changes

                this.lastDeltaX = deltaX;
                this.lastDeltaZ = deltaZ;

<<<<<<< Updated upstream
                double predictionY = 0.42F;
                predictionY += PlayerUtils.getPotionLevel(event.getPlayer(),
                        PotionEffectType.JUMP) * 0.1F;

=======
            final double predictionY = 0.42F + (double) ((float) (PlayerUtils.getPotionLevel(playerData.getPlayer(),
                    PotionEffectType.JUMP) + 1) * 0.1F);

            final boolean invalidY = deltaY > predictionY;
>>>>>>> Stashed changes

            final boolean exempt = isExempt(ExemptType.CLIMBABLE, ExemptType.NOTINAIR, ExemptType.SLIME, ExemptType.LIQUID, ExemptType.WEB);

            if (exempt) return;

<<<<<<< Updated upstream
                if(movementProcessor.getAirTicks() > 2 ) {
                    if(deltaY > predictionY) {
                        flag();
                    }
=======
            if (movementProcessor.getAirTicks() > 1) {
                if (invalidY) {
                    flag();
>>>>>>> Stashed changes
                }
            }


<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes
        }
    }


}
