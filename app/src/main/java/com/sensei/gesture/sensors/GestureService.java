package com.sensei.gesture.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public abstract class GestureService extends Service {

    protected GestureListener mListener;

    public GestureService (){
    }

    public void init (Context context, GestureListener listener, String configuration) {
    }

    public void init (GestureListener listener) {
        mListener = listener;
    }

    public abstract void unRegisterSensors ();

    ///////////////////////////// GestureListener interface //////////////////////////////////

    protected interface GestureListener {
        void onGesture (String gestureKey);
    }

    protected void sendOutBroadcast (String gestureKey) {
        Intent i = new Intent ();
        i.setAction ("com.sensei.gesture");
        i.addFlags (Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        i.putExtra ("gestureKey", gestureKey);
        sendBroadcast(i);
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
