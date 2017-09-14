package com.syam.helpers.database;

import android.util.Log;

/**
 * Created by Syamesh on 31-05-2017.
 */

public abstract class SqlParser {


    public static Create create(String tableName) {
        return new Create(tableName);
    }

    public static Update update(String tableName) {
        return new Update(tableName);
    }

    public static QueryFull query() {
        return new QueryFull();
    }

    public static Cursor cursor(android.database.Cursor c) {
        return new Cursor(c);
    }

    public static Insert insert(String tableName) {
        return new Insert(tableName);
    }

    public static Delete delete(String tableName) {
        return new Delete(tableName);
    }

    SqlParser() {
        clear();
    }

    protected abstract String getSql();

    protected abstract SqlParser clear();


    public String build() {
        String sql = getSql();

        Log.v("SQL", sql);

        return sql;
    }





    protected static final String AND = "AND";
    protected static final String OR = "OR";
    protected static final String EQUAL = "=";
    protected static final String GREATER = ">";
    protected static final String SMALLER = "<";
    protected static final String GREATER_EQUAL = ">=";
    protected static final String SMALLER_EQUAL = "<=";
    protected static final String IN = "IN";
    protected static final String DIFFERENT = "<>";
    protected static final String EXISTS = "EXISTS";
    protected static final String NOT = "NOT";
    protected static final String LIKE = "LIKE";

    protected static final String CREATE_INDEX = "CREATE INDEX %s ON %s (%s);";
    protected static final String CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX %s ON %s (%s);";
    protected static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s)";
    protected static final String INTEGER = "INTEGER";
    protected static final String PK = INTEGER + " PRIMARY KEY";
    protected static final String PK_AUTO = PK + " AUTOINCREMENT";
    protected static final String UNIQUE = INTEGER +" UNIQUE";
    protected static final String TEXT = "TEXT";
    protected static final String FLOAT = "FLOAT";

    protected static final String DELETE = "DELETE";
    protected static final String FROM = "FROM";
    protected static final String WHERE = "WHERE";


}
