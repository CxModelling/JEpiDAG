package example;

import fitter.MCMC;
import pcore.BayesianModel;
import pcore.DirectedAcyclicGraph;
import pcore.Gene;
import pcore.ScriptException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by TimeWz on 25/04/2017.
 */
public class ExampleMCMC {

    public static void show() {
        String Script = "{\n"
                + "n=100\n"
                + "p~beta(1, 1)\n"
                + "x~binom(n,p)\n"
                + "}";
        System.out.println(Script);

        DirectedAcyclicGraph DAG;
        try {
            DAG = new DirectedAcyclicGraph(Script);
            DAG.print();

            Map<String, Double> Data = new HashMap<>();
            Data.put("x", 70.0);
            System.out.println(Data);

            BayesianModel BM = DAG.getBayesianModel(Data.keySet());
            BM.setInputData(Data);

            MCMC Fitter = new MCMC(BM);

            Fitter.fit(300);
            System.out.println(Fitter.getPosterior().stream().mapToDouble(e->e.get("p")).summaryStatistics());


        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    public static void regression() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("x1=1\n");
        sb.append("b1~norm(10, 1)\n");
        sb.append("x2=-1\n");
        sb.append("b2~norm(20, 1)\n");
        sb.append("mu=x1*b1+x2*b2\n");
        sb.append("sigma~gamma(0.01, 0.01)\n");
        sb.append("y~norm(mu, sigma)\n");
        sb.append("}");
        DirectedAcyclicGraph ca = null;
        try {
            ca = new DirectedAcyclicGraph(sb.toString());
        } catch (ScriptException scriptException) {
            scriptException.printStackTrace();
        }

        for (String o: ca.getOrder()) {
            System.out.println(o);
        }

        ca.print();

        Map<String, Double> val = ca.sample();
        System.out.println(val);
        System.out.println(ca.evaluate(val));


        List<String> evi = new ArrayList<>();
        evi.add("x1");
        evi.add("y");

        BayesianModel MB = ca.getBayesianModel(evi);

        MB.print();
        Gene gene = MB.samplePrior();
        System.out.println(gene);
        Map<String, Double> Data = new HashMap<>();
        Data.put("x1", 1.0);
        Data.put("y", 100.0);
        MB.setInputData(Data);
        System.out.println(MB.evaluateLikelihood(gene));
    }
}
