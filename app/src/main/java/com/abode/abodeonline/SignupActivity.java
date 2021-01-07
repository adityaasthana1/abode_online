package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    EditText Email,Password,cPassword;
    Button SignupButton;
    FirebaseAuth firebaseAuth;
    //ProgressDialog signupDialog;
    boolean EmailSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Email = findViewById(R.id.emailsignup);
        Password = findViewById(R.id.passwordsignup);
        cPassword = findViewById(R.id.confirmpasswordsu);
        SignupButton = findViewById(R.id.signupbutton);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            Log.d("FIREBASEUSER_STATUS","status == null");
        }

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = Email.getText().toString().trim();
                String getPassword = Password.getText().toString().trim();
                String getcPassword = cPassword.getText().toString().trim();

                if(getPassword.isEmpty() || getcPassword.isEmpty() || getEmail.isEmpty()){
                    Toast.makeText(SignupActivity.this, "You can't leave fields empty", Toast.LENGTH_SHORT).show();
                }else if(!getPassword.equals(getcPassword)){
                    Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }else{
                    //signupDialog.setMessage("Please wait");
                    //signupDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword)
                            .addOnSuccessListener(authResult -> {
                                //signupDialog.dismiss();
                                SendVerificationEmail();
                                //Toast.makeText(SignupActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, GetDetailsActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                //signupDialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Please try again later!", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }

    void SendVerificationEmail(){
        FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
        if(mfirebaseUser!=null){
            mfirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Registered, verify the email!",Toast.LENGTH_SHORT).show();
                    EmailSent = true;
                }
            });
        }
    }

}