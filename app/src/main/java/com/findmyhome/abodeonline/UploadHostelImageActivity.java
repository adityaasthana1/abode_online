package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.findmyhome.abodeonline.Information.HostelInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadHostelImageActivity extends AppCompatActivity {

    Button SelectHostelImagesButton, UploadSelectImagesButton;
    ImageView UploadedImages;
    Uri ImageUri;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    Intent intent = getIntent();
    //HostelInfo hostelObject = (HostelInfo) intent.getSerializableExtra("HOSTEL_OBJECT");


    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hostel_image);


        firebaseAuth = FirebaseAuth.getInstance();
        SelectHostelImagesButton = findViewById(R.id.selecthostelimages);
        UploadSelectImagesButton = findViewById(R.id.hostelselectimageuploader);
        UploadedImages = findViewById(R.id.Uploadedhostelimage);

        SelectHostelImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        UploadSelectImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFile();
            }
        });

    }

    void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && data.getData()!=null){
            ImageUri = data.getData();
            UploadedImages.setImageURI(ImageUri);
        }
    }

    void UploadFile(){
        storageReference = FirebaseStorage.getInstance().getReference("UserUploads").child(firebaseAuth.getCurrentUser().getEmail());
        UploadTask uploadTask = storageReference.child("hostel").putFile(ImageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UploadHostelImageActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                Long progress = (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount())*100;
                //progressBar.setBackgroundColor(Color.parseColor("#9b59b6"));
                //progressBar.setProgress(progress.intValue());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadHostelImageActivity.this, "Task failed successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
