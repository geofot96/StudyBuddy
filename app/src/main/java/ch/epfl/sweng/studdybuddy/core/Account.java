package ch.epfl.sweng.studdybuddy.core;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class Account {
    private String displayName;
    private String uid;
    private String idToken;


    private Account(String displayName, String uid, String idToken){
        this();
        this.displayName = displayName;
        this.uid = uid;
        this.idToken = idToken;
    }

    public Account(){}

    public static Account from(GoogleSignInAccount gsia){
        if (gsia == null){
            return null;
        }
        return new Account(gsia.getDisplayName(), gsia.getId(), gsia.getIdToken());
    }

    public static Account from(FirebaseUser fbu){
        if(fbu == null){
            return null;
        }
        return new Account(fbu.getDisplayName(), fbu.getUid(), null);
    }

    public String getDisplayName(){
        return displayName;
    }
    public String getId(){
        return uid;
    }
    public String getIdToken(){
        return idToken;
    }
}
