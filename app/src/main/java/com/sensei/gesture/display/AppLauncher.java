package com.sensei.gesture.display;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sensei.gesture.sensors.GestureApp;
import com.sensei.gesture.R;

public class AppLauncher extends AppCompatActivity {

    private static final String DEBUG_TAG = "gestureMonitor";
    GestureApp myGestureApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

        myGestureApp = new GestureApp ();
        myGestureApp.enableGesture (this, "test");
    }

    public void showTime (View view) {
        TextView testText = (TextView)findViewById (R.id.testText);
        testText.setText (myGestureApp.getTimeFromService());
    }

    public void enableShaketoOpen (View view) {
        enableSmikSmak (this, "shake", "whatsapp");
    }

    public void disableShaketoOpen (View view) {
        disableSmikSmak (this, "shake", "whatsapp");
    }

    public void enableSmikSmak (Context context, String gestureKey, String actionKey) {
        myGestureApp.enableGesture (this, gestureKey);
        //set properties - associate the gesture and the action
        //save properties to phone storage
    }

    public void disableSmikSmak (Context context, String gestureKey, String actionKey) {
        myGestureApp.disableGesture (this, gestureKey);
        //remove properties which associate the gesture and the action
        //remove these properties from the phone storage
    }
}