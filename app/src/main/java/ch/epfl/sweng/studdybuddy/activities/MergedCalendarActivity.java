package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
import ch.epfl.sweng.studdybuddy.R;


public class MergedCalendarActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendars=new ArrayList<>();
       //TODO Temporary, remove after
        c2[0][1]=true;
        c2[0][2]=true;
        c3[0][2]=true;
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
