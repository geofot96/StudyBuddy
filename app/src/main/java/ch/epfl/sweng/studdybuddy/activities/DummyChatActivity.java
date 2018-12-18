package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.ChatActivity;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;

public class DummyChatActivity extends ChatActivity
{
    @Override
    public FirebaseReference initRef()
    {
        return (FirebaseReference) new FirebaseReference(FirebaseDatabase.getInstance().getReference().child("test_chat").child("1"));
    }

    @Override
    protected View.OnClickListener getFabListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ref.setVal(new ChatMessage("this is a test", "the_name_is_mr_potato", ""));
                displayChatMessages();
            }
        };
    }

    @Override
    protected Uri getFilePath(Intent data)
    {
        return Uri.parse("android.resource://ch.epfl.sweng.studdybuddy/" + R.drawable.george_logo);
    }

    @Override
    protected View.OnClickListener getGalleryImage()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onActivityResult(PICK_IMAGE_REQUEST, RESULT_OK, new Intent());
            }
        };
    }

    @Override
    protected View.OnClickListener getCameraListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.george_logo);


                Intent in1 = new Intent();
                in1.putExtra("data",bmp);


                onActivityResult(OPEN_CAMERA_REQUEST, RESULT_OK, in1);
            }
        };
    }

    @Override
    protected CharSequence getDate(ChatMessage model)
    {
        return "date";
    }

    @Override
    protected void pushMessage(EditText inputText, FirebaseReference reference, String groupsID, String downloadURI)
    {
        //do nothing
    }
}