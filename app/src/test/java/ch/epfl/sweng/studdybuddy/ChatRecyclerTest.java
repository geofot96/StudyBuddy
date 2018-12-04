package ch.epfl.sweng.studdybuddy;

import android.widget.TextView;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.BasicRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.ChatRecyclerAdapter;

import static ch.epfl.sweng.studdybuddy.util.CoreFactory.groups1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatRecyclerTest {
    @Test
    public void onBindViewHolder() {
        BasicRecyclerAdapter.MyViewHolder holder = mock(BasicRecyclerAdapter.MyViewHolder.class, Mockito.RETURNS_DEEP_STUBS);
        List<Group> groups = groups1();
        MetaGroup mg = mock(MetaGroup.class, Mockito.RETURNS_DEEP_STUBS);
        FirebaseReference fr  = mock(FirebaseReference.class, Mockito.RETURNS_DEEP_STUBS);
        ChatRecyclerAdapter adapter = new ChatRecyclerAdapter(mg, fr, groups, "n");
        ChatRecyclerAdapter adapter2 = new ChatRecyclerAdapter(mg, fr, groups, groups.get(0).getAdminID());
        TextView placeHolder = mock(TextView.class, Mockito.RETURNS_DEEP_STUBS);
        when(holder.getAdmin()).thenReturn(placeHolder);
        when(holder.getGroupCourseTextView()).thenReturn(placeHolder);
        when(holder.getGroupCreationDateTextView()).thenReturn(placeHolder);
        when(holder.getGroupLanguageTextView()).thenReturn(placeHolder);
        adapter.onBindViewHolder(holder, 0);
        adapter2.onBindViewHolder(holder, 0);
    }
}
