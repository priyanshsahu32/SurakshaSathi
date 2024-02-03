package com.pcsahu.surakshasathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class EmergencyLocation extends AppCompatActivity implements OnMapReadyCallback {
    AppCompatButton Receiver,send;

    ArrayList<String> numbers;

    SharedPreferenceClass sharedPreferenceClass;



    // Get the default SmsManager

    String number,msg;




    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    double latitude,longitude;
    private GoogleMap googlemap;

    private LatLng destinationLatLng;

    private Polyline polyline;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location );
        Receiver = findViewById( R.id.receiver);
        send = findViewById( R.id.send );


        sharedPreferenceClass = new SharedPreferenceClass( this );

        numbers = new ArrayList<>();
        numbers.clear();

        Receiver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(EmergencyLocation.this, MngRcvr.class) );
            }
        } );

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for SMS permission
                if (ContextCompat.checkSelfPermission(EmergencyLocation.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmergencyLocation.this,
                            new String[]{Manifest.permission.SEND_SMS}, 2);
                }

                String mapLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;
                msg = "Need your help currently this is my location: " + mapLink;
                numbers = sharedPreferenceClass.getArrayList(EmergencyLocation.this);

                for (int i = 0; i < numbers.size(); i++) {
                    number = "+91" + numbers.get(i);

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, msg, null, null);
                        Toast.makeText(EmergencyLocation.this, "SMS sent successfully to " + number, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(EmergencyLocation.this, "Failed to send SMS to " + number, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        fusedLocationClient = LocationServices.getFusedLocationProviderClient( this );

        // Check for location permission
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById( R.id.map );
            mapFragment.getMapAsync( this);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions( this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1 );
        }






    }



    private void requestLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        locationRequest.setInterval( 5000 ); // Update interval in milliseconds

        locationCallback = new LocationCallback() {
            @Override

            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        // Handle the obtained location
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        // Update marker position
                        LatLng currentLocation = new LatLng( latitude, longitude );
                        updateMapAndDistance( currentLocation );
                    }
                }
            }


        };


        if (ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.requestLocationUpdates( locationRequest, locationCallback, null );



    }


    private void updateMapAndDistance(LatLng currentLocation) {
        if (googlemap != null) {
            googlemap.clear(); // Clear previous markers
            googlemap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
            float zoomLevel = 17.0f; // You can adjust the zoom level as needed
            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                requestLocationUpdates();
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googlemap = googleMap;

        LatLng sydney = new LatLng( latitude,longitude );
        googlemap.addMarker( new MarkerOptions().position( sydney ).title( "Sydney" ) );
        googlemap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
    }







}
