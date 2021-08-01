package eu.sequence.data;

import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.event.PacketEvent;
import org.bukkit.entity.Player;

public class PlayerData {
    private final Player player;
    private final CheckManager checkManager;

    public PlayerData(final Player player)
    {
        this.player = player;
        checkManager = new CheckManager(this);
    }

    public void handle(PacketEvent event)
    {
        for (Check check: checkManager.getChecks()) {
            if (check.isEnabled()) {
                check.handle(event);
            }
        }
    }

    public Player getPlayer()
    {
        return player;
    }

    public CheckManager getCheckManager()
    {
        return checkManager;
    }
}
