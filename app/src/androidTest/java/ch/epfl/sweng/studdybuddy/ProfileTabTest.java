package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProfileTabTest
{

   /* @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);
*/
    @Test
    public void emptyTest(){

    }

   /* @Before
    public void setup(){
    }*/


    //public static final String mockCourse = "Algorithms";




/*    @Test
    public void signOutButtonLeadsToLogin() {
        onView(withId(R.id.signout)).perform(click());
        intended(toPackage(LoginActivity.class)).hasComponent();
    }


    /*@Test
    public void signOutButtonDisconnects() {

    }


    /*@Test
    public void profileListHasCorrectData() {

        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Algorithms"))));
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Algorithms"))));
    }
*/

  /*  public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);

    @Before
    public void setup(){
        DatabaseReference db = Mockito.mock(DatabaseReference.class);
        ReferenceWrapper fb = Mockito.mock(FirebaseReference.class);
        Mockito.when(fb.select(Mockito.anyString())).thenReturn(fb);
        Consumer<List<Pair>> g = Mockito.any();
        when(fb.getAll(any(Pair.class), g));
        mActivityRule.getActivity().setDB(fb);
       // Mockito.when(db.addValueEventListener(any(Group.class), any(Consumer<>)))


    }*/

}
