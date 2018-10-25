package ch.epfl.sweng.studdybuddy.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import ch.epfl.sweng.studdybuddy.R;

public class CalendarActivity extends AppCompatActivity
{

    GridLayout calendarGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarGrid = (GridLayout) findViewById(R.id.calendarGrid);

        setOnToggleBehavior(calendarGrid);
    }

    private void setOnToggleBehavior(GridLayout calendarGrid)
    {
        for(int i = 0; i < calendarGrid.getChildCount(); i++)
        {
            CardView cardView = (CardView) calendarGrid.getChildAt(i);

            cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(cardView.getCardBackgroundColor().getDefaultColor() == -1)
                    {
                        cardView.setCardBackgroundColor(Color.GREEN);
                    }
                    else
                    {
                        cardView.setCardBackgroundColor(Color.WHITE);
                    }
                }
            });
        }
    }
}
