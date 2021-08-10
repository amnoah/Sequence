package eu.sequence.config.impl;

import eu.sequence.check.Check;
import eu.sequence.config.Config;
import eu.sequence.data.impl.CheckManager;
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

        for(Check checks : CheckManager.checks) {
            if(validate(checks)) {
                this.config.set("checks." + checks.getPath() + ".enabled", isEnabled(checks.getName(), checks.getSubName()));
                this.config.set("checks." + checks.getPath() + ".max", getMax(checks.getName(), checks.getSubName()));
                this.config.set("checks." + checks.getPath() + ".punish", isPunish(checks.getName(), checks.getSubName()));
            }else {
                this.config.set("checks." + checks.getPath() + ".enabled", true);
                this.config.set("checks." + checks.getPath() + ".max", 10);
                this.config.set("checks." + checks.getPath() + ".punish", true);
            }
        }
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

    private boolean validate(final Check check) {
        return config.get("checks." + check.getPath()) != null;
    }
}