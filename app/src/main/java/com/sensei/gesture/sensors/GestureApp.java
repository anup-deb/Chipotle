package com.sensei.gesture.sensors;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Hashtable;

public class GestureApp implements GestureService.GestureListener {

    private static final String DEBUG_TAG = "gestureMonitor";

    /* Use gestureService to store currently existing services */
    private Hashtable <String, GestureService> gestureService = new Hashtable <> ();
    private Hashtable <String, Class<? extends GestureService>> gestureServiceClass = new Hashtable<>();

    public GestureApp (){
        initGestureServiceCorrespondence ();
    }

    private void initGestureServiceCorrespondence (){
        //service classes corresponding to each gesture key
        gestureServiceClass.put ("shake", ShakeEventManager.class);
        gestureServiceClass.put ("test", TestService.class);
    }

    public void enableGesture (Context context, String gestureKey){
        ServiceConnection mServiceConnection = createServiceConnection (gestureKey);
        Intent i = new Intent (context, gestureServiceClass.get(gestureKey));
        boolean worked = context.bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (worked)
            Log.i (DEBUG_TAG, gestureServiceClass.get(gestureKey).getName() + " successfully connected as a service :)");
        else
            Log.i (DEBUG_TAG, gestureServiceClass.get(gestureKey).getName() + " did not connect as a service :(");
    }

    private boolean isGestureBound (String gestureKey){
        return gestureService.get(gestureKey) != null;
    }

    public String getTimeFromService(){
        TestService testService = (TestService) gestureService.get("test");
        return testService.getCurrentTime();
    }

    /////////////////////////// Override GestureListener methods //////////////////////////////

    @Override
    public void onShake() {
    }

    @Override
    public void onSwipeRight() {
    }

    /////////////////////////// OP ServiceConnection method //////////////////////////////

    private ServiceConnection createServiceConnection (final String gestureKey){
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    Class<? extends BinderSub> binderClass = Class.forName(gestureServiceClass.get(gestureKey).getName() + "$MyLocalBinder").asSubclass(BinderSub.class);
                    gestureService.put(gestureKey, binderClass.cast(service).getService());
                }catch (ClassNotFoundException c){
                    Log.i(DEBUG_TAG, "ERROR: could not find MyLocalBinder class");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                gestureService.remove (gestureKey);
            }
        };
    }
}