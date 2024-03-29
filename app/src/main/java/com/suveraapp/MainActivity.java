package com.suveraapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suveraapp.drug.DrugLoader;
import com.suveraapp.navigation.HelpScreen;
import com.suveraapp.navigation.HomeScreen;
import com.suveraapp.navigation.Profile;
import com.suveraapp.navigation.ProfileScreen;
import com.suveraapp.navigation.SettingsScreen;
import com.suveraapp.onboarding.AddDrug;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, ResultCallback<GoogleSignInResult> {
    public static DrugLoader drugLoader;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        drugLoader = new DrugLoader(this);
        drugLoader.loadDrugs();

        //hide status bar on lower sdks
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //set background using the time period
        setBackground();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.OverviewFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDrug.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //stetho for checking the realm database by chrome://inspect
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("FireBaseAuth", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("FireBaseAuth", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        silentLogin();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        swapFragment(new HomeScreen());
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    //function to get current time on android
    public void getTimeFromAndroid() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
        } else {
            Date dt = new Date();
            hour = dt.getHours();
        }
    }

    //set backGround for main actiivity
    public void setBackground() {
        getTimeFromAndroid();
        if (hour >= 0 && hour < 3) {
            //latenight
            this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.background_ln);
            this.findViewById(android.R.id.content).getBackground().setColorFilter(null);
        } else if (hour >= 3 && hour < 12) {
            //morning
            this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.background_m);
            this.findViewById(android.R.id.content).getBackground().setColorFilter(null);
        } else if (hour >= 12 && hour < 17) {
            //afternoon
            this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.background_a);
            this.findViewById(android.R.id.content).getBackground().setColorFilter(null);
        } else if (hour >= 17 && hour < 21) {
            //evening
            this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.background_e);
            this.findViewById(android.R.id.content).getBackground().setColorFilter(null);
        } else if (hour >= 21 && hour <= 24) {
            //latenight
            this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.background_ln);
            this.findViewById(android.R.id.content).getBackground().setColorFilter(null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setBackground();

    }

    public void swapFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void silentLogin() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        final OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            Profile.getInstance().setProfile(pendingResult.get().getSignInAccount());
        } else {
            pendingResult.setResultCallback(this); // set a callback
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            swapFragment(new ProfileScreen());
        } else if (id == R.id.nav_settings) {
            swapFragment(new SettingsScreen());
        } else if (id == R.id.nav_help) {
            swapFragment(new HelpScreen());
        } else if (id == R.id.nav_home) {
            swapFragment(new HomeScreen());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed to connect to Google.", Toast.LENGTH_LONG).show();
    }

    //called by google API
    @Override
    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
        Profile.getInstance().setProfile(googleSignInResult.getSignInAccount());
    }
}
