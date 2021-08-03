package eu.sequence.utilities;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MathUtils {

    private final float[] b = new float[65536];
    public final double EXPANDER = Math.pow(2,24);

    /** Credits to MCP **/
    public int floor(final double var0) {
        final int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
    }

    public int clamp(int var0, int var1, int var2) {
        if (var0 < var1) {
            return var1;
        } else {
            return Math.min(var0, var2);
        }
    }

    public int nextInt(Random var0, int var1, int var2) {
        return var1 >= var2 ? var1 : var0.nextInt(var2 - var1 + 1) + var1;
    }

    public float sin(float var0) {
        return b[(int)(var0 * 10430.378F) & '\uffff'];
    }

    public float cos(float var0) {
        return b[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
    }

    public double hypot(double d,double d1) {
        return (d * d) + (d1 * d1);
    }

    public float sqrt(double var0) {
        return (float)Math.sqrt(var0);
    }

    public double gcd(final double limit, final double a, final double b) {
        return b <= limit ? a : MathUtils.gcd(limit, b, a % b);
    }
}
