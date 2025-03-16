package com.dawn.library.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;

/**
 * 应用工具类
 */
@SuppressWarnings("unused")
public class LAppUtil {

    /**
     * 获取版本名称
     * @param context 上下文
     *
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     * @param context 上下文
     *
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用大小
     * @param context 上下文
     * @param packageName 包名
     *
     * @return 应用大小
     */
    public static long getAppSize(Context context, String packageName) {
        long appSize = 0;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            appSize = new File(applicationInfo.sourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appSize;
    }

    /**
     * 安装应用
     * @param context 上下文
     * @param filePath 安装路径
     *
     * @return 是否安装成功
     */
    @Deprecated
    public static boolean installApk(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 卸载应用
     * @param context 上下文
     * @param packageName 包名
     *
     * @return 是否卸载成功
     */
    @Deprecated
    public static boolean uninstallApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 启动应用
     * @param context 上下文
     * @param packageName 包名
     */
    public static void runApp(Context context, String packageName) {
        context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage(packageName)));
    }

    /**
     * 清除应用内部缓存
     * @param context 上下文
     */
    @Deprecated
    public static void cleanCache(Context context) {
        LFileUtil.deleteFileByDirectory(context.getCacheDir());
    }

    /**
     * 清除应用内部数据库
     * @param context 上下文
     */
    @Deprecated
    public static void cleanDatabases(Context context) {
        String filepath = String.format(context.getFilesDir().getParent() + File.separator + "%s", "databases");
        LFileUtil.deleteFileByDirectory(new File(filepath));
    }

    /**
     * 清除应用内部sp
     * @param context 上下文
     */
    @Deprecated
    public static void cleanSharedPreference(Context context) {
        String filepath = String.format(context.getFilesDir().getParent() + File.separator + "%s", "shared_prefs");
        LFileUtil.deleteFileByDirectory(new File(filepath));
    }
}
