package com.example.saber.autumntime.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by saber on 2017/3/21.
 */

public class NetWorkState {

    //检查是否连接到网络
    public static boolean networkConnected(Context context){
        if(context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null){
                return networkInfo.isAvailable();
            }

        }
        return false;
    }

    //检查WiFi是否连接
    public static boolean wifiConnected(Context context){
        if(context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null){
                //如果是WiFi
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                    return true;
                }

            }
        }
        return false;
    }

    //检查移动网络是否连接
    public static boolean mobileDataConnected(Context context){
        if(context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null){
                //如果是移动网络
                if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                    return true;
                }

            }
        }
        return false;
    }





}
