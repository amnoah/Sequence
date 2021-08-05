package eu.sequence.check.impl.movement.flight;

import eu.sequence.check.Check;
import eu.sequence.check.CheckInfo;
import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
<<<<<<< Updated upstream
import eu.sequence.event.PacketEvent;
import eu.sequence.event.PacketReceiveEvent;
=======
import eu.sequence.exempt.ExemptType;
import eu.sequence.packet.Packet;
>>>>>>> Stashed changes


@CheckInfo(name = "Flight", subName = "Stable")
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
    public void handle(PacketEvent event) {
        if (event instanceof PacketReceiveEvent) {
            if (event.getPacket().isFlying()) {

<<<<<<< Updated upstream
                MovementProcessor movementProcessor = playerData.getMovementProcessor();
                int streakY = 0,streakDeltaY = 0;
=======
            final MovementProcessor movementProcessor = playerData.getMovementProcessor();
            final boolean exempt = isExempt(ExemptType.CLIMBABLE,ExemptType.NOTINAIR,ExemptType.SLIME,ExemptType.LIQUID,ExemptType.WEB);
>>>>>>> Stashed changes

                boolean exempt = movementProcessor.getAirTicks() < 5 || movementProcessor.isInLiquid() ||
                        movementProcessor.isInWeb() || movementProcessor.isOnClimbable() ;
                if(exempt) return;

                if(movementProcessor.getLastY() == movementProcessor.getY()) {
                    streakY++;

                }else streakY = 0;

                double deltaY = movementProcessor.getDeltaY();
                double lastDeltaY = this.lastDeltaY;

                this.lastDeltaY = deltaY;

                if(deltaY == lastDeltaY) {
                    streakDeltaY++;

                }else streakDeltaY = 0;

                if(streakDeltaY > 6 || streakY > 11) {
                    if(++vl > 4) {
                        flag();
                    }
                }else vl -= vl > 0 ? 0.05 : 0;





            }
        }
    }


}
