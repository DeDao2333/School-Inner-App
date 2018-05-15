package com.example.java.algorithm.Test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private downBinder mDownBinder = new downBinder(new DownState() {
        @Override
        public void Succeed() {

        }

        @Override
        public void Failed() {

        }

        @Override
        public void Pause() {

        }
    });

    private DownLoad mDownLoad;

    class downBinder extends Binder {

        public downBinder(DownState downState) {
            mDownLoad = new DownLoad();
            mDownLoad.setDownState(downState);
        }

        public void start() {
            mDownLoad.execute();
        }

        public void finish() {

        }
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mDownBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
