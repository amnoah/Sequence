package eu.sequence.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import eu.sequence.Sequence;
import eu.sequence.SequencePlugin;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PacketListener implements Listener {
    public PacketListener(final SequencePlugin plugin)
    {
        for (PacketType packetType: PacketType.values()) {
            if (packetType.isSupported()) {
                if (packetType.isClient()) {
                    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, packetType) {
                        @Override
                        public void onPacketReceiving(PacketEvent e)
                        {
                            onPacketReceive(e.getPlayer(), e.getPacket());
                        }
                    });
                }
            }
        }
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.POSITION) {
            @Override
            public void onPacketSending(PacketEvent e)
            {
                onPacketSend(e.getPlayer(), e.getPacket());
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_VELOCITY) {
            @Override
            public void onPacketSending(PacketEvent e)
            {
                onPacketSend(e.getPlayer(), e.getPacket());
            }
        });
    }

    public void onPacketReceive(Player player, PacketContainer packet)
    {
        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(player);
        if (data == null)
            return;
        data.handle(new eu.sequence.event.PacketEvent(player, new Packet(packet)));
    }

    public void onPacketSend(Player player, PacketContainer packet)
    {
        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(player);
        if (data == null)
            return;
        data.handle(new eu.sequence.event.PacketEvent(player, new Packet(packet)));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Sequence.getInstance().getPlayerDataManager().add(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerJoinEvent e)
    {
        Sequence.getInstance().getPlayerDataManager().remove(e.getPlayer());
    }
}
