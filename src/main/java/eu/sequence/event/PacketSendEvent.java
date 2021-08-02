package eu.sequence.event;

import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;

public class PacketSendEvent extends PacketEvent {

    public PacketSendEvent(Player player, Packet packet) {
        super(player, packet);
    }
}
