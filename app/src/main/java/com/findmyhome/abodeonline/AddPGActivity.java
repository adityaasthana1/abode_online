package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findmyhome.abodeonline.Information.InformationPg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPGActivity extends AppCompatActivity {

    Button RegisterPgButton;
    EditText PgName,PgCity,PgAddress,PgRent,PgContact;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pg);

        mFirebaseAuth = FirebaseAuth.getInstance();
        RegisterPgButton = findViewById(R.id.pgregisterbutton);
        PgName = findViewById(R.id.addpgname);
        PgCity = findViewById(R.id.addpgcity);
        PgAddress = findViewById(R.id.addpgaddress);
        PgRent = findViewById(R.id.addpgrent);
        PgContact = findViewById(R.id.addpgcontactnumber);

        RegisterPgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pgname = PgName.getText().toString().trim();
                String Pgcity = PgCity.getText().toString().trim().toLowerCase();
                String Pgaddress = PgAddress.getText().toString().trim();
                String Pgrent = PgRent.getText().toString().trim();
                String Pgcontact = PgContact.getText().toString().trim();

                InformationPg informationPg = new InformationPg(Pgname,Pgcity,Pgaddress,Pgrent,Pgcontact,mFirebaseAuth.getCurrentUser().getEmail());

                if(Pgname.isEmpty()||Pgaddress.isEmpty()||Pgaddress.isEmpty()||Pgcity.isEmpty()||Pgrent.isEmpty()||Pgcontact.isEmpty()){
                    Toast.makeText(AddPGActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference("PG");
                    mDatabaseReference.child(Pgcity).child(Pgname).setValue(informationPg);
                    Toast.makeText(AddPGActivity.this, "PG Registered!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }
}
