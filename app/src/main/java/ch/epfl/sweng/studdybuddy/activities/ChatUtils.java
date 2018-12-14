package ch.epfl.sweng.studdybuddy.activities;

import com.google.firebase.auth.FirebaseAuth;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class ChatUtils {
    protected static void pushToFirebase(FirebaseReference ref, String groupID,String input, String downloadUri)
    {
        ref.select(Messages.FirebaseNode.CHAT).select(groupID).push(new ChatMessage(input,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), downloadUri));
    }
}
