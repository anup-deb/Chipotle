package com.sensei.gesture.sensors;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class GestureApp {

    private static final String TAG = "sensorMonitor";
    private SensorManager sensorManager;
    private List <Sensor> deviceSensors;

    private AccelService accelService;
    private GyroService gyroService;

    private boolean isAccelBound = false;
    private boolean isGyroBound = false;

    //Constructor
    public GestureApp (Context context){

        Log.i(TAG, "hi");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList (Sensor.TYPE_ALL);
        //for (int x = 0; x<deviceSensors.size(); x++)
        //{
        //    Log.i(TAG, deviceSensors.get(x).toString());
        //}

        Intent i = new Intent (context, GyroService.class);
        context.bindService(i, gyroConnection, Context.BIND_AUTO_CREATE);

        //i =  new Intent (context, AccelService.class);
        //context.bindService(i, accelConnection, Context.BIND_AUTO_CREATE);
    }

    public String getTimeFromService(){
        return gyroService.getCurrentTime();
    }

    private ServiceConnection accelConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AccelService.MyLocalBinder binder = (AccelService.MyLocalBinder) service;
            accelService = binder.getService();
            isAccelBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isAccelBound = false;
        }
    };

    private ServiceConnection gyroConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GyroService.MyLocalBinder binder = (GyroService.MyLocalBinder) service;
            gyroService = binder.getService();
            isGyroBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isGyroBound = false;
        }
    };
}
