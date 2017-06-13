package com.sensei.gesture.sensors.touch_services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;

import com.sensei.gesture.sensors.BinderSub;

public class TouchEventManagerTemplate extends TouchService {

    private final IBinder GESTURE_BINDER = new TouchEventManagerTemplate.MyLocalBinder();

    public TouchEventManagerTemplate() {
    }

    public void init (View v) {
        super.init (v);
    }

    public boolean onTouchGesture (View v, MotionEvent event) {
        //TODO: detect touch gestures using event data
        return true; //true if the listener has consumed the event, false otherwise.
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        return GESTURE_BINDER;
    }

    public class MyLocalBinder extends BinderSub {
        public TouchEventManagerTemplate getService(){
            return TouchEventManagerTemplate.this;
        }
    }
}
