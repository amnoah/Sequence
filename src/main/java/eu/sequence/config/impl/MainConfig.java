package eu.sequence.config.impl;

import eu.sequence.config.Config;
import eu.sequence.utilities.StringUtils;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public class MainConfig extends Config {

    private String mainColor, secondaryColor,
            alertFormat, hoverInfo, clickCommand,
            alertPermission;
    private double alertDelay;
    private boolean alertsOnJoin;

    public MainConfig() {
        super("config.yml");
    }

    @Override
    public void load() {
        mainColor = ChatColor.translateAlternateColorCodes(
                '&', config.getString("color.main")
        );
        secondaryColor = ChatColor.translateAlternateColorCodes(
                '&', config.getString("color.secondary")
        );
        alertFormat = ChatColor.translateAlternateColorCodes(
                '&', config.getString("alerts.format")
        );
        hoverInfo = StringUtils.stringListToString(
                config.getStringList("alerts.hover-info"),
                false,
                true
        );

        clickCommand = config.getString("alerts.click-command");
        alertPermission = config.getString("alerts.permission");

        alertDelay = config.getDouble("alerts.delay") * 20;

        alertsOnJoin = config.getBoolean("alerts.on-join");
    }
}
