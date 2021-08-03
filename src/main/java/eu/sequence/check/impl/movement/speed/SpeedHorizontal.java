package eu.sequence.check.impl.movement.speed;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.util.NumberConversions;

@CheckInfo(name = "Speed",subName = "Horizontal")
public class SpeedHorizontal extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled

    private double lastDeltaXZ;
    private boolean lastClientOnGround;

    public SpeedHorizontal(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isPosition()) {
                EntityPlayer player = ((CraftPlayer) playerData.getPlayer()).getHandle();

                Location lastLocation = new Location(
                        playerData.getPlayer().getWorld(),
                        playerData.getMovementProcessor().getLastX(),
                        NumberConversions.floor(playerData.getMovementProcessor().getLastY()) - 1,
                        playerData.getMovementProcessor().getLastZ()
                );

                float f4 = lastClientOnGround ? getFriction(player, lastLocation) * 0.91F : 0.91F;
                float f5 = lastClientOnGround ? player.bI() : player.aM;

                double max = lastDeltaXZ * f4 + f5;

                double deltaXZ = playerData.getMovementProcessor().getDeltaXZ();

                if (deltaXZ > max) {
                    flag();
                }

                lastDeltaXZ = deltaXZ;
                lastClientOnGround = playerData.getMovementProcessor().isOnGround();
            }
        }
    }

    private float getFriction(EntityPlayer player, Location location) {
        return player.world.getType(
                new BlockPosition(location.getX(), location.getY(), location.getZ())
        ).getBlock().frictionFactor;
    }
}
