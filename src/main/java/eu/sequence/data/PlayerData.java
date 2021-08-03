package eu.sequence.data;

import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.event.PacketEvent;
import eu.sequence.packet.Packet;
import org.bukkit.entity.Player;

public class PlayerData {

    private final Player player;
    private final CheckManager checkManager;
    private final RotationProcessor rotationProcessor;
    private final MovementProcessor movementProcessor;


    public PlayerData(final Player player) {

        //associating a player to playerData
        this.player = player;

        //init checkManager
        this.checkManager = new CheckManager(this);

        //init processors
        this.rotationProcessor = new RotationProcessor(this);
        this.movementProcessor = new MovementProcessor(this);
    }

    public void handle(Packet packet) {
        movementProcessor.handle(packet);
        rotationProcessor.handle(packet);

        for (Check check : checkManager.getChecks()) {
            if (check.isEnabled()) {
                check.handle(packet);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public RotationProcessor getRotationProcessor() {
        return rotationProcessor;
    }

    public MovementProcessor getMovementProcessor() {
        return movementProcessor;
    }
}
