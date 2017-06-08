package com.sensei.gesture.sensors;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.util.Log;

public abstract class GestureService extends Service {

    private final int SERVICE_RESTART_TIME = 100;
    private static final String DEBUG_TAG = "gestureMonitor";
    boolean isRunning = false;

    public GestureService (){}

    public abstract void init (Context context, String configuration);

    public abstract void unRegisterSensors ();

    protected void sendOutBroadcast (String gestureKey) {
        Intent i = new Intent ();
        i.setAction ("com.sensei.gesture");
        i.addFlags (Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        i.putExtra ("gestureKey", gestureKey);
        sendBroadcast(i);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Intent restartService = new Intent (getApplicationContext(), this.getClass());
        restartService.setPackage (getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService (getApplicationContext(), 1, restartService, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set (AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + SERVICE_RESTART_TIME, restartServicePI);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            isRunning = true;
            init(getApplicationContext(), "configuration");
        }
        else {
            stopSelf (startId);
            unRegisterSensors();
            Log.i (DEBUG_TAG, "WOOHOO");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i (DEBUG_TAG, "service destroyed");
        super.onDestroy();
    }

    ///////////////////////////// Binder stuff //////////////////////////////////

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
