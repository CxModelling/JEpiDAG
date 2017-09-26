package test;

import org.json.JSONObject;
import org.junit.Test;
import pcore.DirectedAcyclicGraph;
import pcore.ScriptException;

/**
 *
 * Created by TimeWz on 2017/6/17.
 */
public class TestDAG {

    @Test
    public void fromJSON() throws Exception {
        String Script = "PCore RR{\n"
                + "rate = 0.05\n"
                + "rr = 1.5\n"
                + "tr1 ~ exp(rate)\n"
                + "tr2 ~ exp(rate*rr)\n"
                + "}";
        System.out.println(Script);

        DirectedAcyclicGraph DAG;
        try {
            DAG = new DirectedAcyclicGraph(Script);
            DAG.print();

            DAG = new DirectedAcyclicGraph(DAG.toJSON());
            DAG.print();

            System.out.println(DAG.getPathways());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
