/*
 * This program, Ash, was designed to be a plugin for Spigot to catch cheaters.
 * Copyright (C) 2021 ExDiggers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package eu.sequence.data.tracker;

import eu.sequence.utilities.mc.AxisAlignedBB;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.util.Vector;

@Getter
public class TrackedEntity {

    // DO NOT TOUCH

    // Doesn't account for world changes but idc
    private AxisAlignedBB aabb;
    private double x, y, z, newX, newY, newZ;
    private boolean relMove;
    private int newPosRotationIncrements;

    public void handleMove(double x, double y, double z, boolean relMove) {
        if (relMove) {
            this.newX = this.x + x;
            this.newY = this.y + y;
            this.newZ = this.z + z;
        } else {
            this.newX = x;
            this.newY = y;
            this.newZ = z;
        }

        this.newPosRotationIncrements = 3;
        this.relMove = relMove;
    }

    public void onTick() {
        if (newPosRotationIncrements > 0) {
            this.x = x + (newX - x) / newPosRotationIncrements;
            this.y = y + (newY - y) / newPosRotationIncrements;
            this.z = z + (newZ - z) / newPosRotationIncrements;

            --newPosRotationIncrements;

            aabb = new AxisAlignedBB(new Vector(x, y, z));
        }
    }
    
    @Getter
    @RequiredArgsConstructor
    public static class Update {
        private final double x, y, z;

        @Setter
        private boolean responded;
        private final int entityId;

        @Setter
        private int respondedTick;
        private final short id;
        private final UpdateType type;
    }

    public enum UpdateType {
        SPAWN,
        REL_MOVE,
        REMOVE
    }
}
