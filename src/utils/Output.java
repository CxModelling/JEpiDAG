package utils;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Created by TimeWz on 2015/12/4.
 */
public class Output {
    public static void csv(String data, String file){
        try
        {
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void json(JSONObject data, String file){
        try  {
            FileWriter f = new FileWriter(file);
            data.write(f);
            f.flush();
            f.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
