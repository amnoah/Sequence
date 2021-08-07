package eu.sequence;

import eu.sequence.command.CommandManager;
import eu.sequence.config.impl.CheckConfig;
import eu.sequence.config.impl.MainConfig;
import eu.sequence.data.PlayerData;
import eu.sequence.data.PlayerDataManager;
import eu.sequence.listener.BukkitListener;
import eu.sequence.listener.PacketEventsListener;
import eu.sequence.tick.TickProcessor;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.list.ConcurrentList;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
public class Sequence {
    private static Sequence instance;

    private SequencePlugin plugin;

    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final CommandManager commandManager = new CommandManager();

    private MainConfig mainConfig;
    private CheckConfig checkConfig;

    private final TickProcessor tickProcessor = new TickProcessor();

    private final List<PlayerData> alerting = new ConcurrentList<>();

    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

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
        mainConfig = new MainConfig();
        checkConfig = new CheckConfig();

        commandManager.setup();

        tickProcessor.setup();

        PacketEvents.get().init();
        PacketEvents.get().registerListener(new PacketEventsListener());

        //register events
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), plugin);
    }

    //we call the method just down in the onDisable

    public void stop() {

        //doing that for preventing memory leaks
        executor.shutdown();

        this.plugin = null;
        instance = null;
        this.playerDataManager.getAllData().clear();

        tickProcessor.stop();

        PacketEvents.get().terminate();
    }
}
