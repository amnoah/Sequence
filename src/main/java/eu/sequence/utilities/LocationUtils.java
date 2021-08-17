package eu.sequence.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@UtilityClass
public class LocationUtils {

    /**
     * @param loc the location of the block to get
     * @return the block at the location
     */
    private Block getBlockAsync(final Location loc) {
        if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4))
            return loc.getBlock();
        return null;
    }

    /**
     * Checking if a location is close to ground
     *
     * @param location the location to check
     * @return if the location is close to ground
     */
    public boolean isCloseToGround(Location location) {
        double distanceToGround = 0.31;
        for (double locX = -distanceToGround; locX <= distanceToGround; locX += distanceToGround) {
            for (double locZ = -distanceToGround; locZ <= distanceToGround; locZ += distanceToGround) {
                if (location.clone().add(locX, -0.5001, locZ).getBlock().getType() == Material.AIR) {
                    return false;
                }
            }

        }
        return true;
    }


    /**
     * Check if player is in liquid
     *
     * @param player the player to check
     * @return if player is in liquid
     */
    public boolean isInLiquid(final Location location) {
        final double expand = 0.31;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if player is near a boat
     *
     * @param player the player to check
     * @return if player is near a boat
     */
    public boolean isNearBoat(final Player player) {
        for (final Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof Boat) return true;
        }
        return false;
    }

    /**
     * Checking if player is colliding with a climbable
     *
     * @param player the player to check
     * @return if player is colling with a climbable
     */

    public boolean isCollidingWithClimbable(final Location location) {
        final int var1 = MathUtils.floor(location.getX());
        final int var2 = MathUtils.floor(location.getY());
        final int var3 = MathUtils.floor(location.getZ());
        final Block var4 = new Location(location.getWorld(), var1, var2, var3).getBlock();
        return var4.getType() == Material.LADDER || var4.getType() == Material.VINE;
    }

    /**
     * Checking if player is colliding with web
     *
     * @param player the player to check
     * @return if player is colling with web
     */

    public boolean isCollidingWithWeb(final Location location) {
        final int var1 = MathUtils.floor(location.getX());
        final int var2 = MathUtils.floor(location.getY());
        final int var3 = MathUtils.floor(location.getZ());
        final Block var4 = new Location(location.getWorld(), var1, var2, var3).getBlock();
        return var4.getType() == Material.WEB;
    }

    /**
     * Checking if player is at the edge of a block
     *
     * @param player the player to check
     * @return if player is at the edge of a block
     */
    public boolean isAtEdgeOfABlock(final Location location) {
        Location b1 = location.clone().add(0.3, -0.3, -0.3);
        Location b2 = location.clone().add(-0.3, -0.3, -0.3);
        Location b3 = location.clone().add(0.3, -0.3, 0.3);
        Location b4 = location.clone().add(-0.3, -0.3, +0.3);
        return b1.getBlock().getType() != Material.AIR || b2.getBlock().getType() != Material.AIR ||
                b3.getBlock().getType() != Material.AIR || b4.getBlock().getType() != Material.AIR;

    }

    /**
     * Checking if player is near slabs
     *
     * @param player the player to check
     * @return if player is near slabs
     */

    public boolean isNearSlabs(final Location location) {
        final double expand = 0.31;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).toString().contains("slab")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checking if player is near stairs
     *
     * @param player the player to check
     * @return if player is near stairs
     */


    public boolean isNearStairs(final Location location) {
        final double expand = 0.31;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).toString().contains("stair")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean blockNearHead(final Location location) {
        if (getBlockAsync(location.clone().add(0, 0.5001, 0)).getType() != Material.AIR) {
            return true;

        }
        return false;
    }


}
