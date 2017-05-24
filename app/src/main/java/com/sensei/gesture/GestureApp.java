package com.sensei.gesture;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

//HI ANUP OMGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG

public class GestureApp extends AppCompatActivity {

    private static final String TAG = "sensorMonitor";
    private SensorManager sensorManager;
    List <Sensor> deviceSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList (Sensor.TYPE_ALL);
        //for (int x = 0; x<deviceSensors.size(); x++)
        //{
        //    Log.i(TAG, deviceSensors.get(x).toString());
        //}
        disableGestures();

        setContentView(R.layout.activity_gesture_app);
    }

    /* Disable specific gestures based on whether or not the device has the necessary sensors
     */
    public void disableGestures (){
        //need to implement

        //if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
        //    //Disable features that require the accelerometer
        //}
    }
}
