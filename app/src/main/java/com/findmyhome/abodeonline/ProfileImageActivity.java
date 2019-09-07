package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ProfileImageActivity extends AppCompatActivity {

    private Button SelectGalleryButton, TakePictureButton,ImageUploadButton;
    private ImageView UploadedImage;
    private static final int PICK_IMAGE_REQUEST=1;
    public Uri ImageUri;
    ProgressBar progressBar;
    public StorageReference storageReference;
    public DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);


        progressBar = findViewById(R.id.progressbar);
        SelectGalleryButton = findViewById(R.id.choosefromgallerybutton);
        TakePictureButton = findViewById(R.id.takepicturebutton);
        ImageUploadButton = findViewById(R.id.imageuploadbutton);
        UploadedImage = findViewById(R.id.uploadedimage);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference().child("UserUploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());


        SelectGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        ImageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UplodeFile();
            }
        });


    }

    private void OpenFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && data.getData()!=null){
            ImageUri = data.getData();
            UploadedImage.setImageURI(ImageUri);
            Log.d("SuccessTag","ImageUriIsStored");
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR =getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void UplodeFile(){

        UploadTask uploadTask = storageReference.child(firebaseUser.getEmail()).child(firebaseUser.getUid()).putFile(ImageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProfileImageActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }





}
