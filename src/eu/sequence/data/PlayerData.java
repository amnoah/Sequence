package eu.sequence.data;

import eu.sequence.data.impl.CheckManager;
import org.bukkit.entity.Player;

public class PlayerData {
    private final Player player;
    private final CheckManager checkManager;

    public PlayerData(final Player player) {
        this.player = player;
        checkManager = new CheckManager(this);
    }

    public Player getPlayer() {
        return player;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }
}
