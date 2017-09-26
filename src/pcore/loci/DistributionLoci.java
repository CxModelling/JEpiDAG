package pcore.loci;

import org.json.JSONArray;
import org.json.JSONObject;
import pcore.Gene;
import pcore.ScriptException;
import pcore.distribution.DistributionManager;
import pcore.distribution.IDistribution;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by TimeWz on 2017/4/17.
 */
public class DistributionLoci extends Loci {
    private static ScriptEngine engine = (new ScriptEngineManager())
            .getEngineByName("JavaScript");


    private final List<String> Parents;
    private final String Distribution;

    public DistributionLoci(String name, String dist) throws ScriptException {
        super(name);
        Distribution = dist;
        Parents = parseParents(dist);
    }

    public DistributionLoci(String name, String dist, Collection<String> parents) {
        super(name);
        Distribution = dist;
        Parents = new ArrayList<>(parents);
    }

    @Override
    public List<String> getParents() {
        return Parents;
    }

    public static List<String> parseParents(String fn) throws ScriptException {
        String code = fn.replaceAll("\\s+", "");
        code = code.replaceAll("(\\(|\\))", " ");
        String mat = code.split(" ")[1];


        List<String> parents = new ArrayList<>();
        for (String arg : mat.split(",")) {
            while (true) {
                try {
                    engine.eval(arg);
                    break;
                } catch (javax.script.ScriptException e) {
                    String mes = e.getMessage();
                    mes = mes.split("\"")[1];

                    parents.add(mes);
                    arg = arg.replaceAll("\\b" + mes + "\\b", "0.87");
                }
            }
        }

        return parents;
    }

    @Override
    public double evaluate(Map<String, Double> pas) {
        return findDistribution(pas).logpdf(pas.get(getName()));
    }

    @Override
    public double sample(Map<String, Double> pas) {
        return findDistribution(pas).sample();
    }

    @Override
    public void fill(Gene gene) {
        IDistribution dist = findDistribution(gene.getLocus());
        double v = dist.sample();
        gene.put(getName(), v);
        gene.addLogPriorProb(dist.logpdf(v));
    }

    public IDistribution findDistribution(Map<String, Double> pas) {
        String f = Distribution;
        for (String par : Parents) {
            f = f.replaceAll("\\b" + par + "\\b", pas.get(par).toString());
        }

        String code = f.replaceAll("\\s+", "");
        code = code.replaceAll("(\\(|\\))", " ");
        String[] mat = code.split(" ");
        code = Arrays.stream(mat[1].split(",")).map(e -> {
            try {
                return engine.eval(e).toString();
            } catch (javax.script.ScriptException e1) {
                return "0";
            }
        }).collect(Collectors.joining(","));
        f = mat[0] + "(" + code + ")";

        return DistributionManager.getInstance().parseDistribution(f);
    }

    @Override
    public String getDefinition() {
        return getName() + "~" + Distribution;
    }

    @Override
    public String toString() {
        return getName() + ": " + Distribution;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject js = new JSONObject();
        js.put("Type", "Distribution");
        js.put("Def", Distribution);
        js.put("Parents", new JSONArray(getParents()));
        return js;
    }
}
