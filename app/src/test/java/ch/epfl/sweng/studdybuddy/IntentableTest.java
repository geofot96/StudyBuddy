package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.tools.Intentable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IntentableTest {
    @Test
    public void testLaunch() {
        AppCompatActivity activity = mock(AppCompatActivity.class);
        Intent intent = mock(Intent.class);
        Intentable i = new Intentable(activity, intent);
        i.launch();
        verify(activity, times(1)).startActivity(intent);
    }
}
