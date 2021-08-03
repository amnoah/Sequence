package eu.sequence.data.processors;

import com.comphenix.packetwrapper.WrapperPlayClientFlying;
import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.packetwrapper.WrapperPlayClientPositionLook;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.event.PacketEvent;
import eu.sequence.utilities.LocationUtils;
import eu.sequence.utilities.MathUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class MovementProcessor extends Processor {

    private double deltaX, deltaZ, deltaXZ, deltaY;
    private double lastX, lastY, lastZ;
    private double x = 0, y = 0, z = 0;
    private int airTicks,edgeBlockTicks;
    private boolean isNearBoat,isInLiquid,isInWeb,isOnClimbable,isAtTheEdgeOfABlock,onGround,isNearSlabs,isNearStairs;

    private final PlayerData data;

    @Override
    public void handleReceive(PacketEvent event) {
        if (event.getPacket().isPosition() || event.getPacket().isPosLook()) {

            Player player = event.getPlayer();

            /*
             * Getting the X one tick ago
             * And setting the deltaX with the current X and last X
             **/
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

            updatePos(event);

            // Deltas

            this.deltaX = ( this.x - this.lastX );
            this.deltaY = ( this.y - this.lastY );
            this.deltaZ = ( this.z - this.lastZ );

            // DeltaXZ

            this.deltaXZ = MathUtils.hypot( deltaX, deltaZ );

            player.sendMessage("DXZ: " + deltaXZ);
            player.sendMessage("DY: " + deltaY);

            /* Getting since how many ticks player is in air **/

            if (LocationUtils.isCloseToGround(player.getLocation())) {
                this.airTicks = 0;
            } else this.airTicks++;

            if (LocationUtils.isAtEdgeOfABlock(player)) {
                this.edgeBlockTicks++;
            } else this.edgeBlockTicks = 0;


            this.isNearBoat = LocationUtils.isNearBoat(player);
            this.isInLiquid = LocationUtils.isInLiquid(player);
            this.isInWeb = LocationUtils.isCollidingWithWeb(player);
            this.isOnClimbable = LocationUtils.isCollidingWithClimbable(player);
            this.isAtTheEdgeOfABlock = LocationUtils.isAtEdgeOfABlock(player);
            this.onGround = event.getPacket().getBooleans().read(0); //can be spoofed by the client
            this.isNearSlabs = LocationUtils.isNearSlabs(player);
            this.isNearStairs = LocationUtils.isNearStairs(player);


        }
    }

    @Override
    public void handleSending(PacketEvent event) {

    }

    public void updatePos(PacketEvent packet) {
        if (packet.getPacket().isPosition()) {
            WrapperPlayClientPosition wrapper = new WrapperPlayClientPosition(packet.getPacket());
            this.x = wrapper.getX();
            this.y = wrapper.getY();
            this.z = wrapper.getZ();
        } else if (packet.getPacket().isPosLook()) {
            WrapperPlayClientPositionLook wrapper = new WrapperPlayClientPositionLook(packet.getPacket());
            this.x = wrapper.getX();
            this.y = wrapper.getY();
            this.z = wrapper.getZ();
        }
    }
}
