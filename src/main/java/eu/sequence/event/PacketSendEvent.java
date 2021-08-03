package eu.sequence.event;

import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;

public class PacketSendEvent extends PacketEvent {

    /**
     * @author Salers
     **/

    public PacketSendEvent(Player player, Packet packet) {
        super(player, packet);
    }
}
