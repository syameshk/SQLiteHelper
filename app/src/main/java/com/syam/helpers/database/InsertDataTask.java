package com.syam.helpers.database;

import android.os.AsyncTask;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Syamesh on 01-06-2017.
 */

public class InsertDataTask extends AsyncTask{
    private ITaskCallback taskCallback;
    private Database database;
    private long startTime;

    public InsertDataTask(ITaskCallback callback, Database database){
        taskCallback = callback;
        this.database = database;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(taskCallback != null && (int)o != -1) {
            taskCallback.insertCallback((int)o,  new Date().getTime() - startTime);
        }
    }

    protected void onFail(String reason) {
        if(taskCallback != null) {
            taskCallback.insertFailedCallback(reason);
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        startTime = new Date().getTime();
        int count = 0;
        LinkedList<Insert> inserts = (LinkedList<Insert>)params[0];

        try {
            for (Insert insert : inserts) {
                database.ExcecuteInsert(insert);
                count++;
            }
        }catch (Exception e){
            count = -1;
            onFail(e.getMessage());
        }

        return count;
    }
}