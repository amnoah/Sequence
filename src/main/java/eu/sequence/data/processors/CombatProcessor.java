package eu.sequence.data.processors;

import com.comphenix.packetwrapper.WrapperPlayServerRelEntityMove;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.EvictingList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;


@Data
@RequiredArgsConstructor
public class CombatProcessor extends Processor {

    private final PlayerData data;
    private long lastAttackTime;
    private Entity victim;
    private EvictingList pastTargetLocations = new EvictingList<Location>(20);





    @Override
    public void handleReceive(PacketEvent event) {
        Packet packet = event.getPacket();
        if(packet.isAttack()) {

            this.victim = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
            this.lastAttackTime = System.currentTimeMillis();

        }

    }

    @Override
    public void handleSending(PacketEvent event) {
        Packet packet = event.getPacket();
        if(packet.isRelEntityMove()) {
            if(this.victim != null) {

                WrapperPlayServerRelEntityMove wrappedPacket = new WrapperPlayServerRelEntityMove(packet);

                if(victim.getEntityId() == wrappedPacket.getEntityID()) {
                    pastTargetLocations.add(victim.getLocation());


                }
            }
        }

    }
}
