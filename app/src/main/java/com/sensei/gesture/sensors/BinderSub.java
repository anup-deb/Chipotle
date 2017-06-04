package com.sensei.gesture.sensors;

import android.os.Binder;

/*
The purpose of this class is to ensure createServiceConnection () in GestureApp that the
getService() method exists.
 */
public abstract class BinderSub extends Binder {
    public abstract GestureService getService ();
}