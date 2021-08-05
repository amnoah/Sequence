package eu.sequence.utilities;

import io.github.retrooper.packetevents.PacketEvents;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerUtils {

    public double getTPS() {
        return Math.min(20, PacketEvents.get().getServerUtils().getTPS());
    }
}
