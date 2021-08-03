package eu.sequence.data;

import eu.sequence.check.Check;
import eu.sequence.data.impl.CheckManager;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.event.PacketEvent;
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

    public void handle(PacketEvent event) {

        if (event.getPacket().isSending()) {
            movementProcessor.handleSending(event);
            rotationProcessor.handleSending(event);
        } else if (event.getPacket().isReceiving()) {
            movementProcessor.handleReceive(event);
            rotationProcessor.handleReceive(event);
        }

        for (Check check : checkManager.getChecks()) {
            if (check.isEnabled()) {
                check.handle(event);
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
