package com.dawn.library.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dawn.library.util.LStringUtil;

/**
 * activity基类
 */
@SuppressWarnings("unused")
public abstract class LBaseActivity extends AppCompatActivity {
    protected ActivityBroadcastReceiver mReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//继承AppCompatActivity隐藏标题栏方法
        initBaseData();
        initData();//请求数据
        setContentView(getContextView());//设置xml页面
        initView();//初始化控件
        addListener();//给控件添加值和添加事件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver != null)
            unregisterReceiver(mReceiver);
    }

    protected abstract void initBaseData();//基类要统一实现的方法
    protected abstract void initData();//请求页面数据
    protected abstract @LayoutRes int getContextView();//获取页面的xml文件
    protected abstract void initView();//初始化控件
    protected abstract void addListener();//给控件赋值或添加事件
    protected abstract @NonNull String getReceiverAction();//获取广播的action
    protected abstract void getReceiverMsg(Intent intent);//获取广播发过来的信息

    /////////页面跳转开始/////////////
    protected void jumpToActivity(Class<?> mClass){
        startActivity(new Intent(this, mClass));
    }
    protected void jumpToActivity(Class<?> mClass, int requestCode){
        startActivityForResult(new Intent(this, mClass), requestCode);
    }
    protected void jumpToActivity(Class<?> mClass, Bundle bundle){
        startActivity(new Intent(this, mClass).putExtras(bundle));
    }
    protected void jumpToActivity(Class<?> mClass, Bundle bundle, int requestCode){
        startActivityForResult(new Intent(this, mClass).putExtras(bundle), requestCode);
    }
    protected void jumpToActivity(String className) throws ClassNotFoundException {
        Class<?> mClass = Class.forName(className);
        jumpToActivity(mClass);
    }
    protected void jumpToActivity(String className, int requestCode) throws ClassNotFoundException {
        Class<?> mClass = Class.forName(className);
        jumpToActivity(mClass, requestCode);
    }
    protected void jumpToActivity(String className, Bundle bundle) throws ClassNotFoundException {
        Class<?> mClass = Class.forName(className);
        jumpToActivity(mClass, bundle);
    }
    protected void jumpToActivity(String className, Bundle bundle, int requestCode) throws ClassNotFoundException {
        Class<?> mClass = Class.forName(className);
        jumpToActivity(mClass, bundle, requestCode);
    }
    //////////页面跳转结束////////

    //////////////吐司提示开始////////////////
    protected void toast(String msg){
        Toast.makeText(this, LStringUtil.isEmpty(msg) ? "" : msg, Toast.LENGTH_SHORT).show();
    }
    protected void toastLong(String msg){
        Toast.makeText(this, LStringUtil.isEmpty(msg) ? "" : msg, Toast.LENGTH_LONG).show();
    }
    protected void toastUI(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LBaseActivity.this, LStringUtil.isEmpty(msg) ? "" : msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void toastUILong(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LBaseActivity.this, LStringUtil.isEmpty(msg) ? "" : msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    /////////////吐司提示结束/////////////////

    /**
     * 启动当前应用设置页面
     */
    protected void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 广播注册
     */
    protected void registerReceiver(){
        if(LStringUtil.isEmpty(getReceiverAction())){
            return;
        }
        mReceiver = new ActivityBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getReceiverAction());
        registerReceiver(mReceiver, intentFilter);
    }

    protected class ActivityBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null)
                return;
            getReceiverMsg(intent);
        }
    }

}
