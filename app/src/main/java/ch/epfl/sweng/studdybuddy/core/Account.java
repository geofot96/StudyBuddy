package ch.epfl.sweng.studdybuddy.core;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * A class representing the data structure of an account
 */
public class Account {
    private String displayName;
    private String uid;
    private String idToken;

    /**
     * Full constructor
     * @param displayName THe name that will be displayed
     * @param uid The uniqueID of this account
     * @param idToken The token of the id
     */
    private Account(String displayName, String uid, String idToken) {
        this();
        this.displayName = displayName;
        this.uid = uid;
        this.idToken = idToken;
    }

    /**
     * Empty constructor
     */
    public Account() {
    }

    /**
     * Constructor form  GoogleSigninAccount
     * @param gsia The GoogleSignInAccount from where the necessary information will be extracted
     * @return The newly constructed account
     */
    public static Account from(GoogleSignInAccount gsia) {
        if (gsia == null) {
            return null;
        }
        return new Account(gsia.getDisplayName(), gsia.getId(), gsia.getIdToken());
    }
    /**
     * Constructor form  FirebaseUser
     * @param fbu The FirebaseUser from where the necessary information will be extracted
     * @return The newly constructed account
     */
    public static Account from(FirebaseUser fbu) {
        if (fbu == null) {
            return null;
        }
        return new Account(fbu.getDisplayName(), fbu.getUid(), null);
    }

    /**
     *
     * @return the name to be displayed
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @return The uniqueID of this account
     */
    public String getId() {
        return uid;
    }

    /**
     *
     * @return The token of the id
     */
    public String getIdToken() {
        return idToken;
    }
}
