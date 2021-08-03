package eu.sequence.data.impl;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import eu.sequence.check.Check;
import eu.sequence.check.impl.movement.flight.FlightAirJump;
import eu.sequence.check.impl.movement.flight.FlightStable;
import eu.sequence.check.impl.movement.motion.MotionGravity;
import eu.sequence.check.impl.movement.motion.MotionJump;

import eu.sequence.check.impl.movement.speed.SpeedFriction;

import eu.sequence.data.PlayerData;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CheckManager {
    private final List<Check> checks;

    public CheckManager(final PlayerData playerData) {
       checks = Arrays.asList(
               new FlightStable(playerData),
               new FlightAirJump(playerData),
               new MotionJump(playerData),
               new MotionGravity(playerData),
               new SpeedFriction(playerData));
    }

    public List<Check> getChecks()
    {
        return checks;
    }


}
