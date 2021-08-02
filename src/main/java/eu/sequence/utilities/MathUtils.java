package eu.sequence.utilities;

public class MathUtils {

    /** Credits to Gaetan **/
    public static int floor(final double var0) {
        final int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
    }
}
