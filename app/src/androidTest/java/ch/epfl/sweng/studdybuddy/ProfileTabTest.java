package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileTabTest
{

   /* @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);
*/
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
    @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);

    ProfileTab profile;
    ReferenceWrapper fb;

    @Before
    public void setup() throws  Exception{

        /*DatabaseReference ref = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        ReferenceWrapper firebase = new FirebaseReference(ref);
        List<Pair> tuples = Arrays.asList(new Pair("Default", "ICC"), new Pair("Default", "ICC"));
        DataSnapshot userCourse= mock(DataSnapshot.class), userGroup = mock(DataSnapshot.class), groups = mock(DataSnapshot.class);

        //values we work with when we call OnDataChange
        when(userCourse.getValue(Pair.class)).thenReturn((tuples.get(0)));
        when(userGroup.getValue(Pair.class)).thenReturn((tuples.get(1)));
        when(groups.getValue(Group.class)).thenReturn(new Group(2, new Course("ICC"),"FR"));



        //For the list in OndataChange
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList(userCourse));
        //For  firebase.select("")
        when(ref.child(anyString())).thenReturn(ref);


        ProfileTab profile = mActivityRule.getActivity();
        profile.setDB(firebase);
        profile.setCoursesUp().onDataChange(dataSnapshot);*/
        List<Group> groups = new LinkedList<>();
        groups.addAll(Arrays.asList(blankGroupWCourse("Linear Algebra"), blankGroupWCourse("Algorithms")));
        mActivityRule.getActivity().setUserGroups(groups);
    }

    private Group blankGroupWCourse(String course) {
        return new Group(1, new Course(course), "");
    }

    /*@Test
    public void profileListHasCorrectData() {

        //onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        //onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Algorithms"))));
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Algorithms"))));
    }*/

    @Test
    public void setCourseAndGroupsWorks(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("ICC"))));
    }
}
