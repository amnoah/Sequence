package eu.sequence.check.impl.movement.step;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

@CheckInfo(name = "Step",subName = "Height")
public class StepHeight extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled


    private boolean wasOnGround;


    public StepHeight(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            // ground values
            final boolean onGround = movementProcessor.isOnGround();
            final boolean wasOnGround = this.wasOnGround;

            this.wasOnGround = onGround;


            // deltas
            final double deltaY = movementProcessor.getDeltaY();


            if (wasOnGround && onGround) {
                if(deltaY > 0.6) /** max vanilla step height is 0.6**/ {
                    flag("deltaY=" + deltaY);
                }


            }
        }
    }




}
