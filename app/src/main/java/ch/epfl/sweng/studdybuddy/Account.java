package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Account {
    private String displayName;
    private String uid;
    private String idToken;
    public Account(String displayName, String uid, String idToken){
        this.displayName = displayName;
        this.uid = uid;
        this.idToken = idToken;
    }

    public static Account from(GoogleSignInAccount gsia){
        if (gsia == null){
            return null;
        }
        return new Account(gsia.getDisplayName(), gsia.getId(), gsia.getIdToken());
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
