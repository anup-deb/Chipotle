package com.sensei.gesture.properties;

public class AppAction extends Action{
    private String AppName;
    private String AppPackageName;

    public AppAction(String appName) {
        super(appName);
        AppPackageName = getAppPackageName(appName);
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppPackageName() {
        return AppPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        AppPackageName = appPackageName;
    }

    public String getAppPackageName(String AppName){
        //TODO Find appPackageName given app name
        return "";
    }
}