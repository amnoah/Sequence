package eu.sequence.data.processors;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import io.github.retrooper.packetevents.utils.vector.Vector3d;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@Getter
@RequiredArgsConstructor
public class CombatProcessor extends Processor {

    private final PlayerData data;
    private long lastAttackTime;
    private Entity victim;


    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {

            final WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {

                this.victim = wrapper.getEntity();
                this.lastAttackTime = System.currentTimeMillis();
            }


        }
    }
}
