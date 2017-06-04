package com.sensei.gesture.actions;

import android.content.Context;

public class ExecAction {

    public static void doAction (Context context, String actionKey) {
        if (actionKey.equals ("whatsapp")) {
            ExecAppAction.openApp (context, "com.whatsapp");
        }
        else {
            //TODO: implement other action calls
        }
    }

}
