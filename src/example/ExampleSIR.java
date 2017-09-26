package example;

import fitter.SampImpResamp;
import pcore.BayesianModel;
import pcore.DirectedAcyclicGraph;
import pcore.ScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by TimeWz on 25/04/2017.
 */
public class ExampleSIR {

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

            SampImpResamp Fitter = new SampImpResamp(BM);

            Fitter.fit(3000);
            System.out.println(Fitter.getPosterior().stream().mapToDouble(e->e.get("p")).summaryStatistics());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
