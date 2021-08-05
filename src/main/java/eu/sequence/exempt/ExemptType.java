package eu.sequence.exempt;

import eu.sequence.data.PlayerData;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum ExemptType {

    SLABS(data -> data.getMovementProcessor().isNearSlabs()),
    STAIRS(data -> data.getMovementProcessor().isNearStairs()),
    LIQUID(data -> data.getMovementProcessor().isInLiquid()),
    WEB(data -> data.getMovementProcessor().isInWeb()),
    NOTINAIR(data -> data.getMovementProcessor().isOnGround() || data.getMovementProcessor().isAtTheEdgeOfABlock()
            || data.getMovementProcessor().getAirTicks() < 5),
    DIGGING(data -> data.getClickingProcessor().isDiggingSent()),
    VELOCITY(data -> (data.getVelocityProcessor().getLastVelocityTimeBukkit() - System.currentTimeMillis()) < 200L),
    SLIME(data -> (data.getMovementProcessor().getLastSlimeTime() - System.currentTimeMillis()) < 200L),
    BLOCKNEARHEAD(data -> data.getMovementProcessor().isUnderABlock()),
    CLIMBABLE(data -> data.getMovementProcessor().isOnClimbable()),
    BOAT(data -> data.getMovementProcessor().isNearBoat())
    ;





    private final Function<PlayerData, Boolean> exception;

    ExemptType(final Function<PlayerData, Boolean> exception) {
        this.exception = exception;
    }
}