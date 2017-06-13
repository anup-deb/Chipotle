package com.sensei.gesture.sensors.sensor_services;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.sensei.gesture.sensors.BinderSub;

public class RotatingLiftEventManager extends SensorService {

    private final IBinder GESTURE_BINDER = new RotatingLiftEventManager.MyLocalBinder();
    private Sensor[] sensors;
    private int[] delays;

    public RotatingLiftEventManager() {
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
        return GESTURE_BINDER;
    }

    public class MyLocalBinder extends BinderSub {
        public RotatingLiftEventManager getService(){
            return RotatingLiftEventManager.this;
        }
    }
}
