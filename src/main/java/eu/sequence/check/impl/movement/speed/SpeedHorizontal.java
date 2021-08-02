package eu.sequence.check.impl.movement.speed;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import eu.sequence.utilities.PlayerUtils;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Speed",subName = "Horizontal")
public class SpeedHorizontal extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled

    private float lastFriction = 0, friction = 0;
    private double lastDeltaXZ;
    private boolean wasOnGround;
    private int ground = 0, air = 0;

    public SpeedHorizontal(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            if(event.getPacket().isFlying()) {
                // ground values
                boolean onGround = event.getPacket().getBooleans().read(0);
                boolean wasOnGround = this.wasOnGround;
                this.wasOnGround = onGround;

                air = onGround ? 0 : Math.min(air + 1, 20);
                ground = onGround ? Math.min(ground + 1, 20) : 0;

                // deltas
                double deltaXZ = playerData.getMovementProcessor().getDeltaXZ();
                double lastDeltaXZ = this.lastDeltaXZ;
                this.lastDeltaXZ = deltaXZ;

                // landMovementFactor
                float speed = PlayerUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.SPEED);
                float slow = PlayerUtils.getPotionLevel(playerData.getPlayer(), PotionEffectType.SLOW);
                double d = 0.10000000149011612;
                d += d * 0.20000000298023224 * speed;
                d += d * -0.15000000596046448 * slow;

                // Sprint desync big gay just assume they are sprinting
                d += d * 0.30000001192092896;

                float landMovementFactor = (float) d;

                // the check itself
                double prediction;
                if (ground > 2) {
                    prediction = lastDeltaXZ * 0.91f * getBlockFriction() + landMovementFactor;
                } else if (air == 1) {
                    prediction = lastDeltaXZ * 0.91f + 0.2f + landMovementFactor;
                } else if (ground == 2) {
                    prediction = lastDeltaXZ * 0.91f + landMovementFactor;
                } else {
                    prediction = lastDeltaXZ * 0.91f + 0.026f;
                }
                if (prediction < playerData.getPlayer().getWalkSpeed() + 0.02 * (speed + 1))
                    prediction = playerData.getPlayer().getWalkSpeed() + 0.02 * (speed + 1);

                // very lazy patch for a false flag
                if (ground > 1) {
                    this.lastFriction = this.friction;
                    this.friction = getBlockFriction() * 0.91f;
                }

                if (friction < lastFriction)
                    prediction += landMovementFactor * 1.25;

                // flag
                if (deltaXZ > prediction) {
                    flag();
                }

            }
        }
    }

    public float getBlockFriction() {
        String block = playerData.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase();
        return block.equals("blue ice") ? 0.989f : block.contains("ice") ? 0.98f : block.equals("slime") ? 0.8f : 0.6f;
    }
}
