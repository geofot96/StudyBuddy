package ch.epfl.sweng.studdybuddy;

import android.app.Notification;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.ChatActivity;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class ChatActivityTests
{
    Intent intent;


    @Rule
    public ActivityTestRule<DummyChatActivity> DummyChatActivityIntentRule = new ActivityTestRule<>(DummyChatActivity.class, false, false);

    @Before
    public void setIntent()
    {
        intent = new Intent();
        intent.putExtra(Messages.maxUser, 1);
        intent.putExtra(Messages.userID, Messages.TEST);
        intent.putExtra(Messages.groupID, Messages.TEST);
        FirebaseDatabase.getInstance().getReference().child("test_chat").removeValue();

    }
    @Test
    public void thisIsATest()
    {
        DummyChatActivityIntentRule.launchActivity(intent);
        onView(withId(R.id.fab)).perform(click());

        List<ChatMessage> list = new ArrayList<>();

        FirebaseReference reference = new FirebaseReference();

        reference.select("test_chat").getAll(ChatMessage.class, new Consumer<List<ChatMessage>>()
        {
            @Override
            public void accept(List<ChatMessage> messages)
            {
                list.clear();
                list.addAll(messages);
            }
        });
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e){}
        assertEquals(1, list.size());
        DummyChatActivityIntentRule.finishActivity();
    }
    
}
