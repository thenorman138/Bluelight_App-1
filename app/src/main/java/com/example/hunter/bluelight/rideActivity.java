package com.example.hunter.bluelight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class rideActivity extends AppCompatActivity {

    //Required GPS variables
    public LocationManager locationManager;
    public double longitude;
    public double latitude;
    public TextView latitudeValueGPS;
    public TextView longitudeValueGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        //Setting GPS value TextViews
        latitudeValueGPS = (TextView) findViewById(R.id.latitudeValueGPS);
        longitudeValueGPS = (TextView) findViewById(R.id.longitudeValueGPS);

        //Initializing LocationManager and starting to listen for coordinates
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2 *  60 * 1000, 10, locationListener);

        Button advanceToConfirm = (Button) findViewById(R.id.rideSubmitBtn);
        advanceToConfirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Added if statement for GPS enabled status
                if(isLocationEnabled()){
                    Intent intent = new Intent(rideActivity.this , rideConfirm.class);
                    startActivity(intent);
                } else {
                    showAlert();
                }
            }

        });

        Button CancelConfirm = (Button) findViewById(R.id.rideCancelBtn);
        CancelConfirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(rideActivity.this , choiceActivity.class);
                startActivity(intent);
            }

        });

    }

    //Test for GPS enable
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "request a ride")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    //GPS Listener class
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueGPS.setText(longitude + "");
                    latitudeValueGPS.setText(latitude + "");
                    Toast.makeText(rideActivity.this, "GPS provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(rideActivity.this, "GPS Enabled.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(rideActivity.this, "GPS Disabled. GPS must be enabled to request ride.", Toast.LENGTH_LONG).show();
        }
    };

}
