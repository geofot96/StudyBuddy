package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
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

    private boolean colorCheck(CardView cardView, boolean picked)
    {
        if (cardView.getCardBackgroundColor().getDefaultColor() != -1)
        {
            picked = true;
        }
        return picked;
    }

    public  void confirmSlots(View view)
    {
        for(int i = 0; i < calendarGrid.getChildCount(); i++)
        {
            if(i%8!=0) {
                boolean picked = false;
                CardView cardView = (CardView) calendarGrid.getChildAt(i);
                picked = colorCheck(cardView, picked);
                int column=i%8;
                int row=i/8;
                pickedSlots[column-1][row] = picked;
            }

        }

        Intent intent = new Intent(this, ProfileTab.class);
        startActivity(intent);
    }
}
