package com.syam.helpers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Syamesh on 02-06-2017.
 */

public class DatabaseFactory {

    private static DatabaseFactory instance;

    private Context context;
    private HashMap<String, Database> list = new HashMap<String, Database>();

    //no outer class can initialize this class's object
    private DatabaseFactory() {}

    public static DatabaseFactory getInstance() {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null) {
            instance = new DatabaseFactory();
        }
        return instance;
    }

    public void init(Context context){
        this.context = context;
    }

    public Database getDatabase(String databaseName){
        if(list.containsKey(databaseName)){
            return list.get(databaseName);
        }
        Log.d("Database","Creating new database object :"+databaseName);
        //Create a new database
        SQLiteDatabase sqliteDatabase = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        Database database = new Database(sqliteDatabase);
        //Add to the list
        list.put(databaseName,database);
        return database;
    }
}
