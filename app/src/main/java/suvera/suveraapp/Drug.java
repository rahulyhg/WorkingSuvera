package suvera.suveraapp;

/**
 * Created by williammeaton on 16/08/2016.
 */
public class Drug {

    private DrugType type;
    private String name;
    private String url;
    Drug(String name, DrugType type, String url){
        this.name = name;
        this.type = type;
        this.url = url;
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
