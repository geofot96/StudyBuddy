package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.services.notifications.MyFirebaseMessaging;
import ch.epfl.sweng.studdybuddy.util.Messages;


public class ChatActivity extends AppCompatActivity{
    String groupID;
    public FirebaseReference ref;


    private static DatabaseReference mNotificationsRef = FirebaseDatabase.getInstance().getReference("notifications");
    private static List<User> users = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            groupID=extras.getString(Messages.groupID);
        }
        else {
            Toast.makeText(this,
                    "Group not found in database",
                    Toast.LENGTH_LONG).show();
            finish();
        }
        displayChatMessages();
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        this.ref = initRef();

        MetaGroup mGp = new MetaGroup();
        mGp.getGroupUsers(groupID, users);

        fab.setOnClickListener(getFabListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        //we avoid receiving notifications from people we are currently chatting with
        MyFirebaseMessaging.setCurrentGroupID(groupID);
    }

    @Override
    protected void onStop(){
        super.onStop();
        //once we don't see the chat box anymore, we tell to myFirebaseMessaging to send us
        //the further notification from this chat box
        MyFirebaseMessaging.setCurrentGroupID(MyFirebaseMessaging.NO_GROUP);
    }
    public static View.OnClickListener fabListener(EditText input, FirebaseReference ref, FirebaseAuth auth, String groupID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().toString().trim().length() > 0) {
                    sendMessage(input, ref, auth.getCurrentUser(), groupID);
                }
            }
        };
    }

    private static void sendMessage(EditText input, FirebaseReference ref, FirebaseUser auth, String groupID) {
        String msg = input.getText().toString();
        ref.select(Messages.FirebaseNode.CHAT).select(groupID)
                .push(new ChatMessage(msg, auth.getDisplayName()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap<String, String> notification = new HashMap<>();
                        notification.put("FROM", auth.getUid());
                        notification.put("GROUP", groupID);
                        notification.put("TYPE", "message");
                        for(User u: users) {
                            if (!u.getUserID().getId().equals(auth.getUid())) {
                                mNotificationsRef.child(u.getUserID().getId()).push().setValue(notification);
                            }
                        }
                    }
                });

        input.setText("");
    }


    @NonNull
    protected View.OnClickListener getFabListener()
    {
        return fabListener((EditText)findViewById(R.id.input), ref, FirebaseAuth.getInstance(), groupID);
    }

    public FirebaseReference initRef(){
        return new FirebaseReference();
    }
    public void displayChatMessages()
    {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        FirebaseListAdapter<ChatMessage> adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message,
                FirebaseDatabase.getInstance().getReference().child(Messages.FirebaseNode.CHAT).child(groupID)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                if (!model.getMessageText().isEmpty()) {
                    // Get references to the views of message.xml
                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());

                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));
                }
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}
