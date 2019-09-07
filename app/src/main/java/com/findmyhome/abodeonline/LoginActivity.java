package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button signinbutton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.lemail);
        password = findViewById(R.id.lpassword);
        signinbutton = findViewById(R.id.signinbutton);

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String pw = password.getText().toString().trim();
                if(em.isEmpty()||pw.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Fields",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(em,pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if(checkVerificationStatus()) {
                                            Toast.makeText(getApplicationContext(),"Logged in",Toast.LENGTH_SHORT).show();
                                            startHomeActivity();
                                            finish();

                                        }else{
                                            Toast.makeText(getApplicationContext(),"Verify Email",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });




    }
    void startHomeActivity(){
        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(i);
    }
    boolean checkVerificationStatus(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            return true;
        }else{
            firebaseAuth.signOut();
            return false;
        }
    }
}
