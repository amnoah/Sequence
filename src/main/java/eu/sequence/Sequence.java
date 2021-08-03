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

    //we call the method just down in the onEnable

    public void start(final SequencePlugin plugin) {
        this.plugin = plugin;

        //config registering
        plugin.saveDefaultConfig();

        //register events
        Bukkit.getPluginManager().registerEvents(new PacketListener(plugin), plugin);
    }

    //we call the method just down in the onDisable

    public void stop() {

        //doing that for preventing memory leaks

        this.plugin = null;
        instance = null;
        this.playerDataManager.getAllData().clear();
    }
}
