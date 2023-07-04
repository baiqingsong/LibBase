package com.dawn.library;

import android.app.Application;
import android.content.Context;

import com.dawn.library.util.LCrashHandlerUtil;
import com.dawn.library.util.LLog;

public abstract class LBaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        LLog.init(getApplicationContext(), showLog(), getPackageName());
        LCrashHandlerUtil.getInstance().init(getApplicationContext());
        context = getApplicationContext();
    }
    @SuppressWarnings("InfiniteRecursion")
    public static Context getAppContext(){
        return context;
    }
    public abstract boolean showLog();
}
