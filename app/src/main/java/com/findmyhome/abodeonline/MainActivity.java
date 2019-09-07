package com.findmyhome.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private Button signupbutton;
    private TextView tvsingin;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(MainActivity.this,LoginActivity.class));
            startHomeActivity();

        }




        email = findViewById(R.id.semail);
        password = findViewById(R.id.spassword);
        signupbutton = findViewById(R.id.signupbutton);
        tvsingin = findViewById(R.id.textsignin);
        //firebaseAuth = FirebaseAuth.getInstance();

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String pw = password.getText().toString().trim();
                progressDialog.setMessage("Wait while we sign you up!");

                if(em.isEmpty()||pw.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty fields",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(em,pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    SendVerificationEmail();
                                    startActivity(new Intent(MainActivity.this,GetDataActivity.class));
                                }
                            });
                }
            }
        });

        tvsingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });
    }

    void startLoginActivity(){
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);
    }
    void startHomeActivity(){
        Intent i = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(i);
    }
    void SendVerificationEmail(){
        FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
        if(mfirebaseUser!=null){
        mfirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Registered, verify the email!",Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

}
