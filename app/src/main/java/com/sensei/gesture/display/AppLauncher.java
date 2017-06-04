package com.sensei.gesture.display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sensei.gesture.properties.Action;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.GestureApp;
import com.sensei.gesture.R;

public class AppLauncher extends AppCompatActivity {

    private static final String DEBUG_TAG = "gestureMonitor";
    private GestureApp myGestureApp;
    private Properties myProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

        myProperties = new Properties (this);
        myGestureApp = new GestureApp (this, myProperties); //make sure properties are initialized.
        myGestureApp.enableGesture (this, "test");
    }

    public void showTime (View view) {
        TextView testText = (TextView)findViewById (R.id.testText);
        testText.setText (myGestureApp.getTimeFromService());
    }

    public void enableShaketoOpenWhatsApp (View view) {
        SmikFunctions.enableSmikSmak (this, myGestureApp, myProperties, "shake", new Action("whatsapp"));
    }

    public void disableShaketoOpenWhatsApp (View view) {
        SmikFunctions.disableSmikSmak (this, myGestureApp, myProperties, "shake");
    }
}