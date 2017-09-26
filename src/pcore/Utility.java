package pcore;

/**
 *
 * Created by TimeWz on 2017/4/24.
 */
public class Utility {
    public static double max(double[] vs) {
        double m = Double.NEGATIVE_INFINITY;
        for(double v: vs) {
            if (m < v) m = v;
        }
        return m;
    }

    public static double lse(double[] vs) {
        double max = max(vs);
        return max + Math.log(sum(exp(add(vs, -max))));
    }

    public static double[] log(double[] vs) {
        double[] l = new double[vs.length];
        for (int i = 0; i < vs.length; i++) {
            l[i] = Math.log(vs[i]);
        }
        return l;
    }

    public static double[] exp(double[] vs) {
        double[] l = new double[vs.length];
        for (int i = 0; i < vs.length; i++) {
            l[i] = Math.exp(vs[i]);
        }
        return l;
    }

    public static double sum(double[] vs) {
        double m = 0;
        for(double v: vs) {
            m += v;
        }
        return m;
    }

    public static double mean(double[] vs) {
        return sum(vs)/vs.length;
    }

    public static double[] add(double[] vs, double m) {
        double[] l = new double[vs.length];
        for (int i = 0; i < vs.length; i++) {
            l[i] = vs[i] + m;
        }
        return l;
    }

    public double rnorm() {
        double u, v, x, y, q;
        do {
            u = Math.random();
            v = 1.7156 * (Math.random() - 0.5);
            x = u - 0.449871;
            y = Math.abs(v) + 0.386595;
            q = x * x + y * (0.19600 * y - 0.25472 * x);
        } while (q > 0.27597 && (q > 0.27846 || v * v > -4 * Math.log(u) * u * u));
        return v / u;
    }
}
