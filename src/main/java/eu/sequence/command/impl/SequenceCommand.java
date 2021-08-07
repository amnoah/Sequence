package eu.sequence.command.impl;

import eu.sequence.command.Command;
import eu.sequence.command.CommandInfo;
import eu.sequence.data.PlayerData;
import org.bukkit.command.CommandSender;

@CommandInfo(command = "sequence", permission = "sequence.command")
public class SequenceCommand extends Command {

    @Override
    public void handle(CommandSender sender, PlayerData playerData, String[] args) {

    }
}
