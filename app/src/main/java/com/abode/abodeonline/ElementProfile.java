package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ElementProfile extends AppCompatActivity {
    TextView ElementName,ElementCity,ElementRent,ElementAddress,ElementDescription,ElementPhone;
    Button ContactOwnerButton,WriteReviewButton;
    ListView ReviewListView;
    ImageView ProfileImage;
    StorageReference storageReference;
    DatabaseReference userDatabaseReference;
    FirebaseListAdapter<ReviewAdapter> adapter;
    UserInfoAdapter userInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_profile);
        EntityAdapter entityAdapter = getIntent().getParcelableExtra("ENTITY_OBJECT");
        ElementName = findViewById(R.id.ElementName);
        ElementCity = findViewById(R.id.ElementCity);
        ElementRent = findViewById(R.id.ElementRent);
        ElementAddress = findViewById(R.id.ElementAddress);
        ElementPhone = findViewById(R.id.ElementPhone);
        ElementDescription = findViewById(R.id.ElementDescription);
        ContactOwnerButton = findViewById(R.id.ContactOwnerButton);
        WriteReviewButton = findViewById(R.id.WriteReviewButton);
        ReviewListView = findViewById(R.id.ReviewListView);
        ProfileImage = findViewById(R.id.hostelimageload);

        storageReference = FirebaseStorage.getInstance().getReference("hostels").child(entityAdapter.getEntityid()).child("profileimage");
        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(ProfileImage);
                    }
                });

        ElementName.setText(entityAdapter.getEntityname());
        ElementRent.setText(entityAdapter.getEntityrent());
        ElementCity.setText(entityAdapter.getEntitycity());
        ElementAddress.setText(entityAdapter.getEntityaddress());
        ElementPhone.setText(entityAdapter.getEntityphone());
        ElementDescription.setText(entityAdapter.getEntitydescription());

        WriteReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElementProfile.this,ReviewEntityActivity.class);
                intent.putExtra("ENTITY_OBJECT",entityAdapter);
                startActivity(intent);
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference("hostels").child("hostel-list")
                .child(entityAdapter.getEntityid()).child("reviews");
        Log.d("UID",entityAdapter.getEntityid());

        FirebaseListOptions<ReviewAdapter> options = new FirebaseListOptions.Builder<ReviewAdapter>()
                .setQuery(query,ReviewAdapter.class)
                .setLayout(R.layout.review_list_element)
                .build();

        adapter = new FirebaseListAdapter<ReviewAdapter>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void populateView(View v, ReviewAdapter model, int position) {
                TextView ReviewerName = v.findViewById(R.id.list_element_reviewername);
                TextView ReviewDescription = v.findViewById(R.id.list_element_reviewdesc);
                TextView TimeStamp = v.findViewById(R.id.list_element_reviewtimestamp);
                RatingBar ratingBar = v.findViewById(R.id.list_element_ratingbar);
                ImageView ReviewerImage = v.findViewById(R.id.list_element_reviewerimage);

                userDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(model.getUserid()).child("details");
                userDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userInfoAdapter = snapshot.getValue(UserInfoAdapter.class);
                        ReviewerName.setText(userInfoAdapter.getUser_firstname().concat(" ").concat(userInfoAdapter.getUser_lastname()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ratingBar.setRating(Float.parseFloat(model.getRatingawarded()));
                ReviewDescription.setText(model.getRatingdescription());
                TimeStamp.setText(model.getTimestamp());
            }
        };

        ReviewListView.setAdapter(adapter);
        adapter.startListening();




    }
}