package com.neuron.devicetracker;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

class DeviceTrackerService extends Service {


    //CONSTANT
    private static final long UPDATE_INTERVAL_MS        = 6000; // 6 Second
    private static final long FASTEST_UPDATE_INTEVAL_MS = 2000; // 2 Second
    //END OF CONSTANT


    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //Creating Location Request Parameter
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTEVAL_MS);
        //End Creating Location Request Parameter


        //Location Callback Listener
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        System.out.println("locationCallback : "+location.getLatitude()+", "+location.getLongitude());
                    }
                }
            }
        };
        //End Location Callback Listener


        startGetLocationDevice();

        return START_NOT_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void startGetLocationDevice(){

        //Get Last Location Know Device, this will return null if the Location Service is disabled before.
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    System.out.println("locationCallback : "+location.getLatitude()+", "+location.getLongitude());
                }
            }
        });
        //End Get Last Location Know Device


        //Start Request Location Change, so when the device's location change it will be send data to callback function
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        //locationRequest   : The Option, it set on the startDeviceTrack function
        //locationCallback  : The Callback, when the device's location change it will be send data to locationCallback function

        //End Start Request Location Change
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
