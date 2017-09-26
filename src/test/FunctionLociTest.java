package test;

import org.junit.Test;
import pcore.ScriptException;
import pcore.loci.FunctionLoci;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 *
 * Created by TimeWz on 2017/7/15.
 */
public class FunctionLociTest {
    private FunctionLoci loci;

    public FunctionLociTest() {
        try {
            loci = new FunctionLoci("Z", "log(X2) + Y");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getParents() throws Exception {
        System.out.println(loci.getParents());
    }

    @Test
    public void getDefinition() throws Exception {
        System.out.println(loci.getDefinition());
    }


    @Test
    public void toJSON() throws Exception {
        System.out.println(loci.toJSON().toString());
    }

}