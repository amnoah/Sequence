package eu.sequence.event;

import eu.sequence.packet.Packet;

public class PacketEvent {
    private final Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
