package com.sensei.gesture.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sensei.gesture.database.Database;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.sensor_services.ShakeEventManager;

import java.util.Enumeration;

public class ServiceOnBootReceiver extends BroadcastReceiver {

    private static final String DEBUG_TAG = "gestureMonitor";

    @Override
    public void onReceive(Context context, Intent intent) {
        Database db = new Database(context, "db", null, 1);
        Properties properties = db.getData(context);
        Enumeration<String> enabledGestures = properties.getEnabledGestures();
        String gesture;
        while (enabledGestures.hasMoreElements()) {
            gesture = enabledGestures.nextElement();
            Intent i = new Intent (context, getGestureClass (gesture));
            context.startService (i);
            Log.i(DEBUG_TAG, getGestureClass(gesture).getName() + " successfully connected as a service :)");
        }
    }

    public Class <? extends GestureService> getGestureClass (String gestureKey) {
        if (gestureKey.equals ("shake")) {
            return ShakeEventManager.class;
        }
        else {
            Log.i (DEBUG_TAG, "error on boot");
            return GestureService.class; //never do this
        }
    }
}
