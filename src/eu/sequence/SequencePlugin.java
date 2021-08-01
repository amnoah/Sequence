package eu.sequence;

import org.bukkit.plugin.java.JavaPlugin;

public class SequencePlugin extends JavaPlugin {
    @Override
    public void onEnable()
    {
        Sequence.createInstance();
        Sequence.getInstance().start(this);
    }

    @Override
    public void onDisable()
    {
        Sequence.getInstance().stop(this);
    }
}
