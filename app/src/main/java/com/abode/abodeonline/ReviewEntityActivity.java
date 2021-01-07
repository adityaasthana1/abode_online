package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ReviewEntityActivity extends AppCompatActivity {
    TextView CurrentReviewRating;
    RatingBar CurrentRatingBar,ReviewRatingBar;
    EditText ReviewDescription;
    Button SubmitReviewButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_entity);
        EntityAdapter entityAdapter = getIntent().getParcelableExtra("ENTITY_OBJECT");

        CurrentReviewRating = findViewById(R.id.CurrentReviewRating);
        CurrentRatingBar = findViewById(R.id.CurrentReviewRatingBar);
        ReviewRatingBar = findViewById(R.id.EntityRatingBar);
        ReviewDescription = findViewById(R.id.EntityReviewDescription);
        SubmitReviewButton = findViewById(R.id.SubmitReviewButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("hostels").child("hostel-list").child(entityAdapter.getEntityid());
        firebaseAuth = FirebaseAuth.getInstance();

        SubmitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                float givenRating =  ReviewRatingBar.getRating();
                String awardedRating = Float.toString(givenRating);
                String reviewDescription = ReviewDescription.getText().toString().trim();
                String timeStamp = currentTime.toString();
                String hashString = firebaseAuth.getCurrentUser().getUid().concat(timeStamp);
                String key = UUID.nameUUIDFromBytes(hashString.getBytes()).toString();
                ReviewAdapter reviewAdapter = new ReviewAdapter(firebaseAuth.getCurrentUser().getUid(),awardedRating,reviewDescription,timeStamp);
                databaseReference.child("reviews").child(key).setValue(reviewAdapter)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                                Toast.makeText(ReviewEntityActivity.this, "Review Submitted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ReviewEntityActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
}