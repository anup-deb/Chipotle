package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.util.Log;

public class ShakeEventManager extends GestureService {

    private final IBinder shakeBinder = new ShakeEventManager.MyLocalBinder();
    private float[] gravity = new float [3];
    private int counter = 0;
    private long firstMovTime;
    private float MOV_THRESHOLD;
    private final int MOV_COUNTS;
    private final long SHAKE_WINDOW_TIME_INTERVAL;

    public ShakeEventManager() {
    }

    @Override
    public void init(Context context, GestureListener listener, Sensor[] sensors, int[] delays) {
        super.init(context, listener, sensors, delays);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float maxAcc = calcMaxAcceleration(event);
        Log.d("SwA", "Max Acc ["+maxAcc+"]");
        if (maxAcc >= MOV_THRESHOLD) {
            if (counter == 0) {
                counter++;
                firstMovTime = System.currentTimeMillis();
                Log.d("SwA", "First mov..");
            }
            else {
                long now = System.currentTimeMillis();
                if ((now - firstMovTime) <= SHAKE_WINDOW_TIME_INTERVAL) {
                    counter++;
                }
                else {
                    resetAllData();
                    counter++;
                    return;
                }
                Log.d("SwA", "Mov counter ["+counter+"]");
                if (counter >= MOV_COUNTS) {
                    if (super.mListener != null)
                        super.mListener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void resetAllData (){

    }

    private float calcMaxAcceleration(SensorEvent event) {
        gravity[0] = calcGravityForce(event.values[0], 0);
        gravity[1] = calcGravityForce(event.values[1], 1);
        gravity[2] = calcGravityForce(event.values[2], 2);

        float accX = event.values[0] - gravity[0];
        float accY = event.values[1] - gravity[1];
        float accZ = event.values[2] - gravity[2];

        float max1 = Math.max(accX, accY);
        return Math.max(max1, accZ);
    }

    // Low pass filter
    private float calcGravityForce(float currentVal, int index) {
        final float ALPHA = (float)0.8; //wouldn't actually be, need to calculate
        return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        return shakeBinder;
    }

    private class MyLocalBinder extends BinderSub {
        ShakeEventManager getService(){
            return ShakeEventManager.this;
        }
    }

}
