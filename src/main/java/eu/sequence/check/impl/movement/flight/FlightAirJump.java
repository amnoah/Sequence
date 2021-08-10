package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;

import eu.sequence.exempt.ExemptType;

import eu.sequence.packet.Packet;

@CheckInfo(name = "Flight", subName = "AirJump",configPath = "flight.airjump")
public class FlightAirJump extends Check {

    private int vl;
    private double lastDeltaY;

    /**
     * @author Salers
     **/


    public FlightAirJump(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            final MovementProcessor movementProcessor = playerData.getMovementProcessor();

            final double deltaY = movementProcessor.getDeltaY();
            final double lastDeltaY = this.lastDeltaY;

            this.lastDeltaY = deltaY;

            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.WEB,ExemptType.LIQUID,ExemptType.NOTINAIR);



            if (!exempt && deltaY > lastDeltaY) {
                if (++this.vl > 3) {
                    flag("delta=" + deltaY + " ldelta=" + lastDeltaY);
                }
            } else this.vl -= this.vl > 0 ? 0.05 : 0;
        }
    }
}
