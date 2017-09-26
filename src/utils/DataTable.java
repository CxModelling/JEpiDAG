package utils;

import org.json.JSONObject;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Created by TimeWz on 2016/7/13.
 */
public class DataTable extends TreeMap<Double, DataEntry> implements Cloneable{

    private String ser;

    public DataTable(String tit) {
        ser = tit;
    }

    public DataTable() {
        this("Time");
    }

    public void outputCSV(String file) {
        StringBuilder s = new StringBuilder();
        s.append(ser+",").append(this.firstEntry().getValue().header());
        for (Map.Entry<Double, DataEntry> de: this.entrySet()) {
            s.append("\n").append(de.getKey()).append(",").append(de.getValue().toCSV());
        }
        Output.csv(s.toString(), file);
    }

    public void outputJSON(String file) {
        Output.json(new JSONObject(this), file);
    }

    public DataTable clone() {
        DataTable dt;
        try {
            dt = (DataTable) super.clone();
        } catch (Exception e) {
            dt = new DataTable(this.ser);
            for (Map.Entry<Double, DataEntry> de: this.entrySet()) {
                dt.put(de.getKey(), de.getValue().clone());
            }
        }


        return dt;
    }
}
