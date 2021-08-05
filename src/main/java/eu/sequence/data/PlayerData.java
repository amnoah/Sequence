package eu.sequence.data;

import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.data.processors.ClickingProcessor;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.data.processors.VelocityProcessor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import io.github.retrooper.packetevents.packetwrappers.api.SendableWrapper;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class PlayerData {

    private final Player player;

    private final Channel channel;

    private int tick;

    private final CheckManager checkManager;
    private final RotationProcessor rotationProcessor;
    private final MovementProcessor movementProcessor;
    private final ClickingProcessor clickingProcessor;
    private final VelocityProcessor velocityProcessor;



    public PlayerData(final Player player) {

        //associating a player to playerData
        this.player = player;


        this.channel = (Channel) PacketEvents.get().getPlayerUtils().getChannel(player);
    }

    public void handle(PacketEvent event, Packet packet) {
        if (packet.isFlying()) {
            ++tick;
        }


        //init checkManager
        this.checkManager = new CheckManager(this);

        //init processors
        this.rotationProcessor = new RotationProcessor(this);
        this.movementProcessor = new MovementProcessor(this);
        this.clickingProcessor = new ClickingProcessor(this);
        this.velocityProcessor = new VelocityProcessor(this);
    }

    public void handle(Packet packet) {

        //handling processors

        movementProcessor.handle(packet);
        rotationProcessor.handle(packet);
        clickingProcessor.handle(packet);
        velocityProcessor.handle(packet);


        for (Check check : checkManager.getChecks()) {
            if (check.isEnabled()) {
                check.handle(packet);
            }
        }
    }

    public void sendTransaction(WrappedPacketOutTransaction wrapper, boolean flush) {
        PacketEvents.get().getPlayerUtils().sendPacket(player, wrapper);

        if (flush) {
            channel.flush();
        }
    }

    public ClickingProcessor getClickingProcessor() {
        return clickingProcessor;
    }
    
    public VelocityProcessor getVelocityProcessor() {
        return velocityProcessor;
    }
}
