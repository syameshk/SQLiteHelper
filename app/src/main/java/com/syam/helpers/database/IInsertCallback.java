package com.syam.helpers.database;

/**
 * Created by Syamesh on 03-06-2017.
 */

public interface IInsertCallback {
    public void onSuccess(int rows, long time);
    public void onFail(String message);
}
