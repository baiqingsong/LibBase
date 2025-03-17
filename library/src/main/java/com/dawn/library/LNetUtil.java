package com.dawn.library;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 网络工具类
 */
@SuppressWarnings("unused")
public class LNetUtil {
    private static final String NETWORK_TYPE_WIFI = "wifi";
    private static final String NETWORK_TYPE_UNKNOWN = "unknown";
    private static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 获取网络类型
     * @param context 上下文
     *
     * @return int 网络类型
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * 网络是否可用
     * @param context 上下文
     *
     * @return boolean 是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                return info.isAvailable();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 是否是WiFi
     * @param context 上下文
     *
     * @return boolean 是否是WiFi
     */
    public static boolean isWiFi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        if(cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置界面
     * @param activity Activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent();
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 设置WiFi状态
     * @param context 上下文
     * @param enabled 是否打开WIFI
     */
    public static void setWifiEnabled(Context context, boolean enabled) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null)
            return;
        wifiManager.setWifiEnabled(enabled);
    }

    /**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @param host 需要连接的外网
     * @param pingCount ping的次数
     * @param stringBuffer ping的结果
     */
    public static boolean ping(String host, int pingCount, StringBuffer stringBuffer) {
        String line;
        Process process = null;
        BufferedReader successReader = null;
        // String command = "ping -c " + pingCount + " -w 5 " + host;
        String command = "ping -c " + pingCount + " " + host;
        boolean isSuccess = false;
        try {
            process = Runtime.getRuntime().exec(command);
            if (process == null) {
                append(stringBuffer, "ping fail:process is null.");
                return false;
            }
            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = successReader.readLine()) != null) {
                append(stringBuffer, line);
            }
            int status = process.waitFor();
            if (status == 0) {
                append(stringBuffer, "exec cmd success:" + command);
                isSuccess = true;
            } else {
                append(stringBuffer, "exec cmd fail.");
                isSuccess = false;
            }
            append(stringBuffer, "exec finished.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (successReader != null) {
                try {
                    successReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    private static void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text);
            stringBuffer.append("\n");
        }
    }

}
