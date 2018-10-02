package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DisplayPhotoTaken extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bitmap imageBitmap = (Bitmap) intent.getExtras().get("data");
        ImageView imageTaken=(ImageView)findViewById(R.id.imageView);
        imageTaken.setImageBitmap(imageBitmap);
    }
}
