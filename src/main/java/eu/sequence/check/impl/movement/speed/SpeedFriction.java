package eu.sequence.check.impl.movement.speed;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import eu.sequence.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Speed",subName = "Friction")
public class SpeedFriction extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled


    private double lastDeltaXZ;
    private boolean wasOnGround;




    public SpeedFriction(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            if(event.getPacket().isFlying()) {

                final MovementProcessor movementProcessor = playerData.getMovementProcessor();

                final boolean onGround = movementProcessor.isOnGround();
                final boolean wasOnGround = this.wasOnGround;

                final double deltaXZ = movementProcessor.getDeltaXZ();
                final double lastDeltaXZ = this.lastDeltaXZ;

                this.wasOnGround = onGround;
                this.lastDeltaXZ = deltaXZ;

                final float friction = getBlockFriction() * 0.91F;
                final double prediction = lastDeltaXZ * friction;

                final double result = Math.abs(deltaXZ - prediction);
               




                if(!wasOnGround && !onGround) {
                    Bukkit.broadcastMessage("sR=" + result);

                }


            }
        }
    }

    public float getBlockFriction() {
        String block = playerData.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase();
        return block.equals("blue ice") ? 0.989f : block.contains("ice") ? 0.98f : block.equals("slime") ? 0.8f : 0.6f;
    }
}
