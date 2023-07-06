package com.dawn.library.util.wifi;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * WiFi连接工具类
 */
public class WifiConnectFactory {
    private Context context;
    public WifiConnectFactory(Context context){
        this.context = context;
    }
    private LWifiBroadcastReceiver mReceiver;//WiFi广播
    private LWifiMgr mWifiMgr;
    private List<ScanResult> mScanResults = new ArrayList<>();//扫描到的可用的WiFi列表


    private List<String> resultNameList = new ArrayList<>();
    private String connectSSID;//当前连接的WIFI的SSID

    private OnWifiListener mListener;
    public void setListener(OnWifiListener listener){
        this.mListener = listener;
    }

    /**
     * WiFi初始化
     */
    public void initWifi(){
        createWifiMgr();//创建WiFi管理类
        openWifi();//打开WiFi，创建WiFi广播
        getConnectWifiSsid();//获取当前连接的WiFi
    }

    /**
     * WiFi链接
     */
    public void connectWifi(String ssid, String pwd){
        try{
            createWifiMgr();
            mWifiMgr.connectWifi(ssid, pwd, mScanResults);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 清除当前WiFi连接
     */
    public void clearWifi(String ssid){
        createWifiMgr();
        mWifiMgr.clearWifiConfig(ssid);
    }

    /**
     * 注册监听WiFi操作的系统广播
     */
    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mReceiver = new LWifiBroadcastReceiver() {
            @Override
            public void onWifiEnabled() {
                //WiFi已开启，开始扫描可用WiFi
                if(mWifiMgr != null)
                    mWifiMgr.startScan();
            }

            @Override
            public void onWifiDisabled() {
                //WiFi已关闭，清除可用WiFi列表
                if(mWifiMgr != null)
                    mScanResults.clear();
            }

            @Override
            public void onScanResultsAvailable(List<ScanResult> scanResults) {
                //扫描周围可用WiFi成功，设置可用WiFi列表
                if(mScanResults != null){
                    mScanResults.clear();
                    mScanResults.addAll(scanResults);
                    getSsidNameList();
                    refreshWifiList();
                }
            }

            @Override
            public void onWifiConnected(String connectedSSID) {
                //判断指定WiFi是否连接成功
                if(mListener != null)
                    mListener.onWifiConnectStatus(connectedSSID, true);
            }

            @Override
            public void onWifiDisconnected() {
                //wifi 连接失败
                if(mListener != null)
                    mListener.onWifiConnectStatus(null, false);
            }
        };
        context.registerReceiver(mReceiver, filter);
    }
    /**
     * 创建WiFi管理类
     */
    private void createWifiMgr(){
        if(mWifiMgr == null)
            mWifiMgr = new LWifiMgr(context);
    }
    /**
     * 开启WiFi
     */
    private void openWifi(){
        registerWifiReceiver();
//        LLog.e("open wifi ");
        if(mWifiMgr.isWifiEnabled()) {
            mWifiMgr.startScan();
        } else {
            mWifiMgr.openWifi();
        }
    }
    /**
     * 获取ssid得列表
     */
    private void getSsidNameList(){
        resultNameList.clear();
        for(int i = 0; i < mScanResults.size(); i ++){
            if(("\"" + mScanResults.get(i).SSID + "\"").equals(connectSSID))
                continue;
            resultNameList.add(mScanResults.get(i).SSID);
        }
    }
    /**
     * 获取当前连接WIFI的名称
     */
    private void getConnectWifiSsid(){
        connectSSID = mWifiMgr.getConnectedSSID();
        if(mListener != null)
            mListener.getWifiSSID(connectSSID);
    }
    /**
     * 刷新WIFI列表
     */
    private void refreshWifiList(){
        if(resultNameList == null || resultNameList.size() == 0)
            return;
        if(mListener != null)
            mListener.getWifiList(resultNameList);
    }
}
