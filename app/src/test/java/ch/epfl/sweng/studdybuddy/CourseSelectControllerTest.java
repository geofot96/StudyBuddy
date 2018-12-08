package ch.epfl.sweng.studdybuddy;

import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Holder;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;

import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.deleteCourseOnSwipe;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.onClickAddCourse;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.onSwiped_;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.resetSelectViews;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateClickable;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateCoursesOnDone;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseSelectControllerTest {
    List<String> cSelect = new ArrayList<>();
    Button b = mock(Button.class);
    RecyclerView.Adapter ad = mock(RecyclerView.Adapter.class);
    AdapterView<ArrayAdapter> parent = mock(AdapterView.class);
    ArrayAdapter a = mock(ArrayAdapter.class);
    Consumer<String> cs = mock(Consumer.class);
    Button doneButton = mock(Button.class);
    @Before
    public void setup() {
        cSelect.add("c");
        when(parent.getAdapter()).thenReturn(a);
        when(a.getItem(anyInt())).thenReturn("c");
    }

    @Test
    public void testUpdateCoursesOnDone() {
        User u = new User("-", "id");
        MetaGroupAdmin mga = mock(MetaGroupAdmin.class, RETURNS_DEEP_STUBS);
        Intentable i = mock(Intentable.class, RETURNS_DEEP_STUBS);
        updateCoursesOnDone(u, cSelect, mga, i).onClick(mock(View.class, RETURNS_DEEP_STUBS));
        verify(i, times(1)).launch();
        verify(mga, times(1)).putAllCourses(cSelect, "id");
    }
    @Test
    public void testDeleteCourseOnSwipe() {
        AdapterAdapter adad = mock(AdapterAdapter.class);
        ItemTouchHelper ith = deleteCourseOnSwipe(cSelect, b, adad);
        RecyclerView.ViewHolder vh = mock(RecyclerView.ViewHolder.class, RETURNS_DEEP_STUBS);
        Holder cc = mock(Holder.class, RETURNS_DEEP_STUBS);
        when(cc.get()).thenReturn("c");
        onSwiped_(cSelect, b, adad, cc);
        verify(adad, times(1)).update();
        verify(b, times(1)).setEnabled(false);
    }

    @Test
    public void testResetSelectViews() {
        AutoCompleteTextView ac = mock(AutoCompleteTextView.class, RETURNS_MOCKS);
        InputMethodManager imm = mock(InputMethodManager.class);
        IBinder token = mock(IBinder.class);
        when(ac.getWindowToken()).thenReturn(token);
        resetSelectViews(b, ac, imm).accept(null);
        verify(b, times(1)).setEnabled(true);
        verify(ac, times(1)).setText("");
        verify(imm, times(1)).hideSoftInputFromWindow(token, 0);
    }

    @Test
    public void testOnClickAddCourseSuccess() {
        onClickAddCourse(cSelect, cs).onItemClick(parent, mock(View.class), 0, (long)0);
        verify(cs, times(0)).accept("c");
    }

    @Test
    public void testOnClickAddCourseFailure() {
        onClickAddCourse(new ArrayList<>(), cs).onItemClick(parent, mock(View.class), 0, (long)0);
        verify(cs, times(1)).accept("c");
    }

    @Test
    public void testUpdateClickableEmpty() {
        updateClickable(doneButton, new ArrayList<>()).update();
        verify(doneButton, times(1)).setEnabled(false);
    }

    @Test
    public void testUpdateClickable() {
        updateClickable(doneButton, cSelect).update();
        verify(doneButton, times(1)).setEnabled(true);
    }
}
