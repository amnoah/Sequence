package eu.sequence.check.impl.player.invalidpackets;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;

@CheckInfo(name = "InvalidPackets",subName = "Pitch")
public class InvalidPacketsPitch extends Check {

    //dumbass check but needed

    public InvalidPacketsPitch(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {

            final double pitch = playerData.getRotationProcessor().getPitch();

            if(Math.abs(pitch) > 90.0D) {
                flag("pitch=" + pitch);
            }

        }
    }
}
