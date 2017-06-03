package com.sensei.gesture.sensors;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestService extends GestureService {

    private final IBinder testBinder = new MyLocalBinder();

    public TestService(){
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        return (df.format(new Date()));
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return testBinder;
    }

    private class MyLocalBinder extends BinderSub {
        public TestService getService(){
            return TestService.this;
        }
    }
}
