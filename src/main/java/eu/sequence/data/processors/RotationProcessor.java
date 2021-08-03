package eu.sequence.data.processors;

import com.comphenix.packetwrapper.WrapperPlayClientLook;
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

    private double yaw, pitch,
            deltaYaw, deltaPitch,
            lastYaw, lastPitch,
            lastDeltaYaw, lastDeltaPitch;

    @Override
    public void handleReceive(PacketEvent event) {
        if(event.getPacket().isRotation()) {
            WrapperPlayClientLook wrapper = new WrapperPlayClientLook(event.getPacket());

            /* Getting yaw one tick ago */
            lastYaw = yaw;
            yaw = wrapper.getYaw() % 360;
            deltaYaw = Math.abs(yaw - lastYaw) % 360;

            /* Getting pitch one tick ago */
            lastPitch = pitch;
            pitch = wrapper.getPitch();
            deltaPitch = Math.abs(pitch - lastPitch);
        }
    }


    @Override
    public void handleSending(PacketEvent event) {
    }
}
