package com.sensei.gesture.sensors.sensor_services;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sensei.gesture.sensors.BinderSub;

public class ShakeEventManager extends SensorService {

    private static final String DEBUG_TAG = "gestureMonitor";
    private final IBinder SHAKE_BINDER = new ShakeEventManager.MyLocalBinder();
    private Sensor[] sensors;
    private int[] delays;
    private float[] gravity = new float [3];
    private static final float SHAKE_THRESHOLD = 5f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;

    public ShakeEventManager() {
    }

    public void init(Context context, String configuration) {
        SensorManager sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        setSensors (sManager, configuration);
        super.init (sManager, sensors, delays);
    }

    //Based on the configuration set the appropriate sensors and their delays
    private void setSensors (SensorManager sManager, String configuration) {
        //TODO: insert configuration if statements
        sensors = new Sensor [1];
        delays = new int [1];
        sensors[0] = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        delays[0] = SensorManager.SENSOR_DELAY_NORMAL;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    Toast.makeText(this, "shake detected", Toast.LENGTH_SHORT).show();
                    sendOutBroadcast("shake");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void resetAllData (){

    }

    private float calcMaxAccel(SensorEvent event) {
        gravity[0] = calcGravForce(event.values[0], 0);
        gravity[1] = calcGravForce(event.values[1], 1);
        gravity[2] = calcGravForce(event.values[2], 2);

        float accX = event.values[0] - gravity[0];
        float accY = event.values[1] - gravity[1];
        float accZ = event.values[2] - gravity[2];

        float max1 = Math.max(accX, accY);
        return Math.max(max1, accZ);
    }

    // Low pass filter
    private float calcGravForce(float currentVal, int index) {
        final float ALPHA = (float)0.8; //wouldn't actually be, need to calculate
        return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        return SHAKE_BINDER;
    }

    public class MyLocalBinder extends BinderSub {
        public ShakeEventManager getService(){
            return ShakeEventManager.this;
        }
    }
}
