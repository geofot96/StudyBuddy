package ch.epfl.sweng.studdybuddy.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

/**
 * A simple {@link Fragment} subclass.
 */
public class MergedCalendarFragment extends Fragment
{

    GridLayout calendarGrid;
    ArrayList<boolean[][]> calendars;
    //Temporary dummy inputs
    boolean c1[][]={{true,true,true,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,true},
            {false,false,false,false,false,false,false,false,false,true,false},
            {false,false,false,false,false,false,false,false,true,false,false},
            {false,false,false,false,false,false,false,true,false,false,false},
            {false,false,false,false,false,false,true,false,false,false,false},
            {false,false,false,false,false,true,false,false,false,false,false}};
    boolean c2[][]=c1;

    boolean c3[][]=c1;


    public MergedCalendarFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_merged_calendar, container, false);

        calendars=new ArrayList<>();
        //TODO Temporary, remove after
        c2[0][1]=true;
        c2[0][2]=true;
        c3[0][2]=true;
        calendars.add(c1);
        calendars.add(c2);
        calendars.add(c3);
        calendarGrid = (GridLayout) v.findViewById(R.id.calendarGrid);
        Button button = v.findViewById(R.id.confirmSlots);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), ProfileTab.class);
                startActivity(intent);
            }
        });
        mergeCalendars(calendarGrid);

        return v;
    }

    public void editColor(int calendarNumber, int i)
    {
        CardView cardView = (CardView) calendarGrid.getChildAt(i);
        int timesSelected=0;
        for (int j=0; j<calendars.size();j++)
        {
            if(calendars.get(j)[(i%8)-1][i/8])timesSelected++;

        }

        if(timesSelected==0)
        {
            cardView.setCardBackgroundColor(Color.WHITE);
        }

        else
        {
            double gradient=(((255.0/calendarNumber)*(calendarNumber-timesSelected))+60)%255;
            cardView.setCardBackgroundColor(Color.rgb(0,(int)gradient,0));
        }
    }

    private void mergeCalendars(GridLayout calendarGrid)
    {
        int calendarNumber=calendars.size();
        for(int i = 0; i < calendarGrid.getChildCount(); i++) {
            if(i%8!=0) {
                editColor(calendarNumber, i);
            }
        }
    }

}
