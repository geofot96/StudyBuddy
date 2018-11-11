package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.util.ParticipantAdapter;

import static junit.framework.TestCase.assertTrue;

public class ParticipantAdapterTest {

    @Test
    public void ParticipantsCountTest(){
        ParticipantAdapter ad = new ParticipantAdapter(new ArrayList<>());
        ParticipantAdapter ad2 = new ParticipantAdapter(Arrays.asList(new User("a", "b")));
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }


}
