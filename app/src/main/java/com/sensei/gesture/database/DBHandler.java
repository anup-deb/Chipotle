package com.sensei.gesture.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensei.gesture.properties.Properties;

import java.lang.reflect.Type;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gestureDB.db";
    private static final String TABLE_NAME = "gesture_actions";
    private static final String COLUMN_PROPERTIES = "product";

    private final Context myContext;

    //We need to pass database information along to superclass
   public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_PROPERTIES + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void update(Properties properties){
        deleteProperties();
        addProperties(properties);
    }

    //Add a new row to the database
    public void addProperties(Properties entry){
        Gson gson = new Gson();
        String entryS = gson.toJson(entry);
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROPERTIES, entryS);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProperties(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE 1");
        db.close();
    }

    public Properties getData(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        String dbString = c.getString(c.getColumnIndex(COLUMN_PROPERTIES));
        db.close();
        Gson gson = new Gson();
        Type type = new TypeToken<Properties>(){}.getType();
        Properties g = gson.fromJson(dbString, type);
        return g;
    }

    public boolean isEmpty(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";
        Cursor mCursor = db.rawQuery(query, null);
        boolean isEmpty = !(mCursor.moveToFirst());
        return isEmpty;
    }
}


