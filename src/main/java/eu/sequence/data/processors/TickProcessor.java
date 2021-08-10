package eu.sequence.data.processors;

import eu.sequence.check.Check;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import eu.sequence.tick.TickEvent;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TickProcessor extends Processor {

    private final PlayerData data;
    private int flyingTicks;

    @Override
    public void handle(Packet packet) {
        if(this.data.getClientVersion().isOlderThanOrEquals(ClientVersion.v_1_8)) {
            if(packet.isFlying()) {

                for(Check check : data.getCheckManager().getChecks()) {
                    check.onTick(new TickEvent(flyingTicks,System.currentTimeMillis()));
                }
                this.flyingTicks++;

                if(flyingTicks >= 20) {
                    flyingTicks = 0;
                }
            }
        }

    }
}
