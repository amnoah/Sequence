package eu.sequence.listener;

import eu.sequence.Sequence;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;

public class PacketEventsListener extends PacketListenerAbstract {

    public PacketEventsListener() {
        super(PacketListenerPriority.LOWEST);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(event.getPlayer());

        if (data != null) {
            Packet packet = new Packet(event.getPacketId(), event.getNMSPacket(), true);

            data.handle(event, packet);
        }
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent event) {
        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(event.getPlayer());

        if (data != null) {
            Packet packet = new Packet(event.getPacketId(), event.getNMSPacket(), true);

            data.handle(event, packet);
        }
    }
}
