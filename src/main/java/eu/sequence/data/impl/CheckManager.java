package eu.sequence.data.impl;

import eu.sequence.check.Check;
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
        checks = Arrays.asList(new MotionGravity(playerData),new MotionJump(playerData),new SpeedHorizontal(playerData), new FlightStable(playerData));
    }

    public List<Check> getChecks() {
        return checks;
    }

    public static void init(PlayerData data) {

    }


}
