package eu.sequence.event;

import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;

public class PacketEvent {
    private final Player player;
    private final Packet packet;

    public PacketEvent(Player player, Packet packet) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Packet getPacket()
    {
        return packet;
    }
}
