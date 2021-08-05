package eu.sequence.data;

import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.data.tracker.EntityTracker;
import eu.sequence.event.PacketReceiveEvent;
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
    private final CheckManager checkManager = new CheckManager(this);
    private final RotationProcessor rotationProcessor = new RotationProcessor(this);
    private final MovementProcessor movementProcessor = new MovementProcessor(this);
    private final EntityTracker entityTracker = new EntityTracker(this);
    private final Channel channel;

    private int tick;


    public PlayerData(final Player player) {

        //associating a player to playerData
        this.player = player;

        this.channel = (Channel) PacketEvents.get().getPlayerUtils().getChannel(player);
    }

    public void handle(PacketEvent event, Packet packet) {
        if (packet.isFlying()) {
            ++tick;
        }

        entityTracker.handle(packet);
        movementProcessor.handle(packet);
        rotationProcessor.handle(packet);

        if (event instanceof PacketPlaySendEvent) {
            entityTracker.onPacketPlaySend((PacketPlaySendEvent) event);
        }

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
}
