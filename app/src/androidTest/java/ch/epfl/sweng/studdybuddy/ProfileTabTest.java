package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
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
    @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);

    ProfileTab profile;
    ReferenceWrapper fb;
    Course c =  new Course("Maths");
    Group group = new Group(10,c, "FR");
    @Before
    public void setup() throws  Exception{

        profile = mActivityRule.getActivity();
        fb = Mockito.mock(FirebaseReference.class);
        Mockito.when(fb.select(Mockito.anyString())).thenReturn(fb);
        profile.setDB(fb);
        Consumer<Group>  groupConsumer= profile.groupConsumer();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                profile.groupConsumer().accept(group);
                return null;
            }
        }).when(fb).get(Group.class, groupConsumer);

        Consumer<List<Pair>> courseConsumer = profile.userCourseConsumer();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                List<Pair> pairs = new ArrayList<>();
                pairs.add(new Pair("Default", c.getCourseID().toString()));
                profile.userGroupConsumer().accept(pairs);
                return null;
            }
        }).when(fb).getAll(Pair.class,  profile.userCourseConsumer());


        Consumer<List<Pair>> userGroup = profile.userGroupConsumer();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                List<Pair> pairs = new ArrayList<>();
                pairs.add(new Pair("Default","b"));
                profile.userGroupConsumer().accept(pairs);
                return null;
            }
        }).when(fb).getAll(Pair.class,  profile.groupConsumer );
    }

    @Test
    public void test(){



      //  profile.setCoursesUp();
        profile.setGroupsUp();


        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Maths"))));

    }

}
