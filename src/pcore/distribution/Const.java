package pcore.distribution;

import utils.Statistics;

public class Const implements IDistribution {
	private double K;

	public Const(double k){
		K = k;
	}

	@Override
	public String getName() {
		return String.format("k(%1$s,)", K);
	}

	@Override
	public double sample() {
		return K;
	}

	@Override
	public double[] sample(int n) {
		return Statistics.rep(K, n);
	}

	@Override
	public double logpdf(double rv) {
		if (rv == K) {
			return 0;
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

	@Override
	public String getDataType() {
		return "Double";
	}

	@Override
	public double getUpper() {
		return K;
	}

	@Override
	public double getLower() {
		return K;
	}

	@Override
	public double getMean() {
		return K;
	}

	@Override
	public double getStd() {
		return 0;
	}
}
