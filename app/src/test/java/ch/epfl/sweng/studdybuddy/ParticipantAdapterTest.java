package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Holder;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParticipantAdapterTest {
    List<User> participants = Arrays.asList(new User("a", "b"));

    @Test
    public void ParticipantsCountTest(){
        ParticipantAdapter ad = new ParticipantAdapter(new ArrayList<>());
        ParticipantAdapter ad2 = new ParticipantAdapter(participants);
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }

    @Test
    public void partAdBinCallsBind() {
        Holder h = mock(Holder.class);
        ParticipantAdapter adapter = new ParticipantAdapter(participants);
        adapter.onBindViewHolder(h, 0);
        verify(h, times(1)).bind("a");
    }


}
