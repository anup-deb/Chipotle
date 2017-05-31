package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.gesture.Gesture;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Hashtable;

public class GestureApp implements GestureService.GestureListener {

    private static final String SENSOR_TAG = "sensorMonitor";
    private static final String DEBUG_TAG = "runTimeDebug";
    private Hashtable <String, GestureService> gestureService = new Hashtable <> ();
    private Hashtable <String, Class<? extends GestureService>> gestureServiceClass = new Hashtable<>();
    private Hashtable <String, Class<? extends BinderSub>> gestureBinderClass = new Hashtable <> ();
    private Hashtable <String, Boolean> gestureBound = new Hashtable <> ();

    public GestureApp (){
        initGestureServiceCorrespondence ();
    }

    private void initGestureServiceCorrespondence (){
        //binder classes corresponding to each gesture key
        gestureBinderClass.put ("shake", ShakeEventManager.MyLocalBinder.class.asSubclass(BinderSub.class));
        //gestureBinderClass.put ("swipeRight", null); //TODO: Create SwipeRightEventManager
        gestureBinderClass.put ("gyro", GyroService.MyLocalBinder.class.asSubclass(BinderSub.class));

        //service classes corresponding to each gesture key
        gestureServiceClass.put ("shake", ShakeEventManager.class.asSubclass(GestureService.class));
        gestureServiceClass.put ("gyro", GyroService.class.asSubclass(GestureService.class));

        //init all gesture services to be disabled
        gestureBound.put ("shake", false);
        gestureBound.put ("swipeRight", false);
        gestureBound.put ("gyro", false);
    }

    public String getTimeFromService(){
        GyroService gyroService = (GyroService) gestureService.get("gyro");
        return gyroService.getCurrentTime();
    }

    public void enableGesture (Context context, String gestureKey){
        ServiceConnection mServiceConnection = createServiceConnection (gestureKey);
        Intent i = new Intent (context, gestureServiceClass.get(gestureKey));
        boolean worked = context.bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.i (DEBUG_TAG, "WORKED: " + ((Boolean)worked).toString());
        //i =  new Intent (context, AccelService.class);
        //context.bindService(i, accelConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection createServiceConnection (final String gestureKey){
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                gestureService.put (gestureKey, gestureBinderClass.get(gestureKey).cast(service).getService ());
                gestureBound.put (gestureKey, true);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                gestureBound.put (gestureKey, false);
            }
        };
    }

    /*
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
    }; */

    /////////////////////////// Override GestureListener methods //////////////////////////////

    @Override
    public void onShake() {
    }

    @Override
    public void onSwipeRight() {
    }
}
