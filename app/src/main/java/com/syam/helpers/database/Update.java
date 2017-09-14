package com.syam.helpers.database;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Syamesh on 31-05-2017.
 */

public class Update extends SqlParser {

    private String tableName;
    private ContentValues cv;

    private LinkedList<String> where;
    private LinkedList<String> whereValues;

    public Update(String tableName) {
        this.tableName = tableName;
        cv = new ContentValues();
        this.where = new LinkedList<String>();
        this.whereValues = new LinkedList<String>();

    }

    private Update add(String name, String value) {
        cv.put(name, value);
        return this;
    }

    public Update col(String name, String value) {
        cv.put(name, value);
        return this;
    }

    public Update col(String name, int value) {
        cv.put(name, value);
        return this;
    }

    public Update col(String name, Boolean value) {
        cv.put(name, value);
        return this;
    }

    public Update col(String name, Date value) {
        cv.put(name, value.getTime());
        return this;
    }

    public Update col(String name, long value) {
        cv.put(name, value);
        return this;
    }

    // --- Wheres --- //

    private Update where(String column, String op, Object value) {

        return where(column + " " + op + " %s", String.valueOf(value));
    }

    public Update where(String value, String data) {
        if (!value.isEmpty()) {
            where.add(value);
            if (data != null && !data.isEmpty())
                whereValues.add(data);
        }
        return this;
    }

    public Update where(String value) {
        if (!value.isEmpty()) {
            where.add(value);
        }
        return this;
    }

    public Update and() {
        if (!where.isEmpty()) {
            where(" AND ");
        }

        return this;
    }

    public Update or() {
        if (!where.isEmpty()) {
            where(" OR ");
        }

        return this;
    }

    public Update in(List<String> params) {
        String paramCount = "";
        for (int i = 0; i < params.size(); i++) {
            if (i != params.size() - 1) {
                paramCount += "%s, ";
            }else{
                paramCount += "%s";
            }
        }

        if (!where.isEmpty() && params.size() > 0) {
            where(" IN (" + paramCount + ")");
            whereValues.addAll(params);
        }

        return this;
    }

    public Update equal(String column, Object value) {
        return where(column, SqlParser.EQUAL, value);
    }

    public Update equalStr(String column, Object value) {
        return where(column, SqlParser.EQUAL, "'" + value + "'");
    }

    public Update greater(String column, Object value) {
        return where(column, SqlParser.GREATER, value);
    }

    public Update greaterEqual(String column, Object value) {
        return where(column, SqlParser.GREATER_EQUAL, value);
    }

    public Update smaller(String column, Object value) {
        return where(column, SqlParser.SMALLER, value);
    }

    public Update smallerEqual(String column, Object value) {
        return where(column, SqlParser.SMALLER_EQUAL, value);
    }

    public Update like(String column, String value) {
        return where(column, SqlParser.LIKE, "'" + value + "'");
    }

    @Override
    protected String getSql() {

        String sql = "UPDATE " + tableName + " SET";

        List<String> cols = new ArrayList<>();
        List<String> vals = new ArrayList<String>();

        boolean firstTime = true;
        for (Map.Entry<String, Object> entry : cv.valueSet()) {
            sql += (firstTime) ? "" : ",";
            sql += String.format("%s %s", entry.getKey(), entry.getValue());
        }

        if (!where.isEmpty())
            sql += " WHERE " + Utils.breakList("", where, whereValues);

        return sql;
    }

    @Override
    protected Update clear() {
        tableName = "";
        cv = new ContentValues();
        return this;
    }

    //Getters

    public String getTableName() {
        return tableName;
    }

    public ContentValues getContentValues() {
        return cv;
    }

    public String getSelection() {
        if (where.size() > 0)
            return Utils.breakList("", where).replace("%s", "?");
        else
            return null;
    }

    public String[] getSelectionArgs() {
        if (whereValues.size() > 0)
            return whereValues.toArray(new String[whereValues.size()]);
        else
            return null;
    }
}
