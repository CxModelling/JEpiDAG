package pcore.distribution;

/**
 *
 * Created by TimeWz on 2017/4/17.
 */
public interface IDistributionMaker {
    IDistribution formulate(double[] args);
}
