package ch.epfl.sweng.studdybuddy;

import com.google.firebase.auth.FirebaseAuth;

public class AuthService {
    private FirebaseAuth mAuth;
    AuthService(){
        this.mAuth = FirebaseAuth.getInstance();
    }

    AuthService(FirebaseAuth mAuth){
        this.mAuth = mAuth;
    }

    public FirebaseAuth getmAuth(){
        return mAuth;
    }
}
