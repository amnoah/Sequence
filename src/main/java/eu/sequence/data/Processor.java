package eu.sequence.data;

import com.comphenix.protocol.events.PacketEvent;

;

public abstract class Processor {




    public abstract void handleReceive(PacketEvent event);

    public abstract void handleSending(PacketEvent event);
}
