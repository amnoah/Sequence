

/*
 * This program, Ash, was designed to be a plugin for Spigot to catch cheaters.
 * Copyright (C) 2021 ExDiggers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package eu.sequence.command;

import eu.sequence.Sequence;
import eu.sequence.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class Command implements CommandExecutor {

    private String permission;
    private boolean playerOnly, async;

    public Command() {
        if (getClass().isAnnotationPresent(CommandInfo.class)) {
            CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);

            String command = commandInfo.command();
            String[] aliases = commandInfo.aliases();
            permission = commandInfo.permission();
            playerOnly = commandInfo.playerOnly();
            async = commandInfo.async();

            Sequence.getInstance().getPlugin().getCommand(command).setExecutor(this);

            if (aliases.length > 0) {
                Sequence.getInstance().getPlugin().getCommand(command).setAliases(Arrays.asList(aliases));
            }
        } else {
            Sequence.getInstance().getPlugin().getLogger().info("Error: '" + getClass().getSimpleName() + "' is not annotated with CommandInfo!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        PlayerData playerData;

        if (!(sender instanceof Player) && playerOnly) {
            sendMessage(sender, "&cOnly players can use this command.");
            return true;
        } else if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission(permission)) {
                playerData = Sequence.getInstance().getPlayerDataManager().getPlayerData(player);

                if (playerData == null) {
                    sendMessage(sender, "&cError #001; please relog.");
                } else {
                    if (async) {
                        Sequence.getInstance().getExecutor().execute(() ->
                                handle(sender, playerData, args));

                        return true;
                    }

                    handle(sender, playerData, args);
                }
                return true;
            } else {
                sendMessage(sender, "&cNo permission.");
            }
        }

        return false;
    }

    public abstract void handle(CommandSender sender, PlayerData playerData, String[] args);

    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        (message.replaceAll("&m", Sequence.getInstance().getMainConfig().getMainColor())
                                .replaceAll("&s", Sequence.getInstance().getMainConfig().getSecondaryColor())))
        );
    }

    protected void sendLineBreak(CommandSender sender, String color) {
        sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        (color.replaceAll("&m", Sequence.getInstance().getMainConfig().getMainColor())
                                .replaceAll("&s", Sequence.getInstance().getMainConfig().getSecondaryColor())
                                + ChatColor.STRIKETHROUGH + "------------------------" + ChatColor.RESET)
                )
        );
    }
}
