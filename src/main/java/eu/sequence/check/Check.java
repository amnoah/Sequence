package eu.sequence.check;

import eu.sequence.Sequence;
import eu.sequence.data.PlayerData;


import eu.sequence.exempt.ExemptType;

import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
import eu.sequence.utilities.ServerUtils;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Check {
    protected final PlayerData playerData;
    @Getter
    protected final String name;
    @Getter
    protected boolean enabled, punish;
    @Getter
    protected final boolean experimental;
    @Getter
    protected double max;
    private final String subName;
    private final String description;
    private double lastAlert;

    protected double vl = 0;





    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        /* get the annotation and extract the check info from it */
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.name = checkInfo.name();
            this.subName = checkInfo.subName();
            this.experimental = checkInfo.experimental();
            this.description = checkInfo.description();
        } else {
            Bukkit.getConsoleSender().sendMessage("No CheckInfo annotation found in class " + this.getClass().getSimpleName());
            this.name = this.getClass().getSimpleName();
            this.experimental = false;
            this.subName = "";
            this.description = "";
        }
        /* getting values from the config */
        String nameLowerCase = name.contains(" ") ? name.toLowerCase().split(" ")[0] : name.toLowerCase();
        String subNameLowerCase = subName.toLowerCase();

        max = Sequence.getInstance().getCheckConfig().getMax(nameLowerCase, subNameLowerCase);
        enabled = Sequence.getInstance().getCheckConfig().isEnabled(nameLowerCase, subNameLowerCase);
        punish = Sequence.getInstance().getCheckConfig().isPunish(nameLowerCase, subNameLowerCase);
    }



    protected boolean isExempt(ExemptType exemptType) {
        return playerData.getExemptProcessor().isExempt(exemptType);
    }

    protected boolean isExempt(ExemptType... exemptTypes) {
        return playerData.getExemptProcessor().isExempt(exemptTypes);
    }

    protected void flag(String info) {
        final Player player = this.playerData.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR ||  player.getAllowFlight()) return;

        vl++;

        int tick = Sequence.getInstance().getTickProcessor().getTick();


        if (tick - lastAlert >= Sequence.getInstance().getMainConfig().getAlertDelay()) {
            lastAlert = tick;



                TextComponent alert = new TextComponent();

                alert.setText(
                        replace(Sequence.getInstance().getMainConfig().getAlertFormat(), info)
                );

                alert.setHoverEvent(
                        new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                new ComponentBuilder(
                                       ChatColor.translateAlternateColorCodes('&', replace(
                                                Sequence.getInstance().getMainConfig().getHoverInfo(), info
                                        ))
                                ).create()
                        )
                );

                alert.setClickEvent(
                        new ClickEvent(
                                ClickEvent.Action.RUN_COMMAND,
                                Sequence.getInstance().getMainConfig().getClickCommand().replaceAll(
                                        "%player%", playerData.getPlayer().getName()
                                )
                        )
                );

                Sequence.getInstance().getAlerting().forEach(data
                        -> data.getPlayer().spigot().sendMessage(alert));


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
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                      ChatColor.translateAlternateColorCodes('&' , Sequence.getInstance().getCheckConfig().getPunishCommand().replaceAll(
                                "%player%",
                                playerData.getPlayer().getName())
                        )
                );
            }
        });
    }

    public abstract void handle(Packet packet);

    protected long now() {
        return System.currentTimeMillis();
    }

    private String replace(String str, String info) {
        str = str.replaceAll("%player%", playerData.getPlayer().getName())
                .replaceAll("%check%", name)
                .replaceAll("%subname%", subName)
                .replaceAll("%experimental%", experimental ? "*" : "")
                .replaceAll("%vl%", String.valueOf(vl))
                .replaceAll("%info%", info)
                .replaceAll("%description", description)
//                .replaceAll("%keepaliveping%", String.valueOf(playerData.getConnectionProcessor().getKeepAlivePing()))
//                .replaceAll("%transping%", String.valueOf(playerData.getConnectionProcessor().getTransactionPing()))
//                .replaceAll("%lagging%", String.valueOf(playerData.getConnectionProcessor().isFlyingDeltaDeviationLagging()))
                .replaceAll("%version%", playerData.getClientVersion().name())
                .replaceAll("%tps%", String.valueOf(MathUtils.round(ServerUtils.getTPS(), 2)))
//                .replaceAll("%client%", playerData.getClient())
                .replaceAll("%max%", String.valueOf(max));

        return str;
    }
}
