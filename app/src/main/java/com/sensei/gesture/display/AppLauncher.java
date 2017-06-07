package com.sensei.gesture.display;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sensei.gesture.database.Database;
import com.sensei.gesture.properties.Action;
import com.sensei.gesture.properties.Properties;
import com.sensei.gesture.sensors.GestureApp;
import com.sensei.gesture.R;


public class AppLauncher extends AppCompatActivity {

    private static final String DEBUG_TAG = "gestureMonitor";
    private GestureApp myGestureApp;
    private Properties myProperties;
    private Database db;
    private Properties dataProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);
        db = new Database(this, "db", null, 1);
        if(db.isEmpty()){
            Log.i(DEBUG_TAG, "db is empty");
            myProperties = new Properties(this);
            myProperties.setSmikSmak("shake", new Action("messenger"));
            db.add (myProperties);
        }
        else {
            Log. i (DEBUG_TAG, "db is not empty");
            dataProperties = db.getData();
            Log. i (DEBUG_TAG, "properties loaded");
        }

        if (dataProperties != null) {
            Log.i(DEBUG_TAG, "data is not null");
            Log.i(DEBUG_TAG, dataProperties.getAction("shake"));
        }
        else
            Log.i (DEBUG_TAG, "data is null");
        /*
        myProperties = new Properties (this);
        myGestureApp = new GestureApp (this, myProperties); //make sure properties are initialized.
        myGestureApp.enableGesture (this, "test");
        */
    }


    public void showTime (View view) {
        TextView testText = (TextView)findViewById (R.id.testText);
        testText.setText (myGestureApp.getTimeFromService());
    }

    public void enableShaketoOpenWhatsApp (View view) {
        SmikFunctions.enableSmikSmak (this, myGestureApp, myProperties, "shake", new Action("whatsapp"), db);
        myProperties = db.getData();
        Log.i (DEBUG_TAG, myProperties.getAction ("shake"));
    }

    public void disableShaketoOpenWhatsApp (View view) {
        SmikFunctions.disableSmikSmak (this, myGestureApp, myProperties, "shake", db);
    }
}