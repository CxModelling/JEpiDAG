package pcore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by TimeWz on 2017/4/23.
 */
public class CausalDiagram implements IParameterModel {

    private final DirectedAcyclicGraph DAG;

    public CausalDiagram(DirectedAcyclicGraph DAG) {
        this.DAG = DAG;
    }

    public Collection<String> getMarkovBlanket(String src) {
        Set<String> mb = new HashSet<>();
        Set<String> pa = new HashSet<>();
        pa.add(src);
        pa.addAll(DAG.getParents(src));
        mb.forEach(mb::add);

        pa.forEach(e->mb.addAll(DAG.getChildren(e)));
        mb.remove(src);

        return mb;
    }

    @Override
    public DirectedAcyclicGraph getDAG() {
        return DAG;
    }
}
