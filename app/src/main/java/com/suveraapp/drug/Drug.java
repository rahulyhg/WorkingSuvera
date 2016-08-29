package com.suveraapp.drug;

/**
 * Created by williammeaton on 16/08/2016.
 */
public class Drug {

    private DrugType type;
    private int id;
    private String name;
    private String url;
    public Drug(int id, String name, DrugType type, String url){
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public int getId(){
        return id;
    }

    public DrugType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}