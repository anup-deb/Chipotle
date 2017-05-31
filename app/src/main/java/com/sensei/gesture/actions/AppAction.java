package com.sensei.gesture.actions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.util.List;


public class AppAction extends Action {
    public AppAction(){
    }

    /**
     * Opens application given its package name. If not found on device, goes to Play Store.
     * @param context current context
     * @param packageName package name of the application that should be opened
     */

    protected void openApp(Context context, String packageName){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if(intent == null) {     //executes if the activity is not found
            goToAppStore(context, intent, packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(intent);
    }

    /**
     * Goes to Google Play Store to the page of the given packageName, tries to use Play Store app,
     * but opens online version if app is not found.
     * @param intent Intent for executing the application
     * @param packageName Name of application on Play Store
     */
    protected void goToAppStore(Context context, Intent intent, String packageName) {
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + packageName));
            context.startActivity(intent);
        }
    }

    /* the following are optional methods that we might use for testing
       ________________________________________________________________   */

    /**
     * Opens a Map application at a given location.
     * @param context
     */
    protected void openMap(Context context, String locations){
        // Build the intent
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            context.startActivity(mapIntent);
        }
    }

    protected void makeCall(Context context, String numbers) {
        // Build the intent
        Uri number = Uri.parse("tel:" + numbers);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        // Verify it resolves
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, packageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            context.startActivity(callIntent);
        }
    }
}
