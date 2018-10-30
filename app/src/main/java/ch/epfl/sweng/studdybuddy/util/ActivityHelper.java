package ch.epfl.sweng.studdybuddy.util;

import android.view.View;
import android.widget.AutoCompleteTextView;

public class ActivityHelper {
    public static View.OnClickListener showDropdown(AutoCompleteTextView tv) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                tv.showDropDown();
            }
        };
    }
}
