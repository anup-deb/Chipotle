package com.sensei.gesture.sensors.sensor_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import com.sensei.gesture.sensors.BinderSub;

public class SensorEventManagerTemplate extends SensorService {

    private final IBinder SENSOR_GESTURE_BINDER = new SensorEventManagerTemplate.MyLocalBinder();
    private Sensor[] sensors;
    private int[] delays;

    public SensorEventManagerTemplate() {
    }

    public void init(Context context, String configuration) {
        SensorManager sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        setSensors (sManager, configuration);
        super.init (sManager, sensors, delays);
    }

    //Based on the configuration set the appropriate sensors and their delays
    private void setSensors (SensorManager sManager, String configuration) {
        //TODO: implement this method
        //TODO: insert configuration if statements

        //example code below
        //final int NUM_SENSORS = 1;
        //sensors = new Sensor [NUM_SENSORS];
        //delays = new int [NUM_SENSORS];
        //sensors[0] = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //delays[0] = SensorManager.SENSOR_DELAY_NORMAL;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        return SENSOR_GESTURE_BINDER;
    }

    public class MyLocalBinder extends BinderSub {
        public SensorEventManagerTemplate getService(){
            return SensorEventManagerTemplate.this;
        }
    }
}
