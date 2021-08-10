package eu.sequence.check.impl.player.invalidpackets;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;

@CheckInfo(name = "InvalidPackets",subName = "NearGround",configPath = "invalidpackets.nearground")
public class InvalidPacketsNearGround extends Check {


    public InvalidPacketsNearGround(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final int airTicks = movementProcessor.getAirTicks();

            /**
             * as it is from the PacketPlayInFlying ground
             * which is set by the client it can be spoofed
              **/

            final boolean isFlyingPacketGround = movementProcessor.isOnGround();

            final boolean spoofingGround = airTicks > 3 && isFlyingPacketGround;

            final boolean exempt = isExempt(ExemptType.NOTINAIR);

            if(spoofingGround && !exempt) {
                flag("airTicks=" + airTicks);
            }



        }
    }
}
