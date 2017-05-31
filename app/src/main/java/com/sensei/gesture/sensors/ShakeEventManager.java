package com.sensei.gesture.sensors;

import android.content.Context;

class ShakeEventManager extends GestureService {

    protected ShakeEventManager (Context context, GestureListener listener){
        super (context, listener);
    }

    class MyLocalBinder extends BinderSub {
        ShakeEventManager getService(){
            return ShakeEventManager.this;
        }
    }
}
