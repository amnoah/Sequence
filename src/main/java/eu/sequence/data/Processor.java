package eu.sequence.data;

import eu.sequence.event.PacketEvent;
import eu.sequence.packet.Packet;

public abstract class Processor {

    /**
     * @author Salers
     **/

    public abstract void handle(Packet packet);
}
