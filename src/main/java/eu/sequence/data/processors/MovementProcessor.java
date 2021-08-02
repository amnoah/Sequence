package eu.sequence.data.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.utilities.LocationUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MovementProcessor extends Processor {

    private double deltaX,deltaZ,deltaXZ,deltaY,lastX,lastY,lastZ,y;
    private int airTicks,edgeBlockTicks;
    private boolean isNearBoat,isInLiquid,isInWeb,isOnClimbable,isAtTheEdgeOfABlock,onGround;

    private final PlayerData data;

    @Override
    public void handleReceive(PacketEvent event) {

        if(event.getPacketType() == PacketType.Play.Client.POSITION_LOOK || event.getPacketType() ==
                PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client.LOOK) {

            /**
             * Getting the X one tick ago
             * And setting the deltaX with the current X and last X
             **/
            double x = event.getPlayer().getLocation().getX();
            deltaX = (x - this.lastX);
            this.lastX = x;

            /**
             * Getting the Y one tick ago
             * And setting the deltaY with the current and last Y
             **/
            y = event.getPlayer().getLocation().getY();
            deltaY = (y - this.lastY);
            this.lastY = y;

            /**
             * Getting the Z one tick ago
             * And setting the deltaZ with the current and last Z
             **/
            double z = event.getPlayer().getLocation().getZ();
            deltaZ = (z - this.lastZ);
            this.lastZ = z;

            deltaXZ = (Math.hypot(deltaX, deltaZ));


            /** Getting since how many ticks player is in air **/

            if (LocationUtils.isCloseToGround(event.getPlayer().getLocation())) {
                airTicks = 0;
            } else airTicks++;

            if (LocationUtils.isAtEdgeOfABlock(event.getPlayer())) {
                edgeBlockTicks++;
            } else edgeBlockTicks = 0;


            isNearBoat = LocationUtils.isNearBoat(event.getPlayer());
            isInLiquid = LocationUtils.isInLiquid(event.getPlayer());
            isInWeb = LocationUtils.isCollidingWithWeb(event.getPlayer());
            isOnClimbable = LocationUtils.isCollidingWithClimbable(event.getPlayer());
            isAtTheEdgeOfABlock = LocationUtils.isAtEdgeOfABlock(event.getPlayer());
            onGround = event.getPacket().getBooleans().read(0); //can be spoofed by the client
        }
    }

    @Override
    public void handleSending(PacketEvent event) {

    }
}
