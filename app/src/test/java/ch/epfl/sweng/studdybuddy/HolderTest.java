package ch.epfl.sweng.studdybuddy;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.tools.Holder;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HolderTest {
    View v = mock(View.class);
    TextView tx = mock(TextView.class);
    Holder h;
    @Before
    public void setup() {

        when(v.findViewById(-1)).thenReturn(tx);
        when(tx.getText()).thenReturn("test");
        h = new Holder(v, -1);
    }
    @Test
    public void holderGetter() {
        assertEquals("test", h.get());
    }

    @Test
    public void holderBind() {
        h.bind("test2");
        verify(tx, times(1)).setText("test2");
    }
}
