package suvera.suveraapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DrugLoader drugLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drugLoader = new DrugLoader(getApplicationContext());
        drugLoader.loadDrugs();
    }
}
