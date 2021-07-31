package eu.sequence;

public class Sequence {
    private static Sequence instance;
    private SequencePlugin plugin;

    public static void createInstance() {
        instance = new Sequence();
    }

    public void start(SequencePlugin plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();

    }

    public void stop(SequencePlugin plugin) {

    }

    public static Sequence getInstance() {
        return instance;
    }
}
