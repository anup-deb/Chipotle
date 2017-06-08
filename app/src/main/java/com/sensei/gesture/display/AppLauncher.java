package com.sensei.gesture.display;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sensei.gesture.database.Database;
import com.sensei.gesture.properties.Action;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.GestureApp;
import com.sensei.gesture.R;
import com.sensei.gesture.sensors.sensor_services.ShakeEventManager;


public class AppLauncher extends AppCompatActivity {

    private static final String DEBUG_TAG = "gestureMonitor";
    private GestureApp myGestureApp;
    private Properties myProperties;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);
        db = new Database(this, "db", null, 1);

        if(db.isEmpty()){
            myProperties = new Properties(this);
            db.add (myProperties);
        }
        else{
            myProperties = db.getData(this);
        }
        myGestureApp = new GestureApp (); //make sure properties are initialized.
        //myGestureApp.enableGesture (this, "test");
    }

    public void showTime (View view) {
        TextView testText = (TextView)findViewById (R.id.testText);
        testText.setText (myGestureApp.getTimeFromService());
    }

    public void enableShaketoOpenWhatsApp (View view) {
        SmikFunctions.enableSmikSmak (this, myGestureApp, myProperties, "shake", new Action("whatsapp"), db);
    }

    public void disableShaketoOpenWhatsApp (View view) {
        //SmikFunctions.disableSmikSmak (this, myGestureApp, myProperties, "shake", db);
        Intent i = new Intent (this, ShakeEventManager.class);
        startService (i);
    }

    public void enableOpenMap (View view) {
        SmikFunctions.enableSmikSmak (this, myGestureApp, myProperties, "shake", new Action ("maps"), db);
    }
}