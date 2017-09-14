package com.syam.helpers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Syamesh on 31-05-2017.
 */

public class Database {
    private SQLiteDatabase database;

    public Database(SQLiteDatabase database) {
        this.database = database;
    }

    public Database(Context context, String path){
        this.database = context.openOrCreateDatabase(path, Context.MODE_PRIVATE, null);
    }

    public void ExcecuteNonQuery(String query) {
        this.database.execSQL(query);
    }

    public long ExcecuteInsert(String query){
        return this.database.compileStatement(query).executeInsert();
    }

    public long ExcecuteInsert(Insert insert){
        return this.database.insert(insert.getTableName(),null,insert.getContentValues());
    }

    public long ExcecuteInsertReplace(Insert insert){
        return this.database.insertWithOnConflict(insert.getTableName(),null,insert.getContentValues(),SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int ExcecuteUpdate(String query){
        return this.database.compileStatement(query).executeUpdateDelete();
    }

    public int ExcecuteUpdate(Update query){
        return this.database.update(query.getTableName(),query.getContentValues(),query.getSelection(),query.getSelectionArgs());
    }

    public Cursor Excecute(String queryFull){
        return new Cursor(this.database.rawQuery(queryFull,null));
    }

    public Cursor Excecute(Query query) {
        return new Cursor(this.database.query(query.getTable(), query.getColumns(), query.getSelection(), query.getSelectionArgs(),
                query.getGrouping(), null, query.getOrder(), query.getLimit()));
    }

    public int ExcecuteDelete(Delete delete){
        return this.database.delete(delete.getTable(),delete.getSelection(),delete.getSelectionArgs());
    }

    public int DeleteTable(String table){
        return this.database.delete(table,null,null);
    }

    public void DropTable(String table){
        database.execSQL("DROP TABLE IF EXISTS "+table);
    }

}