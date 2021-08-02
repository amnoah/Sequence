package eu.sequence.check.impl.movement.motion;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
import org.bukkit.Bukkit;

@CheckInfo(name = "Motion",subName = "Gravity",experimental = true)
public class MotionGravity extends Check {

    private double lastDeltaY;
    private int preVL;

    public MotionGravity(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isFlying()) {

                MovementProcessor movementProcessor = playerData.getMovementProcessor();

                double deltaY = movementProcessor.getDeltaY();
                double lastDeltaY = this.lastDeltaY;

                this.lastDeltaY = deltaY;
                double predictionY = (lastDeltaY - 0.08) * 0.9800000190734863D; //EntityLivingBase lines 1666 and 1669

                double difference = Math.abs(deltaY - predictionY);

                boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                        movementProcessor.isInWeb() || movementProcessor.isOnClimbable() || Math.abs(predictionY) < 0.05;

                Bukkit.broadcastMessage("d=" + difference);

                if (!exempt && difference > 0.01) {
                    if (++this.preVL > 2) {
                        flag();
                    }

                } else this.preVL -= this.preVL > 0 ? 0.05 : 0;


            }
        }
    }
}
