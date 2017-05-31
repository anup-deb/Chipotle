package com.sensei.gesture.sensors;

import android.os.Binder;

/*
The purpose of this class is to ensure createServiceConnection () in GestureApp that the
getService() method exists.
 */
abstract class BinderSub extends Binder {
    abstract GestureService getService ();
}