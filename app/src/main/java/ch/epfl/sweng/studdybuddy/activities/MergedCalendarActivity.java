package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
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
        calendarGrid = (GridLayout) findViewById(R.id.calendarGrid);
        Button button = findViewById(R.id.confirmSlots);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MergedCalendarActivity.this, ProfileTab.class);
                startActivity(intent);
            }
        });
        mergeCalendars(calendarGrid);
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
