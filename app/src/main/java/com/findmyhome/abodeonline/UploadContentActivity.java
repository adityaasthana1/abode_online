package com.findmyhome.abodeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UploadContentActivity extends AppCompatActivity {

    private Button HostelButton,PGButton,RentalRoomButton,TiffinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_content);

        HostelButton = findViewById(R.id.addhostelbutton);
        PGButton = findViewById(R.id.addpgbutton);
        RentalRoomButton = findViewById(R.id.addrentaltoombutton);
        TiffinButton = findViewById(R.id.addtiffincenterbutton);

        HostelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadContentActivity.this,AddHostelActivity.class));
            }
        });

        PGButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadContentActivity.this,AddPGActivity.class));
            }
        });



    }
}
