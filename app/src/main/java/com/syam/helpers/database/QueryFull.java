package com.syam.helpers.database;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class QueryFull extends SqlParser {

    private LinkedList<String> columns;
    private LinkedList<String> tables;
    private LinkedList<String> leftJoin;
    private LinkedList<String> where;
    private LinkedList<String> grouping;
    private LinkedList<String> order;

    private int limit = 0;

    public QueryFull() {

    }

    @Override
    public QueryFull clear() {
        columns = new LinkedList();
        tables = new LinkedList();
        leftJoin = new LinkedList();
        where = new LinkedList();
        grouping = new LinkedList();
        order = new LinkedList();

        return this;
    }

    // --- Columns --- //


    public QueryFull col(String name) {
        columns.add(name);
        return this;
    }


    public QueryFull cols(String... names) {
        if (names != null) {
            columns.addAll(Arrays.asList((String[]) names));
        }
        return this;
    }


    public QueryFull col(String alias, String name, String nick) {
        return col(colAlias(alias, asAlias(name, nick)));
    }


    public QueryFull col(String name, String nick) {
        return col(asAlias(name, nick));
    }

    public QueryFull count(String alias) {
        return col("COUNT(*)", alias);
    }

    public QueryFull count() {
        return col("COUNT(*)");
    }


    public QueryFull max(String name) {
        return col("MAX(" + name + ")");
    }

    public QueryFull sum(String name) {
        return col("SUM(" + name + ")");
    }

    public QueryFull sum(String name, String alias) {
        return col(asAlias("SUM(" + name + ")", alias));
    }

    // --- Tables --- //

    public QueryFull table(String name) {
        tables.add(name);
        return this;
    }

    public QueryFull table(String name, String alias) {
        return table(name + " " + alias);
    }

    // --- Left Join --- //

    public QueryFull left(String name) {
        leftJoin.add(" LEFT JOIN " + name);
        return this;
    }

    public QueryFull left(String name, String alias) {
        leftJoin.add(" LEFT JOIN " + name + " " + alias);
        return this;
    }

    public QueryFull on(String cAlias, String column, String op, String vAlias, String value) {
        int last = leftJoin.size() - 1;
        if (last > -1) {
            leftJoin.set(last, leftJoin.get(last) + " ON (" + colAlias(cAlias, column) + " " + op + " " + colAlias(vAlias, value) + ")");
        }

        return this;
    }

    // --- Wheres --- //

    private QueryFull where(String column, String op, Object value) {
        return where(column + " " + op + " " + String.valueOf(value));
    }

    public QueryFull where(String value) {
        if (!value.isEmpty()) {
            where.add(value);
        }
        return this;
    }

    public QueryFull and() {
        if (!where.isEmpty()) {
            where("", "AND", "");
        }

        return this;
    }

    public QueryFull or() {
        if (!where.isEmpty()) {
            where("", "OR", "");
        }

        return this;
    }

    private QueryFull where(String cAlias, String column, String op, String value) {
        return where(colAlias(cAlias, column), op, value);
    }

    public QueryFull equal(String cAlias, String column, String vAlias, Object value) {
        return where(cAlias, column, "=", colAlias(vAlias, String.valueOf(value)));
    }

    public QueryFull equal(String cAlias, String column, Object value) {
        return where(cAlias, column, "=", String.valueOf(value));
    }

    public QueryFull equal(String column, Object value) {
        return where(column, "=", value);
    }

    public QueryFull equalStr(String column, Object value) {
        return where(column, "=", "'" + value + "'");
    }

    public QueryFull equalTrim(String column, Object value) {
        return where("TRIM(" + column + ")", "=", "'" + value.toString().trim() + "'");
    }

    public QueryFull greater(String cAlias, String column, Object value) {
        return where(colAlias(cAlias, column), ">", value);
    }

    public QueryFull greater(String column, Object value) {
        return where(column, ">", value);
    }

    public QueryFull greaterEqual(String column, Object value) {
        return where(column, ">=", value);
    }

    public QueryFull smallerEqual(String column, Object value) {
        return where(column, "<=", value);
    }

    public QueryFull in(String column, String subSelect) {
        return where(column + " IN (" + subSelect + ")");
    }

    public QueryFull like(String cAlias, String column, String value) {
        return like(colAlias(cAlias, column), value);
    }

    public QueryFull like(String column, String value) {
        return where(column, "LIKE", "'" + value + "'");
    }


    // --- Exists or Not --- //

    public QueryFull exists(String value) {
        where.add(" EXISTS (" + value + ")");
        return this;
    }

    public QueryFull notExists(String value) {
        where.add(" NOT EXISTS (" + value + ")");
        return this;
    }

    // --- Group by --- //

    public QueryFull group(String column) {
        grouping.add(column);
        return this;
    }

    public QueryFull group(String alias, String column) {
        grouping.add(colAlias(alias, column));
        return this;
    }

    // --- Order by --- //


    public QueryFull desc(String column) {
        return order(column, "DESC");
    }

    public QueryFull desc(String alias, String column) {
        return order(alias, column, "DESC");
    }

    public QueryFull asc(String column) {
        return order(column, "ASC");
    }

    public QueryFull asc(String alias, String column) {
        return order(alias, column, "ASC");
    }

    public QueryFull order(String column, String modf) {
        order.add(column + " " + modf);
        return this;
    }

    private QueryFull order(String alias, String column, String modf) {
        order.add(colAlias(alias, column) + " " + modf);
        return this;
    }


    // --- Generate Select --- //

    @Override
    protected String getSql() {
        String sql = "SELECT ";

        if (columns.isEmpty()) {
            sql += " * ";
        } else {
            sql += get(columns);
        }

        sql += " FROM  " + get(tables);

        if (!leftJoin.isEmpty())
            sql += Utils.breakList("", leftJoin);

        if (!where.isEmpty())
            sql += " WHERE " + Utils.breakList("", where);

        if (!grouping.isEmpty())
            sql += " GROUP BY " + get(grouping);

        if (!order.isEmpty())
            sql += " ORDER BY " + get(order);


        if (limit > 0) {
            sql += " LIMIT " + limit;
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

    public QueryFull limit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getLimit() {
        return limit;
    }
}
