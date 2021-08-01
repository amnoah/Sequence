package eu.sequence.check;

import eu.sequence.Sequence;
import eu.sequence.event.PacketEvent;
import lombok.Getter;
import eu.sequence.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Check {
    protected final PlayerData playerData;
    @Getter protected final String name;
    @Getter protected final boolean experimental;
    @Getter protected double max;
    @Getter protected boolean enabled;

    protected double vl = 0;

    public Check(final PlayerData playerData)
    {
        this.playerData = playerData;

        /* get the annotation and extract the check info from it */
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.name = checkInfo.name();
            this.experimental = checkInfo.experimental();
        } else {
            Bukkit.getConsoleSender().sendMessage("No CheckInfo annotation found in class " + this.getClass().getSimpleName());
            this.name = this.getClass().getSimpleName();
            this.experimental = false;
        }
        /* getting values from the config */
        if (name.contains(" ")) {
            String s = name.toLowerCase().split(" ")[0], s2 = name.toLowerCase().split(" ")[1].replace("(", "").replace(")", "");
            max = Sequence.getInstance().getPlugin().getConfig().getInt("checks." + s + "." + s2 + ".max");
            enabled = Sequence.getInstance().getPlugin().getConfig().getBoolean("checks." + s + "." + s2 + ".enabled");
        } else {
            max = Sequence.getInstance().getPlugin().getConfig().getInt("checks." + name.toLowerCase() + ".max");
            enabled = Sequence.getInstance().getPlugin().getConfig().getBoolean("checks." + name.toLowerCase() + ".enabled");
        }
    }

    protected void flag()
    {
        vl++;
        /* TODO: add actual alerts */
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&eSequence&8] &e" + playerData.getPlayer().getName() + "&7 failed &e" + name + " &8(&ex" + ((int) Math.round(vl)) + "&8)"));
        if (Math.floor(vl + 1) > max) {
            vl = 0;
            punish();
        }
    }

    @SuppressWarnings("deprecation")
    public void punish()
    {
        Bukkit.getScheduler().runTask(Sequence.getInstance().getPlugin(), new BukkitRunnable() {
            @Override
            public void run()
            {
                playerData.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&8[&eSequence&8] &eUnfair Advantage"));
            }
        });
    }

    public void handle(PacketEvent event)
    {

    }
}
