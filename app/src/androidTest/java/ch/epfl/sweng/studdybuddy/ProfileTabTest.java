package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

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
    Course c =  new Course("Maths");
    Group group = new Group(10,c, "FR");

    @Before
    public void setup() throws  Exception{
   /*     DatabaseReference ref = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        ReferenceWrapper firebase = new FirebaseReference(ref);
        List<Pair> tuples = Arrays.asList(new Pair("Default", "ICC"), new Pair("Default", "ICC"));
        DataSnapshot userCourse= mock(DataSnapshot.class), userGroup = mock(DataSnapshot.class), groups = mock(DataSnapshot.class);
        when(userCourse.getValue()).thenReturn(Arrays.asList(tuples.get(0)));
        when(userGroup.getValue()).thenReturn(tuples.get(1));
        when(groups.getValue()).thenReturn(new Group(2, new Course("ICC"),"FR"));
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList(userGroup, userCourse));
        when(ref.child(anyString())).thenReturn(ref);
        ProfileTab profile = mActivityRule.getActivity();
        DataSnapshot courses = mock(DataSnapshot.class);
        when(courses.getValue()).thenReturn(Arrays.asList("ICC"));
      //  profile.setDB(firebase);
        profile.setCoursesUp().onDataChange(courses);

*/
    }


    @Test
    public void setCourseAndGroupsWorks(){
    /*    try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("ICC"))));*/
    }
}
