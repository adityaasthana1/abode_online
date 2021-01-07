package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button SigninButton,GoogleSigninButton;
    TextView SignupButton,ForgetPasswordButton,LogoMain;
    EditText Email,Password;
    LinearLayout ForgotPasswordLayout,SignUpTextLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Animation ZoomInanimation,FadeInAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SigninButton = findViewById(R.id.signinbutton);
        SignupButton = findViewById(R.id.signupbutton);
        ForgetPasswordButton = findViewById(R.id.forgotpassword);
        Email = findViewById(R.id.emailsignin);
        Password = findViewById(R.id.passwordsignin);
        LogoMain = findViewById(R.id.LogoMain);
        GoogleSigninButton = findViewById(R.id.googlesigninbutton);
        ForgotPasswordLayout = findViewById(R.id.forgotpasswordlayout);
        SignUpTextLayout = findViewById(R.id.signuptextlayout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ZoomInanimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        FadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        LogoMain.setAnimation(ZoomInanimation);
        GoogleSigninButton.setAnimation(FadeInAnimation);
        SignupButton.setAnimation(FadeInAnimation);
        Email.setAnimation(FadeInAnimation);
        Password.setAnimation(FadeInAnimation);
        ForgotPasswordLayout.setAnimation(FadeInAnimation);
        SignUpTextLayout.setAnimation(FadeInAnimation);

        if(firebaseUser!=null){
            if(firebaseUser.isEmailVerified()) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                Log.d("FIREBASEUSER_STATUS", "status != null");
                finish();
            }else{
                firebaseAuth.signOut();
            }
        }else{
            Log.d("FIREBASEUSER_STATUS","status == null");
        }


        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
            }
        });

        SigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailsignin = Email.getText().toString().trim();
                String passwordsignin = Password.getText().toString().trim();

                if(emailsignin.isEmpty() || passwordsignin.isEmpty()){
                    Toast.makeText(MainActivity.this, "empty fields", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(emailsignin,passwordsignin)
                            .addOnSuccessListener(authResult -> {
                                Log.d("POSTED",  "DATA POSTED SUCCESSFULLY");
                                try{
                                    if(!firebaseAuth.getCurrentUser().isEmailVerified()){
                                        //firebaseAuth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(MainActivity.this, firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(MainActivity.this, "Please Verify your Email!", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                        finish();
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}

                            })
                            .addOnFailureListener(e -> {
                                Log.d("ERROR POSTING",e.getMessage());
                                Toast.makeText(MainActivity.this, "DB error", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }
}