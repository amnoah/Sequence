package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import eu.sequence.exempt.Exempt;
import eu.sequence.packet.Packet;

@CheckInfo(name = "Flight", subName = "AirJump")
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

            final Exempt exemptClass = new Exempt(playerData);

            final boolean exempt = exemptClass.climbable() || exemptClass.web() || exemptClass.liquid() || exemptClass.notInAir();




            if (!exempt && deltaY > lastDeltaY) {
                if (++this.vl > 3) {
                    flag("delta=" + deltaY + " ldelta=" + lastDeltaY);
                }
            } else this.vl -= this.vl > 0 ? 0.05 : 0;
        }
    }
}
