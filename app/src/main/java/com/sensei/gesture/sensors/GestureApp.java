package com.sensei.gesture.sensors;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sensei.gesture.actions.ExecAction;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.sensor_services.SensorService;
import com.sensei.gesture.sensors.sensor_services.ShakeEventManager;

import java.util.Hashtable;

public class GestureApp implements GestureService.GestureListener {

    private static final String DEBUG_TAG = "gestureMonitor";

    /* Use gestureService to store currently existing services */
    private Hashtable <String, GestureService> gestureService = new Hashtable <> ();
    private Hashtable <String, Class<? extends GestureService>> gestureServiceClass = new Hashtable<>();
    private Hashtable <String, ServiceConnection> gestureConnection = new Hashtable <> ();
    private Properties myProperties;
    private Context mContext;

    public GestureApp (Context context, Properties properties){
        mContext = context;
        if (properties == null){
            Log.i (DEBUG_TAG, "NULL PROPERTIES");
        }
        myProperties = properties;
        initGestureServiceCorrespondence ();
    }

    private void initGestureServiceCorrespondence (){
        //service classes corresponding to each gesture key
        gestureServiceClass.put ("shake", ShakeEventManager.class);
        gestureServiceClass.put ("test", TestService.class);
    }

    public void disableGesture (Context context, String gestureKey) {
        if (isGestureBound(gestureKey)) {
            //if this particular gesture service is a sensor service, disable the sensor event listener
            if (SensorService.class.isAssignableFrom(gestureService.get(gestureKey).getClass())){
                gestureService.get(gestureKey).unRegisterSensors();
            }

            gestureService.remove(gestureKey);
            context.unbindService(gestureConnection.get(gestureKey));
            gestureConnection.remove(gestureKey);
        }
        else {
            Toast.makeText(context, "Service is not connected", Toast.LENGTH_LONG).show();
        }
    }

    public void enableGesture (final Context CONTEXT, final String GESTURE_KEY){
        if (gestureService.get(GESTURE_KEY) == null) {
            gestureConnection.put(GESTURE_KEY, createServiceConnection(GESTURE_KEY));
            Intent i = new Intent(CONTEXT, gestureServiceClass.get(GESTURE_KEY));
            boolean worked = CONTEXT.bindService(i, gestureConnection.get(GESTURE_KEY), Context.BIND_AUTO_CREATE);
            if (worked)
                Log.i(DEBUG_TAG, gestureServiceClass.get(GESTURE_KEY).getName() + " successfully connected as a service :)");
            else
                Log.i(DEBUG_TAG, gestureServiceClass.get(GESTURE_KEY).getName() + " did not connect as a service :(");

            //TODO: take configurations into account

            final long OLD_TIME = System.currentTimeMillis();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    long futureTime = System.currentTimeMillis() + 50;
                    while (!isGestureBound(GESTURE_KEY)) {
                        synchronized (this) {
                            try {
                                wait(futureTime - System.currentTimeMillis());
                            } catch (Exception e) {
                            }
                        }
                    }
                    Log.i(DEBUG_TAG, "Time taken to bind = " + (System.currentTimeMillis() - OLD_TIME) + "ms");
                    initGesture(CONTEXT, GESTURE_KEY);
                }
            };
            Thread waitThread = new Thread(r);
            waitThread.start();
        }
        else {
            Toast.makeText (CONTEXT, "This gesture's service is already bound", Toast.LENGTH_LONG).show ();
        }
    }

    private void initGesture (Context context, String gestureKey) {
        //TODO: call specfic init methods depending on what the gesture is
        if (gestureKey.equals ("shake")) {
            gestureService.get(gestureKey).init(context, this, "configuration");
        }
    }

    private boolean isGestureBound (String gestureKey){
        return gestureService.get(gestureKey) != null;
    }

    /////////////////////////// Test service method //////////////////////////////

    public String getTimeFromService(){
        TestService testService = (TestService) gestureService.get("test");
        return testService.getCurrentTime();
    }

    /////////////////////////// Override GestureListener methods //////////////////////////////

    @Override
    public void onGesture(String gestureKey) {
        String action = myProperties.getAction (gestureKey);
        ExecAction.doAction(mContext, action);
    }

    ///////////////////////////// OP ServiceConnection method //////////////////////////////////

    private ServiceConnection createServiceConnection (final String GESTURE_KEY){
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    Class<? extends BinderSub> binderClass = Class.forName(gestureServiceClass.get(GESTURE_KEY).getName() + "$MyLocalBinder").asSubclass(BinderSub.class);
                    gestureService.put(GESTURE_KEY, binderClass.cast(service).getService());
                }catch (ClassNotFoundException c){
                    Log.i(DEBUG_TAG, "ERROR: could not find MyLocalBinder class");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                gestureService.remove (GESTURE_KEY);
            }
        };
    }
}