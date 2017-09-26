package example;

import pcore.*;


/**
 *
 * Created by TimeWz on 2017/4/26.
 */
public class ExamplePCore {
    public static void show() {
        String Script = "PCore RR{\n"
                + "rate = 0.05\n"
                + "rr ~ unif(1, 1.5) # comment\n"
                + "tr1 ~ exp(rate)\n"
                + "tr2 ~ exp(rate*rr)\n"
                + "}";
        System.out.println(Script);

        DirectedAcyclicGraph DAG;
        try {
            DAG = new DirectedAcyclicGraph(Script);
            DAG.print();

            SimulationModel Model = DAG.getSimulationModel();
            ParameterCore Core = Model.sampleCore();
            System.out.println(Core.getDistribution("tr1"));

            ParameterCore Core2 = Model.sampleCore();
            System.out.println(Core.findDifference(Core2));
            System.out.println(Model.getDAG().toJSON().toString(2));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
