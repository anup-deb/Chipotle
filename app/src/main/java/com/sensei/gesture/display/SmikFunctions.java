package com.sensei.gesture.display;

import android.content.Context;
import android.widget.Toast;

import com.sensei.gesture.database.Database;
import com.sensei.gesture.properties.Action;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.GestureApp;

class SmikFunctions {

    static void enableSmikSmak (Context context, GestureApp gestureApp, Properties prop, String gesture, Action action, Database db) {
        boolean valid = prop.setSmikSmak(gesture, action);
        if (!valid) {
            Toast.makeText (context, "Invalid gesture", Toast.LENGTH_LONG).show();
        }
        else {
            gestureApp.enableGesture(context, gesture);
            db.update(prop);
            Toast.makeText (context, "Gesture successfully enabled", Toast.LENGTH_LONG).show();
        }
    }

    static void disableSmikSmak (Context context, GestureApp gestureApp, Properties prop, String gesture, Database db) {
        boolean valid = prop.removeSmikSmak(gesture);
        if (!valid) {
            Toast.makeText (context, "Gesture not enabled", Toast.LENGTH_LONG).show();
        }
        else {
            gestureApp.disableGesture(context, gesture);
            db.update(prop);
            Toast.makeText (context, "Gesture successfully disabled", Toast.LENGTH_LONG).show();
        }
    }
}
