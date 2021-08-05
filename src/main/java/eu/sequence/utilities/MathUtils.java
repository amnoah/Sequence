package eu.sequence.utilities;

import lombok.experimental.UtilityClass;

import java.util.Collection;
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

    public double deviationSquared(final Iterable<? extends Number> numbers) {
        double total = 0.0;
        int i = 0;
        for (final Number number : numbers) {
            total += number.doubleValue();
            ++i;
        }
        final double average = total / (double) i;
        double deviation = 0.0;
        for (final Number number : numbers) {
            deviation += Math.pow(number.doubleValue() - average, 2.0);
        }
        return deviation / (double) (i - 1);
    }

    public double deviation(final Iterable<? extends Number> numbers) {
        return Math.sqrt(MathUtils.deviationSquared(numbers));
    }






    public double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);

        // The standard deviation is the square root of variance. (sqrt(s^2))
        return Math.sqrt(variance);
    }

    public double getVariance(final Collection<? extends Number> data) {
        int count = 0;

        double sum = 0.0;
        double variance = 0.0;

        final double average;

        // Increase the sum and the count to find the average and the standard deviation
        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        average = sum / count;

        // Run the standard deviation formula
        for (final Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }

        return variance;
    }

    public double getKurtosis(final Collection<? extends Number> data) {
        double sum = 0.0;
        int count = 0;

        for (final Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        if (count < 3.0) {
            return 0.0;
        }

        final double efficiencyFirst = count * (count + 1.0) / ((count - 1.0) * (count - 2.0) * (count - 3.0));
        final double efficiencySecond = 3.0 * Math.pow(count - 1.0, 2.0) / ((count - 2.0) * (count - 3.0));
        final double average = sum / count;

        double variance = 0.0;
        double varianceSquared = 0.0;

        for (final Number number : data) {
            variance += Math.pow(average - number.doubleValue(), 2.0);
            varianceSquared += Math.pow(average - number.doubleValue(), 4.0);
        }

        return efficiencyFirst * (varianceSquared / Math.pow(variance / sum, 2.0)) - efficiencySecond;
    }
}
