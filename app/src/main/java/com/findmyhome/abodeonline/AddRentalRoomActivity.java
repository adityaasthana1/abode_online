package com.findmyhome.abodeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.findmyhome.abodeonline.Information.InformationRentalRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRentalRoomActivity extends AppCompatActivity {

    EditText RoomName,RoomCity,RoomAddress,RoomRent,RoomContact;
    Button RoomRegisterButton;

    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rental_room);
        RoomName = findViewById(R.id.addrentalname);
        RoomCity = findViewById(R.id.addroomcity);
        RoomAddress = findViewById(R.id.addroomaddress);
        RoomRent = findViewById(R.id.addroomrent);
        RoomContact = findViewById(R.id.addroomcontactnumber);
        RoomRegisterButton = findViewById(R.id.roomregisterbutton);

        mFirebaseAuth = FirebaseAuth.getInstance();

        RoomRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Roomname = RoomName.getText().toString().trim();
                String Roomcity = RoomCity.getText().toString().trim().toLowerCase();
                String Roomadress = RoomAddress.getText().toString();
                String Roomrent = RoomRent.getText().toString().trim();
                String Roomcontact = RoomContact.getText().toString().trim();
                InformationRentalRoom informationRentalRoom = new InformationRentalRoom(Roomname,Roomcity,Roomadress,Roomrent,Roomcontact);

                if(Roomadress.isEmpty()||Roomcity.isEmpty()||Roomcontact.isEmpty()||Roomname.isEmpty()||Roomcontact.isEmpty()){
                    Toast.makeText(AddRentalRoomActivity.this, "Data storage failed!", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference("RentalRooms");
                    mDatabaseReference.child(Roomcity).child(Roomname).setValue(informationRentalRoom);
                    Toast.makeText(AddRentalRoomActivity.this, "Rental Room added successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });



    }
}
