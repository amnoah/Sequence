package eu.sequence.check.impl.combat.aura;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Aura",subName = "Multi",configPath = "aura.multi")
public class AuraMulti extends Check {

    private int ticks,lastID;

    public AuraMulti(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isFlying()) {
            this.ticks = 0;


        }else if(packet.isUseEntity()) {

            final WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(packet.getNmsPacket());

            if(wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {

                if(lastID != wrapper.getEntityId()) {
                    if(++ticks > 1) {
                        flag("ticks=" + ticks);
                    }
                }


                this.lastID = wrapper.getEntityId();

            }
        }

    }
}
