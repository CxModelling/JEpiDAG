package pcore.distribution;


import org.apache.commons.math3.distribution.AbstractRealDistribution;


/**
 * An adaptor for integer distribution in Apache commons-math
 * Created by TimeWz on 2017/7/15.
 */
public class AdaptorRealCommonsMath implements IDistribution {
    private AbstractRealDistribution D;
    private String Name, Type;
    private double Lower, Upper;

    public AdaptorRealCommonsMath(String name, AbstractRealDistribution d, String type, double lo, double up) {
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
        return D.sample(n);
    }

    @Override
    public double logpdf(double rv) {
        return D.logDensity(rv);
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
