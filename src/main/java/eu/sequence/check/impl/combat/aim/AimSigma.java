package eu.sequence.check.impl.combat.aim;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;

@CheckInfo(name = "Aim", subName = "Sigma")
public class AimSigma extends Check {

    private double lastDeltaYaw, vl;

    public AimSigma(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isRotation()) {

                final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

                final double deltaPitch = rotationProcessor.getDeltaPitch();
                final double deltaYaw = rotationProcessor.getDeltaYaw();

                final double lastDeltaYaw = this.lastDeltaYaw;
                this.lastDeltaYaw = deltaYaw;

                final double yawAcceleration = Math.abs(deltaYaw - lastDeltaYaw);

                if ((now() - playerData.getCombatProcessor().getLastAttackTime() < 100L)) {
                    if (deltaPitch == 0.0D && yawAcceleration > 29.5D) {
                        if (++this.vl > 8) {
                            flag();
                        }
                    } else this.vl -= this.vl > 0 ? 0.25 : 0;
                }

            }
        }

    }
}
