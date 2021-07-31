package eu.sequence.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import eu.sequence.SequencePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {
    public PacketListener(final SequencePlugin plugin) {
        for (PacketType packetType: PacketType.values()) {
            if (packetType.isSupported()) {
                if (packetType.isClient()) {
                    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, packetType) {
                        @Override
                        public void onPacketReceiving(PacketEvent e) {
                            onPacketReceive(e.getPlayer(), e.getPacket());
                        }
                    });
                }
            }
        }
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.POSITION) {
            @Override
            public void onPacketSending(PacketEvent e) {
                onPacketSend(e.getPlayer(), e.getPacket());
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_VELOCITY) {
            @Override
            public void onPacketSending(PacketEvent e) {
                onPacketSend(e.getPlayer(), e.getPacket());
            }
        });
    }

    public void onPacketReceive(Player player, PacketContainer packet) {

    }

    public void onPacketSend(Player player, PacketContainer packet) {

    }
}
