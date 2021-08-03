package eu.sequence.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.util.Vector;

@Getter
@Setter
@AllArgsConstructor
public class SequenceLocation {

    private double x,y,z;

    public Vector toBukkitVec() {
        return new Vector(x,y,z);
    }




}
