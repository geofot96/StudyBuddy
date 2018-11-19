package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;

public class ChatActivity extends AppCompatActivity{
    private FirebaseListAdapter<ChatMessage> adapter;
    String groupID;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras=getIntent().getExtras();
        if(extras!=null)groupID=extras.getString("GroupID");
        else {
            Toast.makeText(this,
                    "Group not found in database",
                    Toast.LENGTH_LONG).show();
            finish();
        }
        displayChatMessages();
        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().child("chat").child(groupID).push()
                        .setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");
            }
        });

    }
    public void displayChatMessages()
    {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child("chat").child(groupID)) {
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
