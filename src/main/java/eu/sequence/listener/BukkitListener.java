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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e)
    {
        Sequence.getInstance().getPlayerDataManager().add(e.getPlayer());

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData(e.getPlayer());

        data.delete();

        Sequence.getInstance().getPlayerDataManager().remove(e.getPlayer());
    }


    @EventHandler
    public void onEDBE(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {

            final PlayerData data = Sequence.getInstance().getPlayerDataManager().getPlayerData((Player) e.getEntity());
            if(data == null) return;
            data.getVelocityProcessor().handleEDBE(e);
        }
    }
}
