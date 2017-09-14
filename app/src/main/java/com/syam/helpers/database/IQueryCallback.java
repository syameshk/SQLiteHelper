package com.syam.helpers.database;

import java.util.LinkedList;

/**
 * Created by Syamesh on 03-06-2017.
 */

public interface IQueryCallback<E> {
    public void onSuccess(LinkedList<E> result, long time);
    public void onFail(String message);
}
