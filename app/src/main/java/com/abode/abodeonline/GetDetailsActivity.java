package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetDetailsActivity extends AppCompatActivity {
    EditText Fname,Lname,Phone,City,Address,PostalCode;
    Button SignupButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);
        Fname = findViewById(R.id.fname);
        Lname = findViewById(R.id.lname);
        Phone = findViewById(R.id.phonedetail);
        City = findViewById(R.id.citydetail);
        Address = findViewById(R.id.addressdetail);
        PostalCode = findViewById(R.id.postaldetail);
        SignupButton = findViewById(R.id.signupdetail);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname =  Fname.getText().toString().trim();
                String lastname = Lname.getText().toString().trim();
                String phonenumber = Phone.getText().toString().trim();
                String CityDetail = City.getText().toString().trim();
                String AddressDetail = Address.getText().toString().trim();
                String PostalDetail = PostalCode.getText().toString().trim();
                UserLocation userLocation = new UserLocation();

                if(firstname.isEmpty() || lastname.isEmpty() || phonenumber.isEmpty() || CityDetail.isEmpty() || AddressDetail.isEmpty() || PostalDetail.isEmpty()){
                    Toast.makeText(GetDetailsActivity.this, "empty fields", Toast.LENGTH_SHORT).show();
                }else{

                    UserInfoAdapter infoAdapter = new UserInfoAdapter(firstname,lastname,phonenumber,CityDetail,AddressDetail,PostalDetail);
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("details").setValue(infoAdapter)
                            .addOnFailureListener(e -> Toast.makeText(GetDetailsActivity.this, "Cannot Store data. Try again later!", Toast.LENGTH_SHORT).show())
                            .addOnSuccessListener(aVoid -> {
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("location").setValue(userLocation);
                                Toast.makeText(GetDetailsActivity.this, "Created!", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnCanceledListener(() -> Toast.makeText(GetDetailsActivity.this, "Cancelled", Toast.LENGTH_SHORT).show());

                }
            }
        });
    }


}