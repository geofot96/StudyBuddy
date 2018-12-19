package ch.epfl.sweng.studdybuddy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.DummyChatActivity;
import ch.epfl.sweng.studdybuddy.activities.ChatActivity;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)

public class ChatActivityTests
{
    Intent intent;

    @Rule
    public ActivityTestRule<DummyChatActivity> DummyChatActivityIntentRule = new ActivityTestRule<>(DummyChatActivity.class, false, false);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA  );

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
    public void messageTest()
    {
        DummyChatActivityIntentRule.launchActivity(intent);
        onView(withId(R.id.fab)).perform(click());

        List<ChatMessage> list = new ArrayList<>();

        FirebaseReference reference = new FirebaseReference();
        //Thread.sleep(500);
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

    @Test
    public void testFabListener() {
        EditText inputText = mock(EditText.class);
        FirebaseReference ref = new FirebaseReference();
        Editable editable = mock(Editable.class);
        DummyChatActivity chatActivity = mock(DummyChatActivity.class);


        when(chatActivity.fabListener(inputText, ref, "fabTest", "FabTestURI")).thenCallRealMethod();
        inputText.setText("text");
        when(inputText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn("text");
        chatActivity.fabListener(inputText, ref, "fabTest", "FabTestURI");
    }


    @Test
    public void galleryTest()
    {
        DummyChatActivityIntentRule.launchActivity(intent);
        onView(withId(R.id.gallery)).perform(click());
        DummyChatActivityIntentRule.finishActivity();
    }

    @Test
    public void cameraTest()
    {
        DummyChatActivityIntentRule.launchActivity(intent);
        onView(withId(R.id.camera)).perform(click());
        DummyChatActivityIntentRule.finishActivity();
    }
}
