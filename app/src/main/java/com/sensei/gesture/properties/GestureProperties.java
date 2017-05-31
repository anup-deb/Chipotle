package com.sensei.gesture.properties;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Hashtable;
import java.util.List;

public class GestureProperties extends Properties {

    private static final String TAG = "sensorProperties";
    //which gestures the user can use depending on the sensors of the device
    Hashtable<String, Boolean> validGestures = new Hashtable <> ();

    public void disableGestures (Context context){
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List <Sensor> deviceSensors = sensorManager.getSensorList (Sensor.TYPE_ALL);
        //logDeviceSensors ();
        //TODO: disable specific gestures depending on which sensors are available
    }

    private void logDeviceSensors (List <Sensor> deviceSensors){
        for (int x = 0; x<deviceSensors.size(); x++)
            Log.i(TAG, deviceSensors.get(x).toString());
    }
}
