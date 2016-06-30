package com.yiwucheguanjia.carmgr.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/6/28.
 */
public class NetworkConnection {
    private volatile static NetworkConnection networkConnection;
    private NetworkConnection(){

    }

    /**
     * 采用单例模式获取对象
     * @return
     */
    public static NetworkConnection getInstance(){
        NetworkConnection instance = null;
        if (networkConnection == null){
            synchronized (NetworkConnection.class){
                if (instance == null){
                    instance = new NetworkConnection();
                    networkConnection = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 检查当前网络是否可用
     *
     * @param activity 当前上下文
     * @return
     */
    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
