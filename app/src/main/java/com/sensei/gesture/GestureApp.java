package com.sensei.gesture;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

class GestureApp {

    private static final String TAG = "sensorMonitor";
    private SensorManager sensorManager;
    private List <Sensor> deviceSensors;

    private GyroService gyroService;

    private boolean isGyroBound = false;

    GestureApp (Context context){

        Log.i(TAG, "hi");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList (Sensor.TYPE_ALL);
        //for (int x = 0; x<deviceSensors.size(); x++)
        //{
        //    Log.i(TAG, deviceSensors.get(x).toString());
        //}
        Intent i = new Intent (context, GyroService.class);
        context.bindService(i, gyroConnection, Context.BIND_AUTO_CREATE);
    }

    String getTimeFromService(){
        return gyroService.getCurrentTime();
    }

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
