package eu.sequence.data.processors;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.play.out.entityvelocity.WrappedPacketOutEntityVelocity;
import io.github.retrooper.packetevents.utils.vector.Vector3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Getter
@RequiredArgsConstructor
public class VelocityProcessor extends Processor {

    private final PlayerData data;
    private double x,y,z,lastX,lastY,lastZ;
    private Vector3d velocityVector;
    private int ticks,maxTicks;
    private long lastVelocityTimeBukkit;

    @Override
    public void handle(Packet packet) {
        if(packet.isVelocity()) {

            final WrappedPacketOutEntityVelocity wrappedPacket = new WrappedPacketOutEntityVelocity(packet.getNmsPacket());

            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;

            this.x = wrappedPacket.getVelocityX();
            this.y = wrappedPacket.getVelocityY();
            this.z = wrappedPacket.getVelocityZ();

            this.velocityVector = wrappedPacket.getVelocity();

            final int ping = PacketEvents.get().getPlayerUtils().getPing(this.data.getPlayer());

            this.maxTicks = (ping / 50) + 5; //skidded from jonhan omg lol

            this.ticks = 0;

        }else if(packet.isFlying()) {
            this.ticks++;
        }
    }

    public void handleEDBE(EntityDamageByEntityEvent event) {
        if(this.data.getPlayer() == event.getEntity()) {
            this.lastVelocityTimeBukkit = System.currentTimeMillis();
        }
     }
}
