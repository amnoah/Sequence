package eu.sequence.listener;

import eu.sequence.Sequence;
import eu.sequence.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {
//    public PacketListener(final SequencePlugin plugin) {
//        for (PacketType packetType : PacketType.values()) {
//            if (packetType.isSupported()) {
//                if (packetType.isClient()) {
//                    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, packetType) {
//                        @Override
//                        public void onPacketReceiving(PacketEvent e) {
//                            onPacketReceive(e.getPlayer(), e.getPacket());
//                        }
//                    });
//                }
//            }
//        }
//        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.POSITION) {
//            @Override
//            public void onPacketSending(PacketEvent e) {
//                onPacketSend(e.getPlayer(), e.getPacket());
//            }
//        });
//        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_VELOCITY) {
//            @Override
//            public void onPacketSending(PacketEvent e)
//            {
//                onPacketSend(e.getPlayer(), e.getPacket());
//            }
//        });
//    }
//
//    public void onPacketReceive(Player player, PacketContainer packet) {
//        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(player);
//        if (data == null)
//            return;
//        data.handle(new eu.sequence.event.PacketReceiveEvent(player, new Packet(packet)));
//    }
//
//    public void onPacketSend(Player player, PacketContainer packet) {
//        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(player);
//        if (data == null)
//            return;
//        data.handle(new eu.sequence.event.PacketSendEvent(player, new Packet(packet)));
//    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e)
    {
        Sequence.getInstance().getPlayerDataManager().add(e.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e)
    {
        Sequence.getInstance().getPlayerDataManager().remove(e.getPlayer());
    }


    /**
>>>>>>> Stashed changes
    @EventHandler
    public void onEDBE(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {

            final PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData((Player) e.getEntity());
            data.getVelocityProcessor().handleEDBE(e);
        }
    }
<<<<<<< Updated upstream
=======
    **/

}
