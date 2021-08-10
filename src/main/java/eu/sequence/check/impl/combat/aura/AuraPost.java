package eu.sequence.check.impl.combat.aura;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura", subName = "Post",configPath = "aura.post")
public class AuraPost extends Check {

    private long lastFlyingPacketSent;
    private int vl;

    public AuraPost(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {

            final WrappedPacketInUseEntity wrappedPacket = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if (wrappedPacket.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {

                final long elapsed = now() - this.lastFlyingPacketSent;

                if(elapsed < 25L) {
                    if(++this.vl > 7) {
                        flag("elapsed=" + elapsed);
                    }
                }else this.vl = 0;
            }


        }else if(packet.isFlying()) {
            this.lastFlyingPacketSent = now();
        }

    }
}
