package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetabaseTest {

    DataSnapshot userCourses = mock(DataSnapshot.class);
    DatabaseReference testref = mock(DatabaseReference.class);
    MetaGroup mb = new MetaGroup(new FirebaseReference(testref));

    @Before
    public void setup() {
        List<DataSnapshot> ucs = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        List<Pair> userCourse = Arrays.asList(new Pair("Robert", "cuisine"), new Pair("Albert", "royalty"), new Pair("Frédé", "clp"), new Pair("Frédé", "parcon"), new Pair("Frédé", "parcon"));
        for(int i = 0; i < ucs.size(); ++i)
            when(ucs.get(i).getValue(Pair.class)).thenReturn(userCourse.get(i));
        when(userCourses.getChildren()).thenReturn(ucs);
    }
    @Test
    public void getUserCoursesInvalidUser() {
        List<String> travisCourse = new ArrayList<>();
        mb.getUserCourses("Travis", travisCourse).onDataChange(userCourses);
        assertTrue(travisCourse.size() == 0);
    }

    @Test
    public void getUserCoursesWhenDoublon() {
        List<String> fredeCourses = new ArrayList<>();
        mb.getUserCourses("Frédé", fredeCourses).onDataChange(userCourses);
        assertTrue(fredeCourses.size() == 2);
        assertTrue(fredeCourses.contains("clp"));
        assertTrue(fredeCourses.contains("parcon"));
    }
}
