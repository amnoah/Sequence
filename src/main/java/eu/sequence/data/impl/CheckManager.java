package eu.sequence.data.impl;

import eu.sequence.check.Check;
import eu.sequence.check.impl.combat.aim.AimSigma;
import eu.sequence.check.impl.combat.reach.ReachA;
import eu.sequence.check.impl.movement.flight.FlightAirJump;
import eu.sequence.check.impl.movement.flight.FlightStable;
import eu.sequence.check.impl.movement.motion.MotionGravity;
import eu.sequence.check.impl.movement.motion.MotionJump;
import eu.sequence.check.impl.movement.speed.SpeedHorizontal;
import eu.sequence.data.PlayerData;

import java.util.Arrays;
import java.util.List;

public class CheckManager {
    private final List<Check> checks;

    public CheckManager(final PlayerData playerData) {
       checks = Arrays.asList(
               new FlightStable(playerData),
               new FlightAirJump(playerData),
               new MotionJump(playerData),
               new MotionGravity(playerData),
               new SpeedHorizontal(playerData),
               new AimSigma(playerData),
               new ReachA(playerData));
    }

    public List<Check> getChecks()
    {
        return checks;
    }
}
