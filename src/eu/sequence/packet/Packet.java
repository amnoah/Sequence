package eu.sequence.packet;

import com.comphenix.protocol.events.PacketContainer;

public class Packet extends PacketContainer {
    public Packet(PacketContainer packet)
    {
        super(packet.getType(), packet.getHandle(), packet.getModifier());
    }

    public boolean isSending()
    {
        return this.getType().isServer();
    }

    public boolean isReceiving()
    {
        return this.getType().isClient();
    }
}
