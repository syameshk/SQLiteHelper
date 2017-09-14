package com.syam.helpers.database;

import android.content.ContentValues;

import java.util.Date;
import java.util.Map;

public class Insert extends SqlParser {


    private static final String INSERT_FORMAT = "INSERT INTO %s(%s) VALUES(%s);";

    private String tableName;
    private ContentValues cv;


    public String getTableName() {
        return tableName;
    }

    public ContentValues getContentValues() {
        return cv;
    }

    public Insert(String tableName) {
        this.tableName = tableName;
    }

    private Insert add(String name, String value) {
        cv.put(name,value);
        return this;
    }

    public Insert col(String name, String value) {
        cv.put(name,value);
        return this;
    }

    public Insert col(String name, int value) {
        cv.put(name,value);
        return this;
    }

    public Insert col(String name, Boolean value) {
        cv.put(name,value);
        return this;
    }

    public Insert col(String name, Date value) {
        cv.put(name,value.getTime());
        return this;
    }

    public Insert col(String name, long value) {
        cv.put(name,value);
        return this;
    }

    @Override
    protected String getSql() {
        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();

        boolean firstRun = true;
        for (Map.Entry<String, Object> entry : cv.valueSet()) {
            if(entry.getValue() == null)
                continue;

            if(!firstRun){
                cols.append(",");
                vals.append(",");
            }
            firstRun = false;
            cols.append(entry.getKey());
            vals.append("'"+entry.getValue()+"'");
        }


        return String.format(INSERT_FORMAT, tableName, cols.toString(), vals.toString());
    }

    @Override
    protected Insert clear() {
        tableName = "";
        cv = new ContentValues();
        return this;
    }
}
