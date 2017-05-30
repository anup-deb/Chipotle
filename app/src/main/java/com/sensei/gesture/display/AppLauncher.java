package com.sensei.gesture.display;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sensei.gesture.sensors.GestureApp;
import com.sensei.gesture.R;

public class AppLauncher extends AppCompatActivity {

    GestureApp myGestureApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

        myGestureApp = new GestureApp (this);
    }

    public void showTime (View view){
        TextView testText = (TextView)findViewById (R.id.testText);
        testText.setText (myGestureApp.getTimeFromService());
    }
}