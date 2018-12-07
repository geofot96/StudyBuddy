package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Account;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.GoogleSigninController.callbackUserFetch;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoogleSigninControllerTest {
    ReferenceWrapper rw = mock(ReferenceWrapper.class, RETURNS_SELF);
    Account acct = mock(Account.class);
    StudyBuddy app = mock(StudyBuddy.class);
    Intentable courseSelect = mock(Intentable.class);
    Consumer<User> callback = callbackUserFetch(rw, acct, app, courseSelect);
    @Test
    public void testCallbackUserFetchNull() {
        callback.accept(null);
        User d = new User("MJ", "23");
        when(rw.select(anyString())).thenReturn(rw);
        when(acct.getId()).thenReturn("23");
        when(acct.getDisplayName()).thenReturn("MJ");
        when(app.getAuthendifiedUser()).thenReturn(d);
        verify(rw, times(1)).select("users");
        //verify(rw, times(1)).select("23");
        //verify(rw, times(1)).setVal(d);
        verify(app, times(1)).disableTravis();
        verify(app, times(1)).setAuthendifiedUser(any(User.class));
        verify(courseSelect, times(1)).launch();
    }

    @Test
    public void testCallbackUserFetchValid() {
        User u = new User("LBJ", "23");
        callback.accept(u);
        verify(app, times(1)).setAuthendifiedUser(u);
        verify(courseSelect, times(1)).launch();
    }
}
