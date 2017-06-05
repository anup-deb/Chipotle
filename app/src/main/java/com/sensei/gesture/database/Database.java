package com.sensei.gesture.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.sensei.gesture.properties.Properties;



public class Database {
    DBHandler dbHandler;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this.dbHandler = new DBHandler(context, name, factory, version);
    }
    public void add(Properties input) {
        dbHandler.addProperties(input);
    }
    public void delete(){
        dbHandler.deleteProperties();
    }
    public void update(Properties input){
        dbHandler.update(input);
    }


}