package ch.epfl.sweng.studdybuddy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.services.notifications.MyFirebaseMessaging;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * An activity showing text messages and photos sent between the members of a group
 */
public class ChatActivity extends AppCompatActivity{
    String groupID;
    public FirebaseReference ref;
    private StorageReference storageRef;
    private Button addImage, cameraButon;
    private Uri filePath;
    private String downloadUri;
    protected static final int PICK_IMAGE_REQUEST = 1;
    protected static final int OPEN_CAMERA_REQUEST = 42;
    private FloatingActionButton fab;

    /**
     * OnCreate method setting up all the graphical components
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.ref = initRef();
        storageRef = FirebaseStorage.getInstance().getReference();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            groupID = extras.getString(Messages.groupID);
        } else {
            Toast.makeText(this,
                    "Group not found in database",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        initializations();
    }
    /**
     * A helper function that sets up part of the graphical components
     */    private void initializations() {
        downloadUri = "";
        displayChatMessages();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        addImage = (Button) findViewById(R.id.gallery);
        addImage.setOnClickListener(getGalleryImage());
        cameraButon = (Button) findViewById(R.id.camera);
        cameraButon.setOnClickListener(getCameraListener());
        //check if camera permission is granted and when it isn't ask for it]
        askForCameraPermission();
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


    /**
     * Check if permissions for using the camera are granted and if not prompt a message to ask the user
     */
    private void askForCameraPermission() {
        if (ContextCompat.checkSelfPermission(ChatActivity.this.getApplicationContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChatActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
    }
    /**
     * Start an intent demanding for the system gallery- style photo picker to be started
     */
    @NonNull
    protected View.OnClickListener getGalleryImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), PICK_IMAGE_REQUEST);
            }
        };
    }
    /**
     * Start an intent demanding for the system camera capture to be started
     */
    @NonNull
    protected View.OnClickListener getCameraListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, OPEN_CAMERA_REQUEST);
            }
        };
    }

    /**
     * Initiate the retrieval of filepath of the selected image
     * @param requestCode The code to be transmitted through an intent
     * @param resultCode resulting code of the operation
     * @param data intent to be started
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            initiatePhotoRequest(requestCode, data);
        }
    }
    /**
     * Decide wether the user asked to pick an existing photo or capture a new one
     * @param requestCode the code contained within the received intent
     * @param data the intent captured
     */
    private void initiatePhotoRequest(int requestCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            ChatUtils.uploadImageFromGallery(getFilePath(data), storageRef).addOnCompleteListener(getOnCompleteListener());
        }
        if (requestCode == OPEN_CAMERA_REQUEST) {
            ChatUtils.openCamera(data, getApplicationContext()).addOnSuccessListener(getOnSuccessListener());
        }
    }

    /**
     * Method to be mocked at DummyChatChatActivity.
     *
     * @param data Intent containing the data
     * @return Returns the Filepath of a Uri contained within an intent
     */

    protected Uri getFilePath(Intent data) {
        return data.getData();
    }

    /**
     *
     * @return Upload the image once the selected or captured photo has been loaded
     */
    @NonNull
    private OnSuccessListener<UploadTask.TaskSnapshot> getOnSuccessListener() {
        return new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                setDownloadUri(urlTask.getResult().toString());
                //mProgress.dismiss();

                fab.performClick();
            }
        };
    }
    /**
     * Method to be mocked at DummyChatChatActivity.
     * @return the firebaseReference
     */
    public FirebaseReference initRef() {
        return new FirebaseReference();
    }

    /**
     * Notify user if the uploading process failed or succeeded
     * @return returns a OnCompleteListener with the specified feature
     */
    @NonNull
    private OnCompleteListener<Uri> getOnCompleteListener() {
        return new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    setDownloadUri(task.getResult().toString());
                    fab.performClick();
                    Toast.makeText(ChatActivity.this, "Uploaded ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ChatActivity.this, "Failed Uploading", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    /**
     * The listener assigned to the FAB that sends the message
     * @return The aforementioned listener
     */
    @NonNull
    protected View.OnClickListener getFabListener() {
        EditText input = (EditText) findViewById(R.id.input);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabListener(input, ref, groupID, downloadUri);
            }
        };
    }

    /**
     * Setter for the downloadUri
     * @param downloadUri the new downloadUri to be set
     */
    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    /**
     * Initializes the pushing to firebase procedure and clears the used fields
     * @param inputText InputText containing the message to be sent
     * @param reference the FirebaseReference
     * @param groupsID the id of the current group related to this chat
     * @param downloadURI the uri of the uploaded image
     * @return //TODO
     */
    public int fabListener(EditText inputText, FirebaseReference reference, String groupsID, String downloadURI) {
        if (inputText.getText().toString().trim().length() > 0 || !downloadURI.isEmpty()) {
            pushMessage(inputText, reference, groupsID, downloadURI);
            inputText.setText("");
            setDownloadUri("");
            displayChatMessages();
        }
        return 0;//TODO also change the return comment
    }

    protected void pushMessage(EditText inputText, FirebaseReference reference, String groupsID, String downloadURI) {
        ChatUtils.pushToFirebase(reference, groupsID, inputText.getText().toString(), downloadURI);
    }

    /**
     * Updates the list of messages visible on screen with the information retrieved from Firebase
     */
    public void displayChatMessages() {
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        FirebaseListAdapter<ChatMessage> adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message,
                FirebaseDatabase.getInstance().getReference().child(Messages.FirebaseNode.CHAT).child(groupID)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                popViewBody(v, model);
            }

        };
        listOfMessages.setAdapter(adapter);
    }

    public int popViewBody(View v, ChatMessage model) {
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        messageUser.setText(model.getMessageUser());
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);
        messageTime.setText(getDate(model));
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        messageText.setText(model.getMessageText());
        ImageView image = (ImageView) v.findViewById(R.id.imgViewGall);
        String modelUri = model.getImageUri();
        if (modelUri != null && !modelUri.isEmpty()) {
            //Put the image in the chat
            Glide.with(ChatActivity.this).
                    load(modelUri)
                    .apply(new RequestOptions().override(500, 700)).
                    into(image);
        } else {
            image.setImageResource(android.R.color.transparent);
        }
        return 0;
    }

    protected CharSequence getDate(ChatMessage model) {
        return DateFormat.format("dd-MM (HH:mm)", model.getMessageTime());
    }
}

