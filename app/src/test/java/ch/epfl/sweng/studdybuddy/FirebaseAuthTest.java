package ch.epfl.sweng.studdybuddy;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FirebaseAuthTest {
    FirebaseAuthManager fam;

    Activity activity = mock(Activity.class);
    GoogleSignInClient gsic = mock(GoogleSignInClient.class);
    FirebaseAuth fauth = mock(FirebaseAuth.class);
    @Before
    public void setup() {
        fam = new FirebaseAuthManager(fauth, gsic, activity);
        //when(fauth.getCurrentUser()).thenReturn()
    }

    @Test
    public void testLogout() {
        fam.logout();
        verify(fauth, times(1)).signOut();
    }

    @Test
    public void testGetCurrentUser() {
        fam.getCurrentUser();
        verify(fauth, times(1)).getCurrentUser();
    }
}
