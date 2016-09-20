package com.suveraapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppSuvera extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //initialise realm database
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }
}

