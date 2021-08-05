package eu.sequence.data;

import eu.sequence.packet.Packet;
import org.bukkit.event.Event;

public abstract class Processor {

    /**
     * @author Salers
     **/

    public abstract void handle(Packet packet);


}
