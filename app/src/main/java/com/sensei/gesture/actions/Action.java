package com.sensei.gesture.actions;

import android.content.Context;

public class Action {

    public static void doAction (Context context, String actionKey) {
        if (actionKey.equals ("whatsapp")) {
            AppAction.openApp (context, "com.whatsapp");
        }
        else {
            //TODO: implement other action calls
        }
    }

}
