package com.syam.helpers.database;

import java.util.LinkedList;

public class Delete extends SqlParser {

    private String table;
    private LinkedList<String> where;
    private LinkedList<String> whereValues;

    public Delete(String name) {
        table = name;
    }

    // --- Wheres --- //

    private Delete where(String column, String op, Object value) {

        return where(column + " " + op + " %s", String.valueOf(value));
    }

    public Delete where(String value, String data) {
        if (!value.isEmpty()) {
            where.add(value);
            if(data != null && !data.isEmpty())
                whereValues.add(data);
        }
        return this;
    }

    public Delete where(String value) {
        if (!value.isEmpty()) {
            where.add(value);
        }
        return this;
    }

    public Delete and() {
        if (!where.isEmpty()) {
            where(" AND ");
        }

        return this;
    }

    public Delete or() {
        if (!where.isEmpty()) {
            where(" OR ");
        }

        return this;
    }

    public Delete equal(String column, Object value) {
        return where(column, SqlParser.EQUAL, value);
    }

    public Delete equalStr(String column, Object value) {
        return where(column, SqlParser.EQUAL, "'" + value + "'");
    }

    public Delete greater(String column, Object value) {
        return where(column, SqlParser.GREATER, value);
    }

    public Delete greaterEqual(String column, Object value) {
        return where(column,  SqlParser.GREATER_EQUAL, value);
    }

    public Delete smaller(String column, Object value) {
        return where(column, SqlParser.SMALLER, value);
    }

    public Delete smallerEqual(String column, Object value) {
        return where(column,  SqlParser.SMALLER_EQUAL, value);
    }

    public Delete like(String column, String value) {
        return where(column, SqlParser.LIKE, "'" + value + "'");
    }

    // --- Exists or Not --- //

    public Delete exists(String value) {

        return where(" EXISTS ( %s )", String.valueOf(value));
    }

    public Delete notExists(String value) {
        return where(" EXISTS ( %s )", String.valueOf(value));
    }

    // --- Utilities --- //

    private String colAlias(String alias, String column) {
        return alias.isEmpty() ? column : alias + "." + column;
    }

    @Override
    protected String getSql() {
        String sql = DELETE + " " + FROM + " " + table;

        if (!where.isEmpty())
            sql += " " + WHERE + " " + Utils.breakList("", where, whereValues);

        return sql;
    }

    @Override
    protected SqlParser clear() {
        table = "";
        where = new LinkedList<String>();
        whereValues = new LinkedList<String>();

        return this;
    }

    //Getters

    public String getTable() {
        return this.table;
    }

    public String getSelection(){
        if(where.size() > 0)
            return Utils.breakList("", where).replace("%s","?");
        else
            return null;
    }

    public String[] getSelectionArgs(){
        if(whereValues.size() > 0)
            return whereValues.toArray(new String[whereValues.size()]);
        else
            return null;
    }


}
