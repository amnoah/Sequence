package eu.sequence.exempt;

import eu.sequence.data.PlayerData;
import eu.sequence.data.processors.MovementProcessor;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;

import java.util.Collection;

@RequiredArgsConstructor
public class Exempt {




    private final PlayerData data;

    private MovementProcessor movementProcessor = data.getMovementProcessor();

    public boolean liquid() {
       return this.movementProcessor.isInLiquid();
    }

    public boolean climbable() {
        return this.movementProcessor.isOnClimbable();
    }

    public boolean notInAir() {
        return this.movementProcessor.isAtTheEdgeOfABlock()
                || this.movementProcessor.isOnGround()
                || this.movementProcessor.getAirTicks() < 5;

    }

    public boolean slime() {
        return (System.currentTimeMillis() - movementProcessor.getLastSlimeTime()) < (100L) * 20 /*ticks*/ ;

    }

    public boolean underABlock() {
        return this.movementProcessor.isUnderABlock();
    }

    public boolean web() {
        return this.movementProcessor.isInWeb();
    }

    public boolean stairsAndSlabs() {
        return this.movementProcessor.isNearStairs() || this.movementProcessor.isNearSlabs();
    }

    public boolean vehicle() {
        return this.data.getPlayer().isInsideVehicle();
    }

    public boolean digging() {
        return this.data.getClickingProcessor().isDiggingSent();
    }






}
