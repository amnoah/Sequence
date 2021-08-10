package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;

import eu.sequence.exempt.ExemptType;

import eu.sequence.packet.Packet;


@CheckInfo(name = "Flight", subName = "Stable",configPath = "flight.stable")
public class FlightStable extends Check {

    //dumbass check but efficient

    /**
     * @author Salers
     **/

    private double lastDeltaY;
    private int vl;

    public FlightStable(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {

            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.WEB,ExemptType.LIQUID,ExemptType.NOTINAIR);



            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;
            final double accelerationYAxis = Math.abs(deltaY - lastDeltaY);

            if (accelerationYAxis < 0.001 && !exempt) {
                if (++this.vl > 4)
                    flag("accel=" + accelerationYAxis);
            }else this.vl -= this.vl > 0 ? 0.05 : 0;


        }
    }
}

