package com.sensei.gesture.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class SensorService extends GestureService implements SensorEventListener {

    protected SensorManager sManager;
    private Sensor[] mSensors;
    private int[] sensorDelays;

    public SensorService () {
    }

    public void init (SensorManager sManager, GestureListener listener, Sensor [] sensors, int[] delays) {
        super.init (listener);
        this.sManager = sManager;
        mSensors = sensors;
        sensorDelays = delays;
        register ();
    }

    public void register () {
        for (int x = 0; x < mSensors.length; x++) {
            sManager.registerListener(this, mSensors[x], sensorDelays [x]);
        }
    }

    public void unRegister () {
    }
}
