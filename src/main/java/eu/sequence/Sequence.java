package eu.sequence;

import eu.sequence.data.PlayerDataManager;
import eu.sequence.listener.BukkitListener;
import eu.sequence.listener.PacketEventsListener;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
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

    public void load(final SequencePlugin plugin) {
        this.plugin = plugin;

        PacketEvents.create(plugin).getSettings()
                .bStats(true)
                .checkForUpdates(false)
                .compatInjector(false)
                .fallbackServerVersion(ServerVersion.v_1_8_8);

        PacketEvents.get().load();
    }

    public void start() {
        //config registering
        plugin.saveDefaultConfig();

        PacketEvents.get().init();
        PacketEvents.get().registerListener(new PacketEventsListener());

        //register events
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), plugin);
    }

    //we call the method just down in the onDisable

    public void stop() {

        //doing that for preventing memory leaks

        this.plugin = null;
        instance = null;
        this.playerDataManager.getAllData().clear();

        PacketEvents.get().terminate();
    }
}
