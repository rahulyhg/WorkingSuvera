package suvera.suveraapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by William Meaton on 8/17/2016.
 */
public class DrugLoader {
    private ArrayList<Drug> drugsList = new ArrayList<Drug>();
    private Context launchContext;


    public DrugLoader(Context ctx) {
        this.launchContext = ctx;
    }

    private String loadJson(){
        String json = null;
        try {
            InputStream is = launchContext.getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void loadDrugs(){
        //code to load the drugs
        try {
            JSONObject obj = new JSONObject(loadJson());

        }catch (JSONException e){
            Log.e("JSON", e.getMessage());
        }
    }
}
