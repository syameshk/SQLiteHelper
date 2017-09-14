package com.syam.helpers.database;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Syamesh on 31-05-2017.
 */

public class Query extends SqlParser {
    private String table;
    private LinkedList<String> columns;
    private LinkedList<String> where;
    private LinkedList<String> whereValues;

    private LinkedList<String> grouping;
    private LinkedList<String> order;

    private int limit = 0;
    private int offset = 0;

    public Query(String table) {
        this.table = table;
    }

    // --- Columns --- //


    public Query col(String name) {
        columns.add(name);
        return this;
    }

    public Query cols(String... names) {
        if (names != null) {
            columns.addAll(Arrays.asList((String[]) names));
        }
        return this;
    }

    public Query count() {
        return col("COUNT(*)");
    }


    public Query max(String name) {
        return col("MAX(" + name + ")");
    }

    public Query sum(String name) {
        return col("SUM(" + name + ")");
    }

    // --- Wheres --- //

    private Query where(String column, String op, Object value) {

        return where(column + " " + op + " %s", String.valueOf(value));
    }

    public Query where(String value, String data) {
        if (!value.isEmpty()) {
            where.add(value);
            if(data != null && !data.isEmpty())
                whereValues.add(data);
            }
        return this;
    }

    public Query where(String value) {
        if (!value.isEmpty()) {
            where.add(value);
        }
        return this;
    }

    public Query and() {
        if (!where.isEmpty()) {
            where(" AND ");
        }

        return this;
    }

    public Query or() {
        if (!where.isEmpty()) {
            where(" OR ");
        }

        return this;
    }

    public Query equal(String column, Object value) {
        return where(column, SqlParser.EQUAL, value);
    }

    public Query equalStr(String column, Object value) {
        return where(column, SqlParser.EQUAL, "'" + value + "'");
    }

    public Query greater(String column, Object value) {
        return where(column, SqlParser.GREATER, value);
    }

    public Query greaterEqual(String column, Object value) {
        return where(column,  SqlParser.GREATER_EQUAL, value);
    }

    public Query smaller(String column, Object value) {
        return where(column, SqlParser.SMALLER, value);
    }

    public Query smallerEqual(String column, Object value) {
        return where(column,  SqlParser.SMALLER_EQUAL, value);
    }

    public Query like(String column, String value) {
        return where(column, SqlParser.LIKE, "'" + value + "'");
    }

    public Query not(String column, String value) {
        return where(column, SqlParser.DIFFERENT, value);
    }



    // --- Exists or Not --- //

    public Query exists(String value) {

        return where(" EXISTS ( %s )", String.valueOf(value));
    }

    public Query notExists(String value) {
        return where(" EXISTS ( %s )", String.valueOf(value));
    }

    // --- Group by --- //

    public Query group(String column) {
        grouping.add(column);
        return this;
    }

    public Query group(String alias, String column) {
        grouping.add(colAlias(alias, column));
        return this;
    }

    // --- Order by --- //


    public Query desc(String column) {
        return order(column, "DESC");
    }

    public Query asc(String column) {
        return order(column, "ASC");
    }


    public Query order(String column, String modf) {
        order.add(column + " " + modf);
        return this;
    }


    // --- Generate Select --- //

    @Override
    public Query clear() {
        table = "";
        this.columns = new LinkedList<String >();
        this.where = new LinkedList<String >();
        this.whereValues = new LinkedList<String >();
        this.grouping = new LinkedList<String >();
        this.order = new LinkedList<String >();

        return this;
    }

    @Override
    protected String getSql() {
        String sql = "SELECT ";

        if (columns.isEmpty()) {
            sql += " * ";
        } else {
            sql += get(columns);
        }

        sql += " FROM  " + table;


        if (!where.isEmpty())
            sql += " WHERE " + Utils.breakList("", where,whereValues);

        if (!grouping.isEmpty())
            sql += " GROUP BY " + get(grouping);

        if (!order.isEmpty())
            sql += " ORDER BY " + get(order);


        if (limit > 0) {
            sql += " LIMIT " + getLimit();
        }

        return sql;
    }


    private String get(List<String> list) {
        return Utils.breakList(",", list);
    }

    // --- Utilities --- //

    private String colAlias(String alias, String column) {
        return alias.isEmpty() ? column : alias + "." + column;
    }

    private String asAlias(String column, String alias) {
        return alias.isEmpty() ? column : column + " AS " + alias;
    }

    public Query limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Query offset(int offset) {
        this.offset = offset;
        return this;
    }

    //Getters

    public String getTable() {
        return this.table;
    }

    public String[] getColumns() {
        if(columns.size() > 0)
            return columns.toArray(new String[columns.size()]);
        else
            return null;
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

    public String getGrouping(){
        if(grouping.size() > 0)
            return Utils.breakList(",", grouping);
        else
            return null;
    }

    public String getOrder(){
        if(order.size() > 0)
            return Utils.breakList(",", order);
        else
            return null;
    }

    public String getLimit() {
        if(limit > 0)
            return this.offset+","+this.limit;
        return null;
    }
}
