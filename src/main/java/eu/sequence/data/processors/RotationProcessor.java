package eu.sequence.data.processors;

import com.comphenix.protocol.PacketType;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.event.PacketEvent;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@RequiredArgsConstructor
public class RotationProcessor extends Processor {

    private final PlayerData data;

    private double deltaYaw,deltaPitch,lastYaw,lastPitch;

    @Override
    public void handleReceive(PacketEvent event) {

        if(event.getPacket().isPosLook() || event.getPacket().isRotation()) {

            /** Getting yaw one tick ago **/
            double yaw = event.getPlayer().getLocation().getYaw();
            deltaYaw = yaw - this.lastYaw;
            this.lastYaw = yaw;

            /** Getting pitch one tick ago **/
            double pitch = event.getPlayer().getLocation().getPitch();
            this.deltaPitch = pitch - this.lastPitch;
            this.lastPitch = pitch;
        }

    }


    @Override
    public void handleSending(PacketEvent event) {
        return;
    }
}
