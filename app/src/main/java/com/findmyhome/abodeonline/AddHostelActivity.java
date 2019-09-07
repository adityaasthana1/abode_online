package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findmyhome.abodeonline.Information.HostelInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHostelActivity extends AppCompatActivity {

    EditText HostelName,CityName,HostelAddress,HostelAverageRent,HostelContact;
    Button RegisterButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mdatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel);

        HostelName = findViewById(R.id.addhostelname);
        CityName = findViewById(R.id.addhostelcity);
        HostelAddress = findViewById(R.id.addhosteladdress);
        HostelAverageRent = findViewById(R.id.addhostelrent);
        HostelContact = findViewById(R.id.addhostelcontactnumber);
        RegisterButton = findViewById(R.id.hostelregisterbutton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdatabaseReference = FirebaseDatabase.getInstance().getReference("hostels");
                final String Hostelname = HostelName.getText().toString().trim().toLowerCase();
                final String  Cityname = CityName.getText().toString().trim().toLowerCase();
                String Hosteladdress = HostelAddress.getText().toString().trim().toLowerCase();
                String Hostelaveragerent = HostelAverageRent.getText().toString().trim().toLowerCase();
                String Hostelcontact = HostelContact.getText().toString().trim();
                final HostelInfo hostelInfo = new HostelInfo(Hostelname,Cityname,Hosteladdress,Hostelaveragerent,Hostelcontact,firebaseUser.getEmail());

                if(Hosteladdress.isEmpty() || Cityname.isEmpty() || Hostelaveragerent.isEmpty() || Hostelcontact.isEmpty() || Hostelname.isEmpty()){
                    Toast.makeText(AddHostelActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference check = mdatabaseReference.child(Cityname).child(Hostelname);
                    check.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(AddHostelActivity.this, "Hostel Already Exist!", Toast.LENGTH_SHORT).show();
                            }else{
                                mdatabaseReference.child(Cityname).child(Hostelname).setValue(hostelInfo);
                                Toast.makeText(getApplicationContext(),"Hostel Registered",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });



    }
}
