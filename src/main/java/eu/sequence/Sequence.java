package eu.sequence;

import eu.sequence.data.PlayerDataManager;
import eu.sequence.event.PacketListener;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class Sequence {
    private static Sequence instance;

    private SequencePlugin plugin;

    private final PlayerDataManager playerDataManager = new PlayerDataManager();

    public static void createInstance() {
        instance = new Sequence();
    }

    public static Sequence getInstance() {
        return instance;
    }

    public void start(final SequencePlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PacketListener(plugin), plugin);
    }

    public void stop() {
        this.plugin = null;
        instance = null;
    }
}
