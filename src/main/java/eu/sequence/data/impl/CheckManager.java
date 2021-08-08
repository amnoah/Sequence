package eu.sequence.data.impl;

import eu.sequence.check.Check;
import eu.sequence.check.impl.combat.aim.AimBasicGCD;
import eu.sequence.check.impl.combat.aim.AimSensivityGCD;
import eu.sequence.check.impl.combat.aim.AimSigma;
import eu.sequence.check.impl.combat.aura.AuraMulti;
import eu.sequence.check.impl.combat.aura.AuraPost;
import eu.sequence.check.impl.combat.aura.AuraSwing;
import eu.sequence.check.impl.movement.flight.FlightAirJump;
import eu.sequence.check.impl.movement.flight.FlightStable;
import eu.sequence.check.impl.movement.flight.FlightVelocity;
import eu.sequence.check.impl.movement.motion.MotionGravity;
import eu.sequence.check.impl.movement.motion.MotionJump;
import eu.sequence.check.impl.movement.step.StepHeight;
import eu.sequence.check.impl.movement.speed.SpeedHorizontal;
import eu.sequence.check.impl.player.invalidpackets.InvalidPacketsAbilitiesFlying;
import eu.sequence.check.impl.player.invalidpackets.InvalidPacketsNearGround;
import eu.sequence.check.impl.player.invalidpackets.InvalidPacketsPitch;
import eu.sequence.check.impl.player.inventory.InventoryMove;
import eu.sequence.data.PlayerData;

import java.util.Arrays;
import java.util.List;

public class CheckManager {
    private final List<Check> checks;

    public CheckManager(final PlayerData playerData) {
       checks = Arrays.asList(
               // MOVEMENT
               new FlightStable(playerData),
               new FlightAirJump(playerData),
               new FlightVelocity(playerData),
               new MotionJump(playerData),
               new MotionGravity(playerData),
               new SpeedHorizontal(playerData),

               new StepHeight(playerData),

               //PLAYER
               new InvalidPacketsAbilitiesFlying(playerData),
               new InvalidPacketsNearGround(playerData),
               new InvalidPacketsPitch(playerData),
               new InventoryMove(playerData),


               //COMBAT

               new AuraPost(playerData),
               new AuraSwing(playerData),
               new AuraMulti(playerData),
               new AimBasicGCD(playerData),
               new AimSensivityGCD(playerData),
               new AimSigma(playerData));
    }

    public List<Check> getChecks()
    {
        return checks;
    }
}
