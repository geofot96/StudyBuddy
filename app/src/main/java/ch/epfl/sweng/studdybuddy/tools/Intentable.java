package ch.epfl.sweng.studdybuddy.tools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Intentable {
    AppCompatActivity activity;
    Intent intent;
    public Intentable(AppCompatActivity activity, Intent intent) {
        this.activity = activity;
        this.intent = intent;
    }
    public void launch() {
        activity.startActivity(intent);
    }
}