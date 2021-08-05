package eu.sequence;

import org.bukkit.plugin.java.JavaPlugin;

public class SequencePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        Sequence.createInstance();

        Sequence.getInstance().load(this);
    }

    @Override
    public void onEnable() {
        Sequence.getInstance().start();
    }

    @Override
    public void onDisable() {
        Sequence.getInstance().stop();
    }
}
