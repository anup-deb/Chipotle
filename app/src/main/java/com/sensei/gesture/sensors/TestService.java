package com.sensei.gesture.sensors;

import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestService extends GestureService {

    private final IBinder testBinder = new MyLocalBinder();

    public TestService(){
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return testBinder;
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        return (df.format(new Date()));
    }

    private class MyLocalBinder extends BinderSub {
        TestService getService(){
            return TestService.this;
        }
    }
}
