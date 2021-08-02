package eu.sequence.data.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import eu.sequence.data.PlayerData;
import eu.sequence.data.Processor;
import lombok.Data;

@Data
public class RotationProcessor extends Processor {

    private PlayerData data;

    private double deltaYaw,deltaPitch,lastYaw,lastPitch;

    public RotationProcessor(PlayerData data) {
        this.data = data;
    }


    @Override
    public void handleReceive(PacketEvent event) {

        if(event.getPacketType() == PacketType.Play.Client.POSITION_LOOK || event.getPacketType() ==
                PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client.LOOK) {

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
