package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeActivity extends AppCompatActivity {
    LinearLayout HostelLayout,RentalRoomLayout,AddResourceLayout,ContactUsLayout,MyProfileLayout;
    Button LogoutButton;
    UserInfoAdapter userInfoAdapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    TextView UserNameHome;
    ImageView UserProfileImage;
    Animation animation;
    //AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LogoutButton = findViewById(R.id.logoutbutton);
        HostelLayout = findViewById(R.id.hostelcard);
        RentalRoomLayout = findViewById(R.id.rentalrooms);
        AddResourceLayout = findViewById(R.id.addresources);
        ContactUsLayout = findViewById(R.id.contactus);
        UserNameHome = findViewById(R.id.userFirstName);
        UserProfileImage = findViewById(R.id.userImageHome);
        MyProfileLayout = findViewById(R.id.MyProfileLayout);

        //animationSet = new AnimationSet(true);


        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);



        firebaseAuth = FirebaseAuth.getInstance();

        try{
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid())
                    .child("details");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userInfoAdapter = snapshot.getValue(UserInfoAdapter.class);
                    UserNameHome.setText(userInfoAdapter.getUser_firstname());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){e.printStackTrace();}

        try{
            storageReference = FirebaseStorage.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("profileimage");
            if (storageReference==null){
                Log.d("STORAGE_REF_NULL","Storage Reference is null hence no image displated.");
            }else{
                storageReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getApplicationContext())
                                .load(uri)
                                .into(UserProfileImage);
                            }
                        });
            }
        }catch (Exception e){e.printStackTrace();}


        MyProfileLayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,UserProfileActivity.class)));
        AddResourceLayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,AddResourcesActivity.class)));
        HostelLayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,HostelActivity.class)));
        RentalRoomLayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,RentalRoomActivity.class)));

        LogoutButton.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            finish();
        });
    }
}