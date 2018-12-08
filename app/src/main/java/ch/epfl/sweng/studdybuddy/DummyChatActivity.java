package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

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
                //Intent galleryIntent = new Intent();
                //galleryIntent.setType("image/*");
                //galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), PICK_IMAGE_REQUEST);
                onActivityResult(PICK_IMAGE_REQUEST, RESULT_OK, new Intent());
            }
        };
    }
}
//Uri.parse("android.resource://ch.epfl.sweng.studdybuddy/" + R.drawable.george_logo)