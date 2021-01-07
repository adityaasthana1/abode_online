package com.abode.abodeonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddResourcesActivity extends AppCompatActivity {
    EditText Name,Phone,Email,State,City,Address,Rent,Description;
    CheckBox TermsCondition,HostelCheckBox,RentalRoomCheckBox;
    Uri ImageURI;
    ImageView ToBeUploadImage;
    Button NextButton,SelectProfileImageButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,userReference, EntityListRef, EntityMapRef;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resources);
        //RegisterType = findViewById(R.id.RadioGroup);
        //HostelRadio = findViewById(R.id.HostelRadioButton);
        //RentalRoomRadio = findViewById(R.id.RentalRoomRadioButton);
        //TiffinCenterRadio = findViewById(R.id.TiffinCenterRadioButton);
        Name = findViewById(R.id.EntityName);
        Phone = findViewById(R.id.EntityPhone);
        Email = findViewById(R.id.EntityEmail);
        State = findViewById(R.id.EntityState);
        City = findViewById(R.id.EntityCity);
        Address = findViewById(R.id.EntityAddress);
        Rent = findViewById(R.id.EntityRent);
        Description = findViewById(R.id.EntityDescription);
        TermsCondition = findViewById(R.id.tncresourcescheckbox);
        NextButton = findViewById(R.id.nextbutton);
        SelectProfileImageButton = findViewById(R.id.selectimagebutton);
        ToBeUploadImage = findViewById(R.id.selectedprofileimage);
        HostelCheckBox = findViewById(R.id.checkboxhostel);
        RentalRoomCheckBox = findViewById(R.id.checkboxrentalroom);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("rentalrooms");
        databaseReference = firebaseDatabase.getReference("rentalrooms");
        EntityListRef = databaseReference.child("rentalroom-list");
        userReference =  firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
        EntityMapRef = databaseReference.child("rentalroom-map");

        HostelCheckBox.setOnClickListener(v ->{
            RentalRoomCheckBox.setChecked(false);
            Log.d("HosteStatus","Hostel checkbox is checked");
        });
        RentalRoomCheckBox.setOnClickListener(v -> {
            HostelCheckBox.setChecked(false);
            Log.d("RentalRoomStatus","Rental Room checkbox is checked");
        });



        SelectProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFile();
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String EntityName = Name.getText().toString().trim();
                String EntityPhone = Phone.getText().toString().trim();
                String HashString = EntityName.concat(EntityPhone);
                String EntityID = UUID.nameUUIDFromBytes(HashString.getBytes()).toString();
                String EntityDescription = Description.getText().toString().trim();
                String EntityEmail = Email.getText().toString().trim();
                String EntityState = State.getText().toString().trim();
                String EntityCity = City.getText().toString().trim().toLowerCase();
                String EntityAddress = Address.getText().toString().trim();
                String EntityRent = Rent.getText().toString().trim();

                if(HostelCheckBox.isChecked()){
                    RentalRoomCheckBox.setChecked(false);
                    storageReference = FirebaseStorage.getInstance().getReference("hostels");
                    databaseReference = firebaseDatabase.getReference("hostels");
                    EntityListRef = databaseReference.child("hostel-list");
                    userReference =  firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
                    EntityMapRef = databaseReference.child("hostel-map");
                }
                if(RentalRoomCheckBox.isChecked()){
                    HostelCheckBox.setChecked(false);
                    storageReference = FirebaseStorage.getInstance().getReference("rentalrooms");
                    databaseReference = firebaseDatabase.getReference("rentalrooms");
                    EntityListRef = databaseReference.child("rentalroom-list");
                    userReference =  firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
                    EntityMapRef = databaseReference.child("rentalroom-map");
                }

                if(EntityAddress.isEmpty()||EntityCity.isEmpty()||EntityName.isEmpty()
                || EntityEmail.isEmpty()||EntityRent.isEmpty()||EntityState.isEmpty()||EntityPhone.isEmpty()){
                    Toast.makeText(AddResourcesActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }else if(!TermsCondition.isChecked()){
                    Toast.makeText(AddResourcesActivity.this, "You need to agree to TnC", Toast.LENGTH_SHORT).show();
                }else{
                    EntityAdapter entityAdapter = new EntityAdapter(EntityID,HashString,EntityName,EntityDescription,EntityPhone,EntityEmail,EntityState,EntityCity,EntityAddress,EntityRent);
                    EntityListRef.child(EntityID).child("details").setValue(entityAdapter)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("POSTED"," Added Successfully");
                                    userReference.child("hostels-added").child(EntityID).setValue(entityAdapter)
                                            .addOnFailureListener(e -> Log.d("USER_HOSTEL_FAILED","Hostel Failed to add in User Data"))
                                            .addOnSuccessListener(aVoid1 -> {
                                                EntityMapRef.child(EntityCity).child(EntityID).setValue(entityAdapter);
                                                String Zero = "0";
                                                EntityListRef.child(entityAdapter.getEntityid()).child("ratings").child("numbers").setValue(Zero);
                                                EntityListRef.child(entityAdapter.getEntityid()).child("ratings").child("total_rating").setValue(Zero);

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageURI);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = storageReference.child(EntityID).child("profileimage").putBytes(data);
                                                uploadTask.addOnFailureListener(e -> Toast.makeText(AddResourcesActivity.this, "Failed to Upload Image", Toast.LENGTH_SHORT).show())
                                                        .addOnCompleteListener(task -> Log.d("IMAGE_UPLOAD","Image uploaded to database successfully"));
                                                //Intent intent = new Intent(AddResourcesActivity.this,AddImageActivity.class);
                                                //intent.putExtra("Hostel_Object",EntityID);
                                                //startActivity(intent);
                                                finish();
                                                Log.d("USER_HOSTEL","Hostel Added in User Data");
                                            });

                                }
                            })
                            .addOnFailureListener(e -> {
                                Log.d("FAILURE","Couldn't Create Data");
                                Toast.makeText(AddResourcesActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }

    void SelectFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && data.getData()!=null){
            ImageURI = data.getData();
            ToBeUploadImage.setImageURI(ImageURI);
        }
    }
}