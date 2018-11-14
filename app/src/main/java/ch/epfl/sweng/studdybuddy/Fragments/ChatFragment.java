package ch.epfl.sweng.studdybuddy.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment
{


    private FirebaseListAdapter<ChatMessage> adapter;

    public ChatFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        FloatingActionButton fab =
                (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText input = (EditText) v.findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .setValue(new ChatMessage(input.getText().toString(),
//                                FirebaseAuth.getInstance()
//                                        .getCurrentUser()
//                                        .getDisplayName())
//                        );
                FirebaseReference ref = new FirebaseReference();
                ref.select("chat").setVal(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName()));

                // Clear the input
                input.setText("");
            }
        });
        displayChatMessages(v);

        return v;
    }

    private void displayChatMessages(View v)
    {
        ListView listOfMessages = (ListView) v.findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(), ChatMessage.class, R.layout.message, FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position)
            {
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
        };

        listOfMessages.setAdapter(adapter);

    }

}
