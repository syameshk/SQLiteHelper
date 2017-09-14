package com.syam.helpers.database;

import android.os.AsyncTask;

import java.util.Date;

/**
 * Created by Syamesh on 03-06-2017.
 */

public class QueryTask extends AsyncTask{
    private ITaskCallback taskCallback;
    private Database database;
    private long startTime;
    private String message;

    public QueryTask(ITaskCallback taskCallback, Database database) {
        this.taskCallback = taskCallback;
        this.database = database;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(taskCallback != null && o != null) {
            if(o!= null)
                taskCallback.queryCallback((Cursor) o, new Date().getTime() - startTime);
            else
                taskCallback.queryFailedCallback(message);
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        startTime = new Date().getTime();
        Query query = (Query) params[0];
        Cursor cursor = null;
        try {
            cursor = database.Excecute(query);
        }catch (Exception e){
            message = e.getMessage();
        }
        return cursor;
    }
}
