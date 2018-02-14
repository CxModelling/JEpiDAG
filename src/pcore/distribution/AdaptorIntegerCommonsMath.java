package pcore.distribution;


import org.apache.commons.math3.distribution.AbstractIntegerDistribution;


/**
 * An adaptor for integer distribution in Apache commons-math
 * Created by TimeWz on 2017/7/15.
 */
public class AdaptorIntegerCommonsMath implements IDistribution {
    private AbstractIntegerDistribution D;
    private String Name, Type;
    private double Lower, Upper;

    public AdaptorIntegerCommonsMath(String name, AbstractIntegerDistribution d, String type, double lo, double up) {
        Name = name;
        D = d;
        Type = type;
        Lower = lo;
        Upper = up;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public double sample() {
        return D.sample();
    }

    @Override
    public double[] sample(int n) {
        double[] res = new double[n];
        int i = 0;
        for (double s: D.sample(n)) {
            res[i] = s;
            i++;
        }
        return res;
    }

    @Override
    public double logpdf(double rv) {
        return D.logProbability((int) rv);
    }

    @Override
    public String getDataType() {
        return Type;
    }

    @Override
    public double getUpper() {
        return Upper;
    }

    @Override
    public double getLower() {
        return Lower;
    }

    @Override
    public double getMean() {
        return D.getNumericalMean();
    }

    @Override
    public double getStd() {
        return Math.sqrt(D.getNumericalVariance());
    }

    public String toString() {
        return Name;
    }
}
