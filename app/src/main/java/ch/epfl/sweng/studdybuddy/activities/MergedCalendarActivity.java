package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.GroupsActivity;
import ch.epfl.sweng.studdybuddy.R;


public class MergedCalendarActivity extends AppCompatActivity
{

    GridLayout calendarGrid;
    ArrayList<boolean[][]> calendars;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendars=new ArrayList<>();
        boolean c1[][]={{true,true,true,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false}};
        boolean c2[][]={{true,true,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false}};
        boolean c3[][]={{true,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false}};
        calendars.add(c1);
        calendars.add(c2);
        calendars.add(c3);
        calendarGrid = (GridLayout) findViewById(R.id.calendarGrid);

        mergeCalendars(calendarGrid);
    }


    private void mergeCalendars(GridLayout calendarGrid)
    {
        int claendarNumber=calendars.size();
        for(int i = 0; i < calendarGrid.getChildCount(); i++) {
            if(i%8!=0) {
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
                    double gradient=(((255.0/claendarNumber)*(claendarNumber-timesSelected))+60)%255;
                    cardView.setCardBackgroundColor(Color.rgb(0,(int)gradient,0));
                }
            }
        }

    }

}
