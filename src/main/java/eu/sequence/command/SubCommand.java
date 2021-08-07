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
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

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
                ChatColor.STRIKETHROUGH
                        + ChatColor.translateAlternateColorCodes('&',
                        (color.replaceAll("&m", Sequence.getInstance().getMainConfig().getMainColor())
                                .replaceAll("&s", Sequence.getInstance().getMainConfig().getSecondaryColor())
                                + "--------------------" + ChatColor.RESET)
                )
        );
    }
}
