package ch.epfl.sweng.studdybuddy.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class ChatUtils {
    protected static void pushToFirebase(FirebaseReference ref, String groupID,String input, String downloadUri)
    {
        ref.select(Messages.FirebaseNode.CHAT).select(groupID).push(new ChatMessage(input,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), downloadUri));
    }
    protected static Task<Uri> uploadImage(Uri filePath,ProgressDialog mProgress,StorageReference storageRef)
    {
        Task result=null;
        if(filePath != null)
        {
            mProgress.setTitle("Uploading...");
            mProgress.show();
            StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
            result= ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            });
        }
        return result;
    }

}
