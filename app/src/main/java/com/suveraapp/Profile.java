package com.suveraapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Profile implements OnCompleteListener<AuthResult> {
    private static Profile instance;
    private boolean logged;
    private boolean authenticated;
    private String familyName;
    private String displayName;
    private FirebaseAuth mAuth;

    private Profile() {
        logged = false;
        authenticated = false;
        familyName = "";
        displayName = "";
        mAuth = FirebaseAuth.getInstance();
    }

    public static Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return logged;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setLoggedIn(boolean b) {
        this.logged = b;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setProfile(GoogleSignInAccount gsi) {
        if (gsi != null) {
            this.logged = true;
            this.familyName = gsi.getFamilyName();
            this.displayName = gsi.getDisplayName();
            firebaseAuthWithGoogle(gsi);
            Log.d("Firebase", "Login success");
        } else {
            Log.d("Firebase", "Login failed");
        }
    }

    public void reset() {
        instance = new Profile();
        FirebaseAuth.getInstance().signOut();

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            authenticated = true;
            Log.d("Firebase", "Account authenticated");
        } else {
            authenticated = false;
            Log.d("Firebase", "Account authentication failed");
        }
    }
}
