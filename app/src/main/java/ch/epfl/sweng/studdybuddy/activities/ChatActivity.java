package ch.epfl.sweng.studdybuddy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class ChatActivity extends AppCompatActivity{
    String groupID;
    public FirebaseReference ref;
    private StorageReference storageRef;
    private Button addImage, cameraButon;
    private File file;
    private Uri filePath,captureUri;
    private String downloadUri;
    private final int PICK_IMAGE_REQUEST = 71;
    private final int OPEN_CAMERA_REQUEST = 72;
    private FloatingActionButton fab;

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
        downloadUri="";
        displayChatMessages();
         fab = (FloatingActionButton)findViewById(R.id.fab);
        addImage=(Button)findViewById(R.id.gallery);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent   ,"SELECT IMAGE"),PICK_IMAGE_REQUEST);
            }
        });
        cameraButon=(Button)findViewById(R.id.camera);
        cameraButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //check if camera permission is granted and when it isn't ask for it
//                if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(ChatActivity.this,
//                            new String[]{Manifest.permission.CAMERA},
//                            0);
//                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent, OPEN_CAMERA_REQUEST);
                }
            }
        });
        this.ref = initRef();
        storageRef= FirebaseStorage.getInstance().getReference();
        fab.setOnClickListener(getFabListener());

    }

    @NonNull
    protected View.OnClickListener getFabListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText input = (EditText)findViewById(R.id.input);
                if(input.getText().toString().trim().length() > 0||!downloadUri.isEmpty()) {
                    pushToFirebase(input.getText().toString(),downloadUri);
                    input.setText("");
                    filePath=null;
                    downloadUri="";
                }
            }
        };
    }

    private void pushToFirebase(String input,String downloadUri) {
        ref.select(Messages.FirebaseNode.CHAT).select(groupID).push(new ChatMessage(input,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), downloadUri));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    //Get the filepath of the selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null )
        {
            if(requestCode == PICK_IMAGE_REQUEST && data.getData() != null){
                filePath = data.getData();
            }
            if(requestCode == OPEN_CAMERA_REQUEST) {
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");

                //Uri of camera image
               filePath = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);


            }

            uploadImage();

        }
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

                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                messageUser.setText(model.getMessageUser());
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));


              //  if (!model.getMessageText().isEmpty()) {
                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
                    messageText.setText(model.getMessageText());
                //}

                ImageView image = (ImageView) v.findViewById(R.id.imgViewGall);
                if (!model.getImageUri().isEmpty()) {


                        Picasso.get().load(model.getImageUri()).into(image);
                    Toast.makeText(ChatActivity.this, "Visible"+model.getImageUri(), Toast.LENGTH_SHORT).show();

                }else{
                    //image.setVisibility(View.INVISIBLE);
                    //image.setMaxHeight(1);
                    image.setImageResource(android.R.color.transparent);
                    Toast.makeText(ChatActivity.this, "INVisible"+model.getImageUri(), Toast.LENGTH_SHORT).show();

                }


                }

        };


        listOfMessages.setAdapter(adapter);
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageRef.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult().toString();
                        progressDialog.dismiss();
                        fab.performClick();
                           Toast.makeText(ChatActivity.this, downloadUri, Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                            Toast.makeText(ChatActivity.this, "Failed Uploading", Toast.LENGTH_SHORT).show();

                    }
                }
            });
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(ChatActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(ChatActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
        }
    }
}
