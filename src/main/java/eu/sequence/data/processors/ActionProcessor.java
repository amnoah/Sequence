package eu.sequence.data.processors;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.entityaction.WrappedPacketInEntityAction;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ActionProcessor extends Processor {

    private final PlayerData data;

    private boolean isSprinting,isSneaking;

    @Override
    public void handle(Packet packet) {
        if(packet.isEntityAction()) {

            final WrappedPacketInEntityAction wrapper = new WrappedPacketInEntityAction(packet.getNmsPacket());

            final WrappedPacketInEntityAction.PlayerAction action = wrapper.getAction();

            switch (action) {
                case START_SNEAKING:
                    this.isSneaking = true;
                    break;
                case STOP_SNEAKING:
                    this.isSneaking = false;
                    break;
                case START_SPRINTING:
                    this.isSprinting = true;
                    break;
                case STOP_SPRINTING:
                    this.isSprinting = false;
                    break;


            }

        }
    }
}
