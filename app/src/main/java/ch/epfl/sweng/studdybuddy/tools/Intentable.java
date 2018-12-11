package ch.epfl.sweng.studdybuddy.tools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Intentable {
    Context ctx;
    Intent intent;
    public Intentable(Context ctx, Intent intent) {
        this.ctx = ctx;
        this.intent = intent;
    }
    public Intentable(Context ctx, Class activity) {
        this(ctx, new Intent(ctx, activity));
    }
    public void launch() {
        ctx.startActivity(intent);
    }
}