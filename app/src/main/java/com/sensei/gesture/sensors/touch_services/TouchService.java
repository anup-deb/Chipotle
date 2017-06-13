package com.sensei.gesture.sensors.touch_services;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.sensei.gesture.sensors.GestureService;

public abstract class TouchService extends GestureService {

    View mView;

    public TouchService () {
    }

    public void init (View v) {
        mView = v;
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchGesture (v, event);
                //return false; //true if the listener has consumed the event, false otherwise.
            }
        });
    }

    public abstract boolean onTouchGesture (View v, MotionEvent event);

    ///////////////////////////// Abstract methods from superclass /////////////////////////////

    public void init (Context context, String configuration) {}

    public void unRegisterSensors () {}
}
