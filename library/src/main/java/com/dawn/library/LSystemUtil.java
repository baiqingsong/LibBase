package com.dawn.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;

/**
 * 系统工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LSystemUtil {

    /**
     * 重启手机
     */
    public static void reboot(){
        try {
            Runtime.getRuntime().exec(new String[]{"su","-c","reboot"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开系统的设置页面
     */
    public static void openSettings(Context context){
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 安装apk
     * @param apkfile apk文件
     */
    public static void installApk(Context context, File apkfile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager == null)
            return;
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     */
    public static void hideSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager == null)
            return;
        View view = ((Activity) context).getCurrentFocus();
        if(view == null)
            return;
        IBinder iBinder = view.getWindowToken();
        if(iBinder == null)
            return;
        inputMethodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
