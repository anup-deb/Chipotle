package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public abstract class GestureService extends Service implements SensorEventListener{

    private final IBinder gestureBinder = new MyLocalBinder();
    private SensorManager sManager;
    protected GestureListener mListener;
    private Sensor[] mSensors;
    private int [] sensorDelays;

    public GestureService (){
    }

    public void init (Context context, GestureListener listener, Sensor[] sensors, int [] delays) {
        sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mListener = listener;
        mSensors = sensors;
        sensorDelays = delays;
        register ();
    }

    public void register () {
        for (int x = 0; x < mSensors.length; x++) {
            sManager.registerListener(this, mSensors[x], sensorDelays [x]);
        }
    }

    ///////////////////////////// GestureListener interface //////////////////////////////////

    interface GestureListener {
        void onShake ();
        void onSwipeRight();
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return gestureBinder;
    }

    private class MyLocalBinder extends BinderSub {
        @Override
        GestureService getService(){
            return GestureService.this;
        }
    }
}
