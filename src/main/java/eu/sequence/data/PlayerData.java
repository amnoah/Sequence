package eu.sequence.data;

import eu.sequence.Sequence;
import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.data.processors.*;
import eu.sequence.exempt.ExemptProcessor;
import eu.sequence.packet.Packet;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.api.SendableWrapper;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import io.netty.channel.Channel;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerData {

    private final Player player;
    private final CheckManager checkManager;
    private final RotationProcessor rotationProcessor;
    private final MovementProcessor movementProcessor;
    private final ClickingProcessor clickingProcessor;
    private final CombatProcessor combatProcessor;
    private final VelocityProcessor velocityProcessor;
    private final ExemptProcessor exemptProcessor;
    private final TickProcessor tickProcessor;
    private final ActionProcessor actionProcessor;
    private final ClientVersion clientVersion;
    private final Channel channel;

    public PlayerData(final Player player) {

        //associating a player to playerData
        this.player = player;

        //init checkManager
        this.checkManager = new CheckManager(this);

        //init processors
        this.rotationProcessor = new RotationProcessor(this);
        this.movementProcessor = new MovementProcessor(this);
        this.clickingProcessor = new ClickingProcessor(this);
        this.velocityProcessor = new VelocityProcessor(this);
        this.combatProcessor = new CombatProcessor(this);
        this.actionProcessor = new ActionProcessor(this);
        this.tickProcessor = new TickProcessor(this);

        this.clientVersion = PacketEvents.get().getPlayerUtils().getClientVersion(player);
        this.channel = (Channel) PacketEvents.get().getPlayerUtils().getChannel(player);

        this.exemptProcessor = new ExemptProcessor(this);

        if (Sequence.getInstance().getMainConfig().isAlertsOnJoin()
                && player.hasPermission(Sequence.getInstance().getMainConfig().getAlertPermission())) {
            Sequence.getInstance().getAlerting().add(this);
        }
    }

    public void handle(Packet packet) {

        //handling processors
        movementProcessor.handle(packet);
        rotationProcessor.handle(packet);
        clickingProcessor.handle(packet);
        velocityProcessor.handle(packet);
        combatProcessor.handle(packet);
        actionProcessor.handle(packet);
        tickProcessor.handle(packet);


        for (Check check : checkManager.getChecks()) {
            if (check.isEnabled()) {
                check.handle(packet);
            }
        }
    }

    public void sendPacket(SendableWrapper packet, boolean flush) {
        PacketEvents.get().getPlayerUtils().sendPacket(player, packet);

        if (flush) {
            channel.flush();
        }
    }

    public void delete() {
        Sequence.getInstance().getAlerting().remove(this);
    }
}
