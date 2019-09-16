package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.Element;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.findmyhome.abodeonline.Information.HostelInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ElementProfileActivity extends AppCompatActivity {

    TextView ElementName,ElementCity,ElementAddress,ElementRent,ElementEmail,ElementPhone;
    ImageView ProfileImageView;
    FirebaseAuth firebaseAuth;
    Button ContactOwnerButton;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference(firebaseAuth.getCurrentUser().getEmail()).child("hostel");

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        animation.start();
        Intent PassedIntent = getIntent();
        HostelInfo hostelInfo = (HostelInfo) PassedIntent.getSerializableExtra("HOSTEL_OBJECT");
        //Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        ElementName = findViewById(R.id.passedelementname);
        ElementCity = findViewById(R.id.passedelementcity);
        ElementAddress = findViewById(R.id.passedelementlocation);
        ElementRent = findViewById(R.id.passedelementrent);
        ElementEmail = findViewById(R.id.passedelementowner);
        ElementPhone = findViewById(R.id.passedelementphone);
        ProfileImageView = findViewById(R.id.passedprofileimage);
        ContactOwnerButton = findViewById(R.id.passedprofilebutton);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(ProfileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ElementProfileActivity.this, "Failed uploading the image.", Toast.LENGTH_SHORT).show();
            }
        });


        ContactOwnerButton.setAnimation(animation);
        ProfileImageView.setAnimation(animation);
        ElementPhone.setAnimation(animation);
        ElementRent.setAnimation(animation);
        ElementName.setAnimation(animation);
        ElementCity.setAnimation(animation);
        ElementEmail.setAnimation(animation);
        ElementPhone.setAnimation(animation);
        ElementAddress.setAnimation(animation);


        ElementEmail.setText(hostelInfo.getOwneremail());
        ElementCity.setText(hostelInfo.getHostelcity().toUpperCase());
        ElementName.setText(hostelInfo.getHostelname());
        ElementAddress.setText(hostelInfo.getHosteladdress());
        ElementRent.setText(hostelInfo.getHostelrent());
        ElementPhone.setText(hostelInfo.getHostelcontact());

        overridePendingTransition(R.anim.no_animation,R.anim.slide_down);
    }
}
