package eu.sequence.data.processors;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.LocationUtils;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

@Getter
@RequiredArgsConstructor
public class MovementProcessor extends Processor {

    private double x, y, z,
                deltaX, deltaY, deltaZ, deltaXZ,
                lastX, lastY, lastZ;

    private int airTicks, edgeBlockTicks;
    private boolean isNearBoat,isInLiquid,
            isInWeb, isOnClimbable,
            isAtTheEdgeOfABlock, onGround,
            isNearSlabs, isNearStairs;

    private final PlayerData data;

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getNmsPacket());
            /*
             * Getting the X one tick ago
             * And setting the deltaX with the current X and last X
             */
            this.lastX = this.x;

            /*
             * Getting the Y one tick ago
             * And setting the deltaY with the current and last Y
             */
            this.lastY = this.y;

            /*
             * Getting the Z one tick ago
             * And setting the deltaZ with the current and last Z
             */
            this.lastZ = this.z;

            // Update the positions

            updatePos(wrapper);

            // Deltas
            // We don't need to do abs deltas for pos deltas
            this.deltaX = this.x - this.lastX;
            this.deltaY = this.y - this.lastY;
            this.deltaZ = this.z - this.lastZ;

            // DeltaXZ

            this.deltaXZ = Math.hypot(deltaX, deltaZ);

            data.getPlayer().sendMessage("DXZ: " + deltaXZ);
            data.getPlayer().sendMessage("DY: " + deltaY);

            /* Getting since how many ticks player is in air */
            
            Location location = new Location(data.getPlayer().getWorld(), x, y, z);
            
            if (LocationUtils.isCloseToGround(location)) {
                this.airTicks = 0;
            } else this.airTicks++;

            if (LocationUtils.isAtEdgeOfABlock(location)) {
                this.edgeBlockTicks++;
            } else this.edgeBlockTicks = 0;


            this.isNearBoat = LocationUtils.isNearBoat(data.getPlayer());
            this. isInLiquid = LocationUtils.isInLiquid(location);
            this.isInWeb = LocationUtils.isCollidingWithWeb(location);
            this.isOnClimbable = LocationUtils.isCollidingWithClimbable(location);
            this.isAtTheEdgeOfABlock = edgeBlockTicks > 0; // We don't have to run this calculation again
            this.onGround = wrapper.isOnGround(); //can be spoofed by the client
            this.isNearSlabs = LocationUtils.isNearSlabs(location);
            this.isNearStairs = LocationUtils.isNearStairs(location);
        }
    }

    public void updatePos(WrappedPacketInFlying wrapper) {
        // We don't have to use PositionLook wrappers because the x, y, & z are in the same spot no matter what
        if (wrapper.isPosition()) {
            this.x = wrapper.getX();
            this.y = wrapper.getY();
            this.z = wrapper.getZ();
        }
    }
}
