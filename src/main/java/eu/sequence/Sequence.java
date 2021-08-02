package eu.sequence;

import eu.sequence.data.PlayerDataManager;
import eu.sequence.event.PacketListener;
import lombok.Getter;
import org.bukkit.Bukkit;

public class Sequence {
    private static Sequence instance;
    private SequencePlugin plugin;

    @Getter private final PlayerDataManager playerDataManager = new PlayerDataManager();

    public static void createInstance()
    {
        instance = new Sequence();
    }

    public void start(final SequencePlugin plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PacketListener(plugin), plugin);
    }

    public void stop(final SequencePlugin plugin)
    {
        this.plugin = null;
        instance = null;
    }

    public static Sequence getInstance()
    {
        return instance;
    }

    public SequencePlugin getPlugin()
    {
        return plugin;
    }
}
