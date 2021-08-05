package eu.sequence.check.impl.combat.reach;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.tracker.TrackedEntity;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.mc.*;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import org.bukkit.Bukkit;

@CheckInfo(name = "Reach", subName = "A", experimental = true)
public class ReachA extends Check {

    // DO NOT TOUCH

    public ReachA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {
            WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                int targetId = wrapper.getEntityId();

                if (!playerData.getEntityTracker().getTrackedEntities().containsKey(targetId)) {

                    Bukkit.broadcastMessage("return 1");
                    return;
                }

                TrackedEntity target = playerData.getEntityTracker().getTrackedEntities().get(targetId);

                if (target == null) {

                    Bukkit.broadcastMessage("return 2");
                    return;
                }

                AxisAlignedBB lastBoundingBox = target.getLastBoundingBox();
                AxisAlignedBB confirmedBoundingBox = target.getConfirmedBoundingBox();

                if (lastBoundingBox == null && confirmedBoundingBox == null) {

                    Bukkit.broadcastMessage("return 3");
                    return;
                }

                Vec3 origin = new Vec3(
                        playerData.getMovementProcessor().getX(),
                        playerData.getMovementProcessor().getY() + 1.62,
                        playerData.getMovementProcessor().getZ()
                );

                Vec3 look = getVectorForRotation(
                        playerData.getRotationProcessor().getPitch(),
                        playerData.getRotationProcessor().getYaw()
                );

                look = origin.addVector(
                        look.xCoord * 6.0,
                        look.yCoord * 6.0,
                        look.zCoord * 6.0
                );

                double distance = -6.9;

                if (target.isConfirmed()) {
                    if (confirmedBoundingBox != null) {
                        MovingObjectPosition collision = confirmedBoundingBox.calculateIntercept(origin, look);

                        distance = origin.distanceTo(collision.hitVec);
                    }
                } else {
                    double d1 = 99;
                    double d2 = 99;

                    if (lastBoundingBox != null) {
                        MovingObjectPosition collision = lastBoundingBox.calculateIntercept(origin, look);

                        d1 = origin.distanceTo(collision.hitVec);
                    }

                    if (confirmedBoundingBox != null) {
                        MovingObjectPosition collision = confirmedBoundingBox.calculateIntercept(origin, look);

                        d2 = origin.distanceTo(collision.hitVec);
                    }

                    if (d1 != 99 || d2 != 99) {
                        distance = Math.min(d1, d2);
                    }
                }

                if (distance != -6.9) {
                    Bukkit.broadcastMessage("distance=" + distance);
                } else {
                    Bukkit.broadcastMessage("collision=null");
                }
            }
        }
    }

    private Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3(f1 * f2, f3, f * f2);
    }
}
