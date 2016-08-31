package com.suveraapp;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

/**
 * Created by williammeaton on 31/08/2016.
 */
public class Profile implements OnCompleteListener<AuthResult>{
    private static Profile instance;
    private boolean logged;
    private boolean authenticated;
    private String familyName;
    private FirebaseAuth mAuth;

    private Profile(){
        logged = false;
        authenticated = false;
        familyName = "";
        mAuth = FirebaseAuth.getInstance();
    }

    public static Profile getInstance(){
        if(instance == null){
            instance = new Profile();
        }
        return instance;
    }

    public boolean isLoggedIn(){
        return logged;
    }

    public void setLoggedIn(boolean b){
        this.logged = b;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setProfile(GoogleSignInAccount gsi){
        if(gsi!= null) {
            this.logged = true;
            this.familyName = gsi.getFamilyName();
            firebaseAuthWithGoogle(gsi);
        }
    }

    public void logout(){
        instance = new Profile();
        FirebaseAuth.getInstance().signOut();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("FireBaseGoogle", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            authenticated = true;
        }else{
            authenticated = false;
        }
    }
}
