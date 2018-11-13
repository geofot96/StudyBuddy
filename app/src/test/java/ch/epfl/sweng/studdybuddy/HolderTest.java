package ch.epfl.sweng.studdybuddy;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.Holder;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
        when(tx.getText()).thenReturn("TA MERE");
        h = new Holder(v, -1);
    }
    @Test
    public void holderGetter() {
        assertEquals("TA MERE", h.get());
    }

    @Test
    public void holderBind() {
        h.bind("NTM");
        verify(tx, times(1)).setText("NTM");
    }
}
