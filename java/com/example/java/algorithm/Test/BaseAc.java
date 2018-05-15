package com.example.java.algorithm.Test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by 59575 on 2018/2/28.
 */

public class BaseAc extends Activity {

    private Broad_sign mBroad_sign = new Broad_sign();


    //not at the top of stack of activity
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroad_sign);
    }


    //at the top of stack of activity
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("sign down");
        registerReceiver(mBroad_sign, intentFilter);
    }

    class Broad_sign extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("warning");
            builder.setMessage("your account has been signed up in other place ");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }
}
