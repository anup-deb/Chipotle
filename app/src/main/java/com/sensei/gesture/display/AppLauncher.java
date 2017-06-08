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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);
        db = new Database(this, "db", null, 1);

        if(db.isEmpty()){
            Log.i(DEBUG_TAG, "db is empty");
            myProperties = new Properties(this);
            myGestureApp = new GestureApp (this, myProperties); //make sure properties are initialized.
            myProperties.setGestureApp(myGestureApp);
            db.add (myProperties);
            Log.i(DEBUG_TAG, "added properly, gesture app created");
        }
        else{

            Log.i(DEBUG_TAG, "db is not empty");
            myProperties = db.getData();
            Log.i(DEBUG_TAG, "got data");
            myGestureApp = myProperties.getGestureApp();
            Log.i(DEBUG_TAG, "got data, retrieved gestureapp");
        }
        myGestureApp.enableGesture (this, "test");
        Log.i(DEBUG_TAG, "hooray");
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

    @Override
    protected void onDestroy() {
        myProperties.setGestureApp(myGestureApp);
        db.update(myProperties);
        super.onDestroy();
    }
}