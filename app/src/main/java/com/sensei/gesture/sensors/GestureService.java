package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class GestureService extends Service {

    private final IBinder gestureBinder = new MyLocalBinder();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    protected GestureListener mListener;

    public GestureService (){

    }

    public GestureService(Context context, GestureListener listener) {
        mListener = listener;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return gestureBinder;
    }

    interface GestureListener {
        void onShake ();
        void onSwipeRight();
    }

    private class MyLocalBinder extends BinderSub {
        @Override
        GestureService getService(){
            return GestureService.this;
        }
    }
}
