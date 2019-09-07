package com.findmyhome.abodeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetDataActivity extends AppCompatActivity {

    EditText firstname,lastname,phonenumber,living,workplace;
    Button GetUserDataButton;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phonenumber = findViewById(R.id.phone);
        living = findViewById(R.id.living);
        workplace = findViewById(R.id.workplace);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        GetUserDataButton = findViewById(R.id.getdatabutton);


        GetUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                String UserId = firebaseUser.getUid();
                String fname = firstname.getText().toString().trim();
                String lname = lastname.getText().toString().trim();
                String livinglocation = living.getText().toString().trim();
                String workps = workplace.getText().toString().trim();

                long phone = Long.parseLong(phonenumber.getText().toString().trim());

                UserInformation user = new UserInformation(fname,lname,phone,livinglocation,workps);

                if(fname.isEmpty()||lname.isEmpty()||phone==0 || livinglocation.isEmpty() || workps.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    databaseReference.child(UserId).setValue(user);
                    Toast.makeText(GetDataActivity.this, "Data Stored", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GetDataActivity.this,LoginActivity.class));
                    firebaseAuth.signOut();
                    finish();
                }
            }
        });


    }
}
