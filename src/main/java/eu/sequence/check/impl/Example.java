package eu.sequence.check.impl;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.event.PacketEvent;

@CheckInfo(name = "Example", experimental = true)
public class Example extends Check {
    public Example(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {

    }
}
