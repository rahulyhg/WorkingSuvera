package suvera.suveraapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by William Meaton on 8/17/2016.
 */
public class DrugLoader {

    private ArrayList<Drug> drugsList = new ArrayList<Drug>();
    private Context launchContext;
    private HashMap<String, Drug> map;
    public DrugLoader(Context ctx) {
        this.launchContext = ctx;
    }

    private String loadJson(){
        String json = null;
        try {
            InputStream is = launchContext.getAssets().open("drugs.json");
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
        drugsList.clear();
        //code to load the drugs
        try {
            //get a json object from the loaded string
            JSONObject obj = new JSONObject(loadJson());
            //load the data array
            JSONArray array = obj.getJSONArray("data");
            for(int i = 0; i < array.length(); i++){
                JSONArray nameArray = array.getJSONObject(i).getJSONArray("Name");
                //get the name and url
                String name = nameArray.getJSONObject(0).getString("text");
                String url = nameArray.getJSONObject(0).getString("href");
                //do a really basic comparison to find what kind of drug this is
                DrugType type;
                if(name.contains("injection") || name.contains("Injection")){
                    type = DrugType.INJECTION;
                }else if(name.contains("tablets") || name.contains("Tablets")){
                    type = DrugType.TABLET;
                }else if(name.contains("Gel") || name.contains("Wash") || name.contains("Cream")){
                    type = DrugType.LIQUID;
                }else{
                    type = DrugType.OTHER;
                }
//                Log.d("New Drug", name);
                //add the new drug
                drugsList.add(new Drug(i, name, type, url));
            }
            Log.d("Drug Count", String.valueOf(drugsList.size()));
        }catch (JSONException e){
            Log.e("JSON", e.getMessage());
        }
        for(Drug d : drugsList){
            map.put(d.getName(), d);
        }
    }

    public boolean doesDrugExist(String name){
        return map.containsKey(name);
    }

    public Drug getDrug(String name){
        if(map.containsKey(name)) {
            return map.get(name);
        }
        return null;
    }

    public Drug getDrug(int id){
        if(id < 0 || id >= drugsList.size()){
            return null;
        }
        return drugsList.get(id);
    }

    public ArrayList<Drug> search(String search){
        ArrayList<Drug> result = new ArrayList<Drug>();
        for(Drug d : drugsList){
            if(d.getName().contains(search)){
                result.add(d);
            }
        }
        return result;
    }

    public String[] getNameArray(){
        String[] names = new String[drugsList.size()];
        for(int i = 0; i < names.length; i++){
            names[i] = drugsList.get(i).getName();
        }
        return names;
    }

    public ArrayList<Drug> getDrugsList() {
        return drugsList;
    }
}
