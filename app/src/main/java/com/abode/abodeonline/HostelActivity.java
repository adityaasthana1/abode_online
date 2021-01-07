package com.abode.abodeonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HostelActivity extends AppCompatActivity implements LocationListener {
    EditText GetCity;
    Button GetCityButton;
    ListView HostelListView;
    TextView CurrentCity;
    FirebaseListAdapter<EntityAdapter> adapter;
    StorageReference storageReference;
    LocationManager locationManager;
    DatabaseReference userLocationReference;
    FirebaseAuth firebaseAuth;
    UserLocation currentLocation;
    String SearchCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);

        GetCity = findViewById(R.id.getcity);
        GetCityButton = findViewById(R.id.getcitybutton);
        HostelListView = findViewById(R.id.HostelListView);
        CurrentCity=findViewById(R.id.CurrentCityTextView);
        firebaseAuth = FirebaseAuth.getInstance();
        userLocationReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("location");
        grantPermission();
        isLocationEnabled();
        getLocation();
        try{
            userLocationReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentLocation = snapshot.getValue(UserLocation.class);
                    try{CurrentCity.setText(currentLocation.getUsercity().toUpperCase());}catch (Exception e){e.printStackTrace();}
                    //Toast.makeText(HostelActivity.this, currentLocation.getUsercity().toLowerCase(), Toast.LENGTH_SHORT).show();
                    Query query = FirebaseDatabase.getInstance().getReference("hostels").child("hostel-map").child(currentLocation.getUsercity().toLowerCase());

                    FirebaseListOptions<EntityAdapter> options =  new FirebaseListOptions.Builder<EntityAdapter>()
                            .setQuery(query,EntityAdapter.class)
                            .setLayout(R.layout.list_element_layout)
                            .build();
                    adapter = new FirebaseListAdapter<EntityAdapter>(options) {
                        @Override
                        protected void populateView(View v, EntityAdapter model, int position) {
                            storageReference = FirebaseStorage.getInstance().getReference("hostels").child(model.entityid);
                            TextView ListName = v.findViewById(R.id.list_element_name);
                            TextView ListAddress = v.findViewById(R.id.list_element_address);
                            TextView ListRent = v.findViewById(R.id.list_element_rent);
                            final ImageView profileImage = v.findViewById(R.id.list_image_element);
                            storageReference.child("profileimage").getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Glide.with(getApplicationContext())
                                                    .load(uri)
                                                    .into(profileImage);
                                        }
                                    });
                            ListName.setText(model.getEntityname());
                            ListAddress.setText(model.getEntityaddress());
                            ListRent.setText(model.getEntityrent());
                        }
                    };
                    HostelListView.setAdapter(adapter);
                    adapter.startListening();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }catch (Exception e){
            e.printStackTrace();
            Log.d("LOCATIONDATA_ERROR","ValueEventListener has failed");
        }



        GetCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchCity = GetCity.getText().toString().trim();
                Query query = FirebaseDatabase.getInstance().getReference("hostels").child("hostel-map").child(SearchCity.toLowerCase());
                CurrentCity.setText(SearchCity.toUpperCase());

                FirebaseListOptions<EntityAdapter> options =  new FirebaseListOptions.Builder<EntityAdapter>()
                        .setQuery(query,EntityAdapter.class)
                        .setLayout(R.layout.list_element_layout)
                        .build();
                adapter = new FirebaseListAdapter<EntityAdapter>(options) {
                    @Override
                    protected void populateView(View v, EntityAdapter model, int position) {
                        storageReference = FirebaseStorage.getInstance().getReference("hostels").child(model.entityid);
                        TextView ListName = v.findViewById(R.id.list_element_name);
                        TextView ListAddress = v.findViewById(R.id.list_element_address);
                        TextView ListRent = v.findViewById(R.id.list_element_rent);
                        final ImageView profileImage = v.findViewById(R.id.list_image_element);
                        storageReference.child("profileimage").getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getApplicationContext())
                                                .load(uri)
                                                .into(profileImage);
                                    }
                                });
                        ListName.setText(model.getEntityname());
                        ListAddress.setText(model.getEntityaddress());
                        ListRent.setText(model.getEntityrent());
                    }
                };
                HostelListView.setAdapter(adapter);
                adapter.startListening();
            }
        });



        HostelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HostelActivity.this,ElementProfile.class);
                EntityAdapter entityAdapter = (EntityAdapter)parent.getAdapter().getItem(position);
                intent.putExtra("ENTITY_OBJECT", entityAdapter);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String CountryGPS = addresses.get(0).getCountryName();
            String StateGPS = addresses.get(0).getAdminArea();
            String CityGPS = addresses.get(0).getLocality();
            String PostalCodeGPS = addresses.get(0).getPostalCode();
            String AddressLineGPS = addresses.get(0).getAddressLine(0);
            String LongitudeGPS = Double.toString(location.getLongitude());
            String LatitudeGPS = Double.toString(location.getLatitude());

            UserLocation userLocation = new UserLocation(CountryGPS, StateGPS, CityGPS, PostalCodeGPS, AddressLineGPS, LongitudeGPS, LatitudeGPS);
            userLocationReference.setValue(userLocation)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, "Disabled GPS.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void grantPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }

    private void isLocationEnabled() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try{
            gpsEnabled =lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!gpsEnabled && !networkEnabled){
            new AlertDialog.Builder(HostelActivity.this)
                    .setTitle("Enable GPS services please!")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel",null)
                    .show();
        }
    }

    private void getLocation() {
        try{
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5,(LocationListener) this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }



}