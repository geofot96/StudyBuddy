package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import ch.epfl.sweng.studdybuddy.GroupsActivity;
import ch.epfl.sweng.studdybuddy.ProfileTab;
import ch.epfl.sweng.studdybuddy.R;


public class CalendarActivity extends AppCompatActivity
{

    GridLayout calendarGrid;
    boolean[][] pickedSlots=new boolean[7][11];

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

            if(i%8!=0) {//Hours shouldn't be clickable
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                            cardView.setCardBackgroundColor(Color.GREEN);


                        } else {
                            cardView.setCardBackgroundColor(Color.WHITE);
                        }
                    }
                });
            }
        }
    }
    public  void confirmSlots(View view)
    {
        for(int i = 0; i < calendarGrid.getChildCount(); i++)
        {
            if(i%8!=0) {
                boolean picked = false;
                CardView cardView = (CardView) calendarGrid.getChildAt(i);
                if (cardView.getCardBackgroundColor().getDefaultColor() != -1) picked = true;
                pickedSlots[(i%8)-1][i/8] = picked;
            }

        }

        Intent intent = new Intent(this, ProfileTab.class);
        startActivity(intent);
    }
}
