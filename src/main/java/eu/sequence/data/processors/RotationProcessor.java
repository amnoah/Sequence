package eu.sequence.data.processors;

import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@RequiredArgsConstructor
public class RotationProcessor extends Processor {

    private final PlayerData data;

    private float yaw, pitch,
            deltaYaw, deltaPitch,
            lastYaw, lastPitch,
            lastDeltaYaw, lastDeltaPitch;

    @Override
    public void handle(Packet packet) {
        if(packet.isRotation()) {
            WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getNmsPacket());

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
}
