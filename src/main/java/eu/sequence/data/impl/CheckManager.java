package eu.sequence.data.impl;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import eu.sequence.check.Check;
import eu.sequence.check.impl.Example;
import eu.sequence.check.impl.movement.motion.MotionGravity;
import eu.sequence.check.impl.movement.motion.MotionJump;

import eu.sequence.check.impl.movement.speed.SpeedHorizontal;

import eu.sequence.data.PlayerData;

import java.util.Collection;

public class CheckManager {
    private final ClassToInstanceMap<Check> checks;

    public CheckManager(final PlayerData playerData) {
        checks = new ImmutableClassToInstanceMap.Builder<Check>()
                .put(Example.class, new Example(playerData))
                .put(MotionGravity.class,new MotionGravity(playerData))
                .put(MotionJump.class,new MotionJump(playerData))

                .put(SpeedHorizontal.class,new SpeedHorizontal(playerData))

                .build();
    }

    public Collection<Check> getChecks()
    {
        return checks.values();
    }

    public Check getCheck(final Class<? extends Check> clazz)
    {
        return checks.getInstance(clazz);
    }
}
