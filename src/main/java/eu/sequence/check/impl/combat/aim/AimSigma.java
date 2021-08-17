package eu.sequence.check.impl.combat.aim;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.packet.Packet;

@CheckInfo(name = "Aim", subName = "Sigma")
public class AimSigma extends Check {

    private double lastDeltaYaw, vl;

    public AimSigma(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double deltaPitch = rotationProcessor.getDeltaPitch();
            final double deltaYaw = rotationProcessor.getDeltaYaw();

            final double lastDeltaYaw = this.lastDeltaYaw;
            this.lastDeltaYaw = deltaYaw;

            final double yawAcceleration = Math.abs(deltaYaw - lastDeltaYaw);

            /**
             *
             The logic behind this check is :
             a human cannot make a perfect straight line while rotating,
             so we check that.

             **/

            if (deltaPitch == 0.0D && yawAcceleration > 31.5D) {
                if (++this.vl > 11) {
                    flag("deltaPitch= " + deltaPitch + " acceleration=" + yawAcceleration);
                }
            } else this.vl -= this.vl > 0 ? 0.75 : 0;
        }
    }
}
