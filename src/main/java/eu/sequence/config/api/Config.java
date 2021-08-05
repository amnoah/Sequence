package eu.sequence.config.api;

import eu.sequence.Sequence;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public abstract class Config {

    protected YamlConfiguration config = new YamlConfiguration();

    public Config(String name) {
        Sequence.getInstance().getPlugin().saveResource(name, false);

        try {
            config.load(new File(Sequence.getInstance().getPlugin().getDataFolder(), name));
        } catch (Exception exception) {
            exception.printStackTrace();
            Sequence.getInstance().getPlugin().getLogger().info(
                    "Error generating config for " + name + "."
            );

            return;
        }

        load();
    }

    public Config(String path, String name) {
        Sequence.getInstance().getPlugin().saveResource(path + "/" + name, false);

        try {
            config.load(
                    new File(Sequence.getInstance().getPlugin().getDataFolder() + "/" + path, name)
            );
        } catch (Exception exception) {
            exception.printStackTrace();
            Sequence.getInstance().getPlugin().getLogger().info(
                    "Error generating config for " + name + "."
            );

            return;
        }

        load();
    }

    public abstract void load();
}
