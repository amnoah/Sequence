package eu.sequence.check.impl.movement.speed;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.packet.Packet;

@CheckInfo(name = "Speed",subName = "Friction")
public class SpeedFriction extends Check {

    // TODO: exempt on teleport & velocity because those aren't handled


    private double lastDeltaXZ;
    private boolean wasOnGround;


    public SpeedFriction(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            // ground values
            final boolean onGround = playerData.getMovementProcessor().isOnGround();
            final boolean wasOnGround = this.wasOnGround;


            // deltas
            final double deltaXZ = playerData.getMovementProcessor().getDeltaXZ();
            final double lastDeltaXZ = this.lastDeltaXZ;

            this.lastDeltaXZ = deltaXZ;
            this.wasOnGround = onGround;

            final double friction = getBlockFriction() * 0.91F;
            final double prediction = this.lastDeltaXZ * friction;

            final double difference = Math.abs(prediction - deltaXZ);

            if (!wasOnGround && !onGround) {
                //TODO CHECK
            }
        }
    }

    public float getBlockFriction() {
        String block = playerData.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase();
        return block.equals("blue ice") ? 0.989f : block.contains("ice") ? 0.98f : block.equals("slime") ? 0.8f : 0.6f;
    }
}
