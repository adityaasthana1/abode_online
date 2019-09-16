package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private Button MessageButton;
    private TextView UserFullName;
    private TextView WorkField,Location;
    private TextView UploadImage;
    private ImageView ProfileImage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference,mDatabaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileImage = findViewById(R.id.profileimage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        storageReference = FirebaseStorage.getInstance().getReference("UserUploads").child(firebaseUser.getEmail());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("profileimages");

        WorkField = findViewById(R.id.workplace);
        Location = findViewById(R.id.locationid);
        MessageButton = findViewById(R.id.messagebutton);
        UserFullName = findViewById(R.id.flname);
        WorkField = findViewById(R.id.work);
        UploadImage = findViewById(R.id.uploadimagebutton);
        ProfileImage = findViewById(R.id.profileimage);
        String UserIdwithExtension = firebaseUser.getUid();

        storageReference.child(UserIdwithExtension).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(ProfileImage);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Picasso.with(getApplicationContext()).load(storageReference.child(firebaseUser.getUid()).getDownloadUrl().toString()).into(ProfileImage);
                String fname = dataSnapshot.child("firstname").getValue().toString().trim();
                String work = dataSnapshot.child("workplace").getValue().toString().trim();
                String living = dataSnapshot.child("living").getValue().toString();
                String lname = dataSnapshot.child("lastname").getValue().toString().trim();
                String flname = fname +" "+lname;
                String finalwork = "Works at " + work;
                String finallocation = "Lives in " + living;
                UserFullName.setText(flname);
                WorkField.setText(finalwork);
                Location.setText(finallocation);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,MessageActivity.class));

            }
        });


        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,ProfileImageActivity.class));

            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}

