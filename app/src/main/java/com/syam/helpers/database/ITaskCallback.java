package com.syam.helpers.database;

/**
 * Created by Syamesh on 01-06-2017.
 */

public interface ITaskCallback {
    public void insertCallback(int rows, long time);
    public void insertFailedCallback(String message);
    public void queryCallback(Cursor cursor, long time);
    public void queryFailedCallback(String message);
}
