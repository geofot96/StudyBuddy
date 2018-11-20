package ch.epfl.sweng.studdybuddy;

import android.view.View;

import com.google.firebase.database.DatabaseReference;
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
                ref.setVal(new ChatMessage("this is a test", "the_name_is_mr_potato"));
            }
        };
    }
}
