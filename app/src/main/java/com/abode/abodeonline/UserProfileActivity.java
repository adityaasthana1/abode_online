package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

public class UserProfileActivity extends AppCompatActivity {
    ImageView UserImage;
    TextView UserName,UserCityState, UserAddress, UserPostalCode;
    Button AddImageButton;
    ListView UserResourceList;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageReference ElementImageReference;
    UserInfoAdapter userInfoAdapter;
    FirebaseListAdapter<EntityAdapter> adapter;
    Uri ImageURI;
    ImageView ToBeUploadImage;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        UserImage = findViewById(R.id.userImage);
        UserName = findViewById(R.id.userName);
        UserCityState = findViewById(R.id.userCityState);
        UserAddress = findViewById(R.id.userAddress);
        UserPostalCode = findViewById(R.id.userPostalCode);
        AddImageButton = findViewById(R.id.changeImageButton);
        UserResourceList = findViewById(R.id.userResource);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("details");
        try{
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userInfoAdapter = snapshot.getValue(UserInfoAdapter.class);
                    UserName.setText(userInfoAdapter.getUser_firstname().concat(" ").concat(userInfoAdapter.getUser_lastname()));
                    UserCityState.setText(userInfoAdapter.getUser_city());
                    UserAddress.setText(userInfoAdapter.getUser_Address());
                    UserPostalCode.setText(userInfoAdapter.getUser_postalcode());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show();
        }

        try{
            storageReference = FirebaseStorage.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());
            if(storageReference==null){
                Log.d("NULL_STORAGE_REF","Storage Reference Instance is NULL");
            }else{
                storageReference.child("profileimage").getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(getApplicationContext())
                        .load(uri)
                        .into(UserImage));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Query query = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid())
                .child("hostels-added");
        FirebaseListOptions<EntityAdapter> options = new FirebaseListOptions.Builder<EntityAdapter>()
                .setQuery(query,EntityAdapter.class)
                .setLayout(R.layout.list_element_layout)
                .build();
        adapter = new FirebaseListAdapter<EntityAdapter>(options) {
            @Override
            protected void populateView(View v, EntityAdapter model, int position) {
                TextView ElementName = v.findViewById(R.id.list_element_name);
                TextView ElementCity = v.findViewById(R.id.list_element_address);
                TextView ElementPrice = v.findViewById(R.id.list_element_rent);
                ImageView ElementImage = v.findViewById(R.id.list_image_element);

                ElementName.setText(model.getEntityname());
                ElementCity.setText(model.getEntitycity());
                ElementPrice.setText(model.getEntityrent());

                ElementImageReference = FirebaseStorage.getInstance().getReference("hostels").child(model.getEntityid());
                if(ElementImageReference==null){
                    ElementImageReference = FirebaseStorage.getInstance().getReference("rentalrooms").child(model.getEntityid());
                }
                ElementImageReference.child("profileimage").getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getApplicationContext())
                                        .load(uri)
                                        .into(ElementImage);
                            }
                        });

            }
        };
        UserResourceList.setAdapter(adapter);
        adapter.startListening();

        AddImageButton.setOnClickListener(v -> startActivity(new Intent(UserProfileActivity.this,AddImageActivity.class)));

    }
}