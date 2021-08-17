package eu.sequence.check.impl.combat.aura;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura",subName = "Swing")
public class AuraSwing extends Check {

    private int packetsSent;

    public AuraSwing(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isArmAnimation()) {
            this.packetsSent = 0;
        }else if(packet.isUseEntity()) {
            final WrappedPacketInUseEntity wrappedPacket = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if (wrappedPacket.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                if(this.packetsSent++ > 3)
                    flag("packets=" + packetsSent);

            }
        }
    }
}
