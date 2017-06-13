package com.sensei.gesture.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

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

    public void unRegisterSensors () {}

    public void init (Context context, String configuration) {}

    public void init (View v) {}
    
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
