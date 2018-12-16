package ch.epfl.sweng.studdybuddy.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * A class containing a selection of auxiliary methods used in ChatActivity
 */
public class ChatUtils {
    protected static void pushToFirebase(FirebaseReference ref, String groupID, String input, String downloadUri) {
        ref.select(Messages.FirebaseNode.CHAT).select(groupID).push(new ChatMessage(input,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), downloadUri));
    }

    /**
     *Uploads the provided image selected from the system "gallery picker" to the Firebase Storage
     * @param filePath the path of the selected image
     * @param mProgress progress bar showing uploading progress
     * @param storageRef Firebase Storage reference
     * @return A task containing the URI of the uploaded image
     */
    protected static Task<Uri> uploadImageFromGallery(Uri filePath, ProgressDialog mProgress, StorageReference storageRef) {
        Task result = null;
        if (filePath != null) {
            mProgress.setTitle("Uploading...");
            mProgress.show();
            StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
            result = ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            });
        }
        return result;
    }

    protected static UploadTask uploadImageFromCamera(byte[] dataBAOS, Context applicationContext, ProgressDialog mProgress) {


        //Firebase storage folder where you want to put the images
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://studybuddy-a5bfe.appspot.com/images");

        //name of the image file (add time to have different files to avoid rewrite on the same file)
        StorageReference imagesRef = storageRef.child("image" + String.valueOf(System.currentTimeMillis()) + ".jpg");

        //upload image to firebase
        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(applicationContext, "Sending failed", Toast.LENGTH_SHORT).show();
                mProgress.dismiss();
            }
        });
        return uploadTask;
    }

    protected static UploadTask openCamera(Intent data, ProgressDialog mProgress, Context applicationContext) {
        mProgress.setMessage("Uploading");
        mProgress.show();

        //get the camera image
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataBAOS = baos.toByteArray();
        return ChatUtils.uploadImageFromCamera(dataBAOS, applicationContext, mProgress);
    }

}
