package pcore.distribution;

import org.apache.commons.math3.distribution.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;


/**
 * todo check variable orders
 * Created by TimeWz on 2017/4/17.
 */
public class DistributionManager {
    private static DecimalFormat df = new DecimalFormat("0.0");

    private static DistributionManager ourInstance = new DistributionManager();
    static {
        df.setMaximumFractionDigits(6);

        ourInstance.addDistribution("exp", args -> {
            String name = String.format("exp(%1$s)", df.format(args[0]));
            return new AdaptorRealCommonsMath(name, new ExponentialDistribution(args[0]), "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("gamma", args -> {
            String name = String.format("gamma(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new GammaDistribution(args[0], args[1]), "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("weibull", args -> {
            String name = String.format("weibull(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new WeibullDistribution(args[0], args[1]), "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("norm", args -> {
            String name = String.format("norm(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new NormalDistribution(args[0], args[1]),
                    "Double", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("lnorm", args -> {
            String name = String.format("lnorm(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new LogNormalDistribution(args[0], args[1]),
                    "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("unif", args -> {
            String name = String.format("unif(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new UniformRealDistribution(args[0], args[1]),
                    "Double", args[0], args[1]);
        });

        ourInstance.addDistribution("pois", args -> {
            String name = String.format("pois(%1$s)", df.format(args[0]));
            return new AdaptorIntegerCommonsMath(name, new PoissonDistribution(args[0]),
                    "Integer", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("geom", args -> {
            String name = String.format("geom(%1$s)", df.format(args[0]));
            return new AdaptorIntegerCommonsMath(name, new GeometricDistribution(args[0]),
                    "Integer", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("beta", args -> {
            String name = String.format("beta(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorRealCommonsMath(name, new BetaDistribution(args[0], args[1]),
                    "Double", 0, 1);
        });

        ourInstance.addDistribution("binom", args -> {
            String name = String.format("binom(%1$s,%2$s)", df.format(args[0]), df.format(args[1]));
            return new AdaptorIntegerCommonsMath(name, new BinomialDistribution((int) args[0], args[1]),
                    "Integer", 0, args[0]);
        });

        ourInstance.addDistribution("t", args -> {
            String name = String.format("t(%1$s)", df.format(args[0]));
            return new AdaptorRealCommonsMath(name, new TDistribution(args[0]),
                    "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("chisq", args -> {
            String name = String.format("chisq(%1$s)", df.format(args[0]));
            return new AdaptorRealCommonsMath(name, new ChiSquaredDistribution(args[0]),
                    "Double", 0, Double.POSITIVE_INFINITY);
        });

        ourInstance.addDistribution("triangle", args ->
           new Triangle(args[0], args[2], args[1])
        );

    }

    public static DistributionManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, IDistributionMaker> Distributions;

    private DistributionManager() {
        Distributions = new HashMap<>();
    }

    public void addDistribution(String type, IDistributionMaker func) {
        Distributions.put(type, func);
    }

    public IDistribution parseDistribution(String code) {
        code = code.replaceAll("\\s+", "");
        code = code.replaceAll("(\\(|\\))", " ");
        String[] mat = code.split(" ");

        double[] args = Arrays.stream(mat[1].split(",")).mapToDouble(Double::parseDouble).toArray();

        return Distributions.get(mat[0]).formulate(args);
    }

}
