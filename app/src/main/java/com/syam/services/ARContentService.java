package com.syam.services;

import com.syam.helpers.database.*;

/**
 * Created by Syamesh on 03-06-2017.
 */

public class ARContentService {
    private Database database;
    private final String tableName = "UserMaster";

    public ARContentService(String database){
        //Get the database instance from the factory class
        this.database = DatabaseFactory.getInstance().getDatabase(database);
        //Create the table if it does not exists
        createTable ();
    }

    public void createTable(){
        //Table rule
        Create table = new Create(tableName)
                .pk("itemId").num("version")
                .str("itemDesc").str("itemName").str("imageUrl")
                .str("customizationData").str("assetBundleUrl");

        //Create the table
        database.ExcecuteNonQuery(table.build());
    }
}
