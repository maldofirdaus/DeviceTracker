package com.neuron.devicetracker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;


public class DeviceTracker {

    private Context context;

    public void startDeviceTrack(Context context, Activity activity){
        this.context = context;

        if(!isOsMarshmallow()){
            context.startService(new Intent(context, DeviceTrackerService.class));
        }else if(isPermissionLocationGranted()){
            context.startService(new Intent(context, DeviceTrackerService.class));
        }else{ //Is Marshmallow and permission not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        }
    }

    public void stopDeviceTrack(){

    }

    private boolean isOsMarshmallow() {
        return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isPermissionLocationGranted(){
        return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
