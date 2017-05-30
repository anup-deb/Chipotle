package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GyroService extends Service {

    private final IBinder gyroBinder = new MyLocalBinder();

    public GyroService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return gyroBinder;
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        return (df.format(new Date()));
    }

    class MyLocalBinder extends Binder {
        GyroService getService(){
            return GyroService.this;
        }
    }
}
