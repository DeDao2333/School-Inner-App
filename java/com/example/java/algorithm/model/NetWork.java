package com.example.java.algorithm.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 59575 on 2018/2/28.
 */

public class NetWork {

    /**
     * to judge the net is worked or bad
     * @param context
     * @return
     */
    public static boolean isConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }
}
