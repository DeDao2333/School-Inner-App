package com.example.java.algorithm.Test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.java.algorithm.R;

public class Main3Activity extends BaseAc {

    private MyService.downBinder mBinder;

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyService.downBinder) service;
            mBinder.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button button = findViewById(R.id.bt_send_broad);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("sign down");
                sendBroadcast(intent);
            }
        });
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, mServiceConnection, 0);

    }
}
