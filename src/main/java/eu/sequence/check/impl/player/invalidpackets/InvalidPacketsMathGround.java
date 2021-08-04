package eu.sequence.check.impl.player.invalidpackets;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;

@CheckInfo(name = "InvalidPackets",subName = "MathGround")
public class InvalidPacketsMathGround extends Check {


    public InvalidPacketsMathGround(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();
            final double groundYExpander = 1 / 64; //0.15625 from EntityLivingBase

            final boolean isMathGround = movementProcessor.getY() % groundYExpander < 0.0001; //can be spoofed but hardly

            /**
             * as it is from the PacketPlayInFlying ground
             * which is set by the client it can be spoofed
              **/
            final boolean isFlyingPacketGround = movementProcessor.isOnGround();

            if(isFlyingPacketGround && !isMathGround && movementProcessor.getAirTicks() > 10 && !movementProcessor.isAtTheEdgeOfABlock()) {
                flag();
            }
        }
    }
}
