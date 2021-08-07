package eu.sequence.command;

import eu.sequence.command.impl.SequenceCommand;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class CommandManager {

    private List<Command> commands;

    public void setup() {
        commands = Arrays.asList(
            new SequenceCommand()
        );
    }
}
