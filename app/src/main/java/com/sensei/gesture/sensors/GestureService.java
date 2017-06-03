package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class GestureService extends Service {

    protected GestureListener mListener;

    public GestureService (){
    }

    public void init (Context context, GestureListener listener, String configuration) {
    }

    public void init (GestureListener listener) {
        mListener = listener;
    }

    ///////////////////////////// GestureListener interface //////////////////////////////////

    protected interface GestureListener {
        void onGesture (String gestureKey);
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
