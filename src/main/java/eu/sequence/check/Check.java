package eu.sequence.check;

import eu.sequence.Sequence;
import eu.sequence.data.PlayerData;
import eu.sequence.event.PacketEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Check {
    protected final PlayerData playerData;
    @Getter
    protected final String name;
    private String subName;
    @Getter
    protected final boolean experimental;
    @Getter
    protected double max;
    @Getter
    protected boolean enabled;

    protected double vl = 0;

    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        /* get the annotation and extract the check info from it */
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.name = checkInfo.name();
            this.subName = checkInfo.subName();
            this.experimental = checkInfo.experimental();
        } else {
            Bukkit.getConsoleSender().sendMessage("No CheckInfo annotation found in class " + this.getClass().getSimpleName());
            this.name = this.getClass().getSimpleName();
            this.experimental = false;
        }
        /* getting values from the config */
        if (name.contains(" ")) {
            String s = name.toLowerCase().split(" ")[0], s2 = subName.toLowerCase();
            max = Sequence.getInstance().getPlugin().getConfig().getInt("checks." + s + "." + s2 + ".max");
            enabled = Sequence.getInstance().getPlugin().getConfig().getBoolean("checks." + s + "." + s2 + ".enabled");
        } else {
            max = Sequence.getInstance().getPlugin().getConfig().getInt("checks." + name.toLowerCase() + "." + subName.toLowerCase() + ".max");
            enabled = Sequence.getInstance().getPlugin().getConfig().getBoolean("checks." + name.toLowerCase() + "." + subName.toLowerCase() + ".enabled");
        }
    }

    protected void flag() {
        vl++;
        /* TODO: add actual alerts */
        if (subName.equalsIgnoreCase("")) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&eSequence&7] " +
                    "&e" + playerData.getPlayer().getName() + "&f failed &e" + name + " &8(&ex" + ((int) Math.round(vl)) + "&8)"));
        } else {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&eSequence&7] " +
                    "&e" + playerData.getPlayer().getName() + "&f failed &e" + name + "&f(&e" + this.subName + "&f)" +
                    " &8(&ex" + ((int) Math.round(vl)) + "&8)"));
        }
        if (Math.floor(vl + 1) > max) {
            vl = 0;
            punish();
        }
    }

    @SuppressWarnings("deprecation")
    public void punish() {
        Bukkit.getScheduler().runTask(Sequence.getInstance().getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                playerData.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&7[&eSequence&7] &eUnfair Advantage"));
            }
        });
    }

    public abstract void handle(PacketEvent event);

    protected long now() {
        return System.currentTimeMillis();
    }
}
