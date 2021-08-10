package eu.sequence.check.impl.combat.aim;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
import org.bukkit.Bukkit;

@CheckInfo(name = "Aim", subName = "SensivityGCD",configPath = "aim.sensivitygcd")
public class AimSensivityGCD extends Check {

    private int vl;

    public AimSensivityGCD(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {

            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double absDeltaPitch = Math.abs(rotationProcessor.getDeltaPitch());
            final double absLastDeltaPitch = Math.abs(rotationProcessor.getLastDeltaPitch());

            final double gcd = MathUtils.gcd(0x4000, (absDeltaPitch * MathUtils.EXPANDER), (absLastDeltaPitch * MathUtils.EXPANDER));


            if (gcd < 131072L && absDeltaPitch != 0.0 && absLastDeltaPitch != 0.0) {
                if (++this.vl > 10) {
                    flag("gcd=" + gcd);
                }

            } else this.vl -= this.vl > 0 ? 1 : 0;
        }


    }
}
