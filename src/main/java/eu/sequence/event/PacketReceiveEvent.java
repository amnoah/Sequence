package eu.sequence.event;

import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;

public class PacketReceiveEvent extends PacketEvent {

    /**
     * @author Salers
     **/

    public PacketReceiveEvent(Player player, Packet packet) {
        super(player, packet);
    }
}
