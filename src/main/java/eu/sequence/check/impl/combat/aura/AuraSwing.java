package eu.sequence.check.impl.combat.aura;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura",subName = "Swing")
public class AuraSwing extends Check {

    private int packetsSended;

    public AuraSwing(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isArmAnimation()) {
            this.packetsSended = 0;
        }else if(packet.isUseEntity()) {
            final WrappedPacketInUseEntity wrappedPacket = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if (wrappedPacket.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                if(this.packetsSended++ > 3)
                    flag();

            }

        }
    }
}
