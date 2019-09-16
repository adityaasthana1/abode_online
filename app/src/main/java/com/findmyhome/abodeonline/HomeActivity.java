package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private Button logout,profilebutton,inboxbutton;
    private Button HostelSearchButton,PgSearchButton,RentalRoomSearchButton,UploadContentButton;
    DatabaseReference databaseReference;
    TextView usernamehome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        usernamehome = findViewById(R.id.usernamehome);
        inboxbutton = findViewById(R.id.inboxbutton);
        profilebutton = findViewById(R.id.profilebutton);
        HostelSearchButton = findViewById(R.id.hostelbutton);
        PgSearchButton = findViewById(R.id.pgbutton);
        RentalRoomSearchButton = findViewById(R.id.pgbutton);
        UploadContentButton = findViewById(R.id.uploadcontentbutton);


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        String uname = dataSnapshot.child("firstname").getValue().toString();
                        usernamehome.setText(uname);
                    }else{
                        startActivity(new Intent(HomeActivity.this,GetDataActivity.class));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"LOL",Toast.LENGTH_SHORT).show();
                }
            });


        logout = findViewById(R.id.logoutbutton);

        HostelSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(HomeActivity.this, "Activity Coming Soon", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,SearchHostelActivity.class));

            }
        });

        PgSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SearchPgActivity.class));
            }
        });







        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
                firebaseAuth.signOut();;
            }
        });

        UploadContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UploadContentActivity.class));
            }
        });

    }
}
