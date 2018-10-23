package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class AccountTest {
    @Mock
    GoogleSignInAccount googleAccount;

    @Mock
    FirebaseUser fbAccount;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void CreateAccountFromNullGoogleAccountReturnNull(){
        Account testAccount = Account.from((GoogleSignInAccount) null);
        assertThat(testAccount, is((Account)null));
    }

    @Test
    public void CreateAccountFromNullFirebaseAccountReturnNull(){
        Account testAccount = Account.from((FirebaseUser) null);
        assertThat(testAccount, is((Account)null));
    }

    @Test
    public void createAccountFromNonNullGoogleAccount(){
        when(googleAccount.getDisplayName()).thenReturn("Yann");
        when(googleAccount.getId()).thenReturn("TestID");
        when(googleAccount.getIdToken()).thenReturn("TestIdToken");
        Account testAccount = Account.from(googleAccount);
        assertThat(testAccount.getDisplayName(), is("Yann"));
        assertThat(testAccount.getId(), is("TestID"));
        assertThat(testAccount.getIdToken(), is("TestIdToken"));
    }

    @Test
    public void createAccountFromNonNullFBAccount(){
        when(fbAccount.getDisplayName()).thenReturn("Yann");
        when(fbAccount.getUid()).thenReturn("TestID");
        Account testAccount = Account.from(fbAccount);
        assertThat(testAccount.getDisplayName(), is("Yann"));
        assertThat(testAccount.getId(), is("TestID"));
        assertThat(testAccount.getIdToken(), is((String)null));
    }
}
