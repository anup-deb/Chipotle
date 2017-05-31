package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GyroService extends GestureService {

    private final IBinder gyroBinder = new MyLocalBinder();
    private static final String DEBUG_TAG = "runTimeDebug";

    public GyroService (){

    }

    //public GyroService(Context context, GestureListener listener) {
    //    super (context, listener);
    //}

    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        return gyroBinder;
    }

    public String getCurrentTime(){
        Log.i(DEBUG_TAG, "gettime");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        return (df.format(new Date()));
    }

    class MyLocalBinder extends BinderSub {
        GyroService getService(){
            return GyroService.this;
        }
    }
}
