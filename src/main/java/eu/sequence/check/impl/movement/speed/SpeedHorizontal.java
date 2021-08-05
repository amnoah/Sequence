package eu.sequence.check.impl.movement.speed;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.PlayerUtils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;

@CheckInfo(name = "Speed", subName = "Horizontal")
public class SpeedHorizontal extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled

    private float lastFriction, friction;
    private double lastDeltaXZ;
    private int ground, air,vl;

    public SpeedHorizontal(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isPosition()) {
            EntityPlayer player = ((CraftPlayer) playerData.getPlayer()).getHandle();

            // ground values
            boolean onGround = playerData.getMovementProcessor().isOnGround();

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
                prediction = lastDeltaXZ * 0.91f * getFriction(player, playerData.getPlayer().getLocation()) + landMovementFactor;
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
                this.friction = getFriction(player, playerData.getPlayer().getLocation()) * 0.91f;
            }

            if (friction < lastFriction)
                prediction += landMovementFactor * 1.25;

            //exempt
            final boolean exempt = isExempt(ExemptType.VELOCITY);

            // flag
            if (deltaXZ > prediction && !exempt) {
                if(this.vl++ > 3)
                flag();
            }else this.vl -= this.vl > 0 ? 0.025 : 0;

        }
    }

    private float getFriction(EntityPlayer player, Location location) {
        return player.world.getType(
                new BlockPosition(location.getX(),
                        NumberConversions.floor(location.getY()) - 1,
                        location.getZ())
        ).getBlock().frictionFactor;
    }
}
