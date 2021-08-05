package eu.sequence.check.impl.combat.aim;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.RotationProcessor;
import eu.sequence.packet.Packet;
import eu.sequence.utilities.MathUtils;
import org.bukkit.Bukkit;

@CheckInfo(name = "Aim", subName = "BasicGCD")
public class AimBasicGCD extends Check {

    //totally not from me, a friend helped me - Salers

    private double last, result, vl;


    public AimBasicGCD(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {

            final RotationProcessor rotationProcessor = playerData.getRotationProcessor();

            final double absDeltaPitch = Math.abs(rotationProcessor.getDeltaPitch());
            final double absLastDeltaPitch = Math.abs(rotationProcessor.getLastDeltaPitch());

            final double to = rotationProcessor.getPitch();
            final double gcd = MathUtils.gcd(0x4000, (absDeltaPitch * MathUtils.EXPANDER), (absLastDeltaPitch * Math.pow(2.0, 24.0)));


            // TODO : EXEMPT FROM CINEMATIC
            if (Math.min(this.last, Math.atan(to)) == this.result && gcd < 0x20000 && gcd > 0) {
                if (this.vl < 15) this.vl++;

                if (this.vl > 0.75)
                    flag("gcd=" + gcd);
            }else this.vl -= this.vl > 0 ? 0.05 : 0;


            this.result = Math.min(this.last, Math.atan(to));
            this.last = Math.atan(to);

        }
    }
}
