package eu.sequence.data.processors;

import com.comphenix.protocol.events.PacketEvent;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.utilities.LocationUtils;

public class MovementProcessor extends Processor {

    private double deltaX,deltaZ,deltaXZ,deltaY,lastX,lastY,lastZ;;
    private int airTicks,edgeBlockTicks;
    private boolean isNearBoat,isInLiquid,isInWeb,isOnClimbable,isAtTheEdgeOfABlock,onGround;

    private PlayerData data;

    public MovementProcessor(PlayerData data) {
        this.data = data;
    }


    @Override
    public void handleReceive(PacketEvent event) {

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
        double y = event.getPlayer().getLocation().getY();
        deltaY = (y - this.lastY);
        this.lastY = y;

        /**
         * Getting the Z one tick ago
         * And setting the deltaZ with the current and last Z
         **/
        double z = event.getPlayer().getLocation().getZ();
        deltaZ = (z - this.lastZ);
        this.lastZ = z;

        deltaXZ = (Math.hypot(deltaX,deltaZ));


        /** Getting since how many ticks player is in air **/

        if(LocationUtils.isCloseToGround(event.getPlayer().getLocation())) {
            airTicks = 0;
        }else airTicks++;

        if(LocationUtils.isAtEdgeOfABlock(event.getPlayer())) {
            edgeBlockTicks++;
        }else edgeBlockTicks = 0;


        isNearBoat = LocationUtils.isNearBoat(event.getPlayer());
        isInLiquid = LocationUtils.isInLiquid(event.getPlayer());
        isInWeb = LocationUtils.isCollidingWithWeb(event.getPlayer());
        isOnClimbable = LocationUtils.isCollidingWithClimbable(event.getPlayer());
        isAtTheEdgeOfABlock = LocationUtils.isAtEdgeOfABlock(event.getPlayer());
        onGround = event.getPacket().getBooleans().read(0); //can be spoofed by the client



    }

    @Override
    public void handleSending(PacketEvent event) {

    }


    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public double getDeltaXZ() {
        return deltaXZ;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public double getLastZ() {
        return lastZ;
    }

    public int getAirTicks() {
        return airTicks;
    }

    public int getEdgeBlockTicks() {
        return edgeBlockTicks;
    }

    public boolean isNearBoat() {
        return isNearBoat;
    }

    public boolean isInLiquid() {
        return isInLiquid;
    }

    public boolean isInWeb() {
        return isInWeb;
    }

    public boolean isOnClimbable() {
        return isOnClimbable;
    }

    public boolean isAtTheEdgeOfABlock() {
        return isAtTheEdgeOfABlock;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
