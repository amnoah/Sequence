package eu.sequence.config.impl;

import eu.sequence.config.Config;
import lombok.Getter;

@Getter
public class CheckConfig extends Config {

    private String punishCommand;

    public CheckConfig() {
        super("checks.yml");
    }

    @Override
    public void load() {
        punishCommand = config.getString("punish-command");
    }

    public boolean isEnabled(String name, String subName) {
        return config.getBoolean("checks." + name + "." + subName + ".enabled");
    }

    public boolean isPunish(String name, String subName) {
        return config.getBoolean("checks." + name + "." + subName + ".punish");
    }

    public double getMax(String name, String subName) {
        return config.getDouble("checks." + name + "." + subName + ".max");
    }
}