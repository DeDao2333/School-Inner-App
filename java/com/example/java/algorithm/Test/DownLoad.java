package com.example.java.algorithm.Test;

import android.os.AsyncTask;

/**
 * Created by 59575 on 2018/3/1.
 */

public class DownLoad extends AsyncTask<Void, Integer, Boolean> {

    private DownState mDownState;

    public void setDownState(DownState downState) {
        mDownState=downState;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
