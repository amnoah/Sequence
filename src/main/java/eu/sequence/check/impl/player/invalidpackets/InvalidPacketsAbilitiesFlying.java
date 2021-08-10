package eu.sequence.check.impl.player.invalidpackets;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;

@CheckInfo(name = "InvalidPackets",subName = "AbilitiesFlying",configPath = "invalidpackets.abilitiesflying")
public class InvalidPacketsAbilitiesFlying extends Check {

    public InvalidPacketsAbilitiesFlying(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isAbilities()) {
            if (packet.spoofingAbilities()) {
                flag("spoofing=true");
            }
        }

    }
}
