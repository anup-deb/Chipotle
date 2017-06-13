package com.sensei.gesture.sensors.sensor_services;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import com.sensei.gesture.sensors.GestureService;

public abstract class SensorService extends GestureService implements SensorEventListener {

    protected SensorManager sManager;
    private Sensor[] mSensors;
    private int[] sensorDelays;

    public SensorService () {
    }

    public void init (SensorManager sManager, Sensor [] sensors, int[] delays) {
        this.sManager = sManager;
        mSensors = sensors;
        sensorDelays = delays;
        registerSensors ();
    }

    public void registerSensors () {
        for (int x = 0; x < mSensors.length; x++) {
            sManager.registerListener(this, mSensors[x], sensorDelays [x]);
        }
    }

    ///////////////////////////// Abstract methods from superclass /////////////////////////////

    public void unRegisterSensors () {
        sManager.unregisterListener(this);
    }

    public void init (View v) {}
}
