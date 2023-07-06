package com.dawn.library.util.wifi;

import java.util.List;

public interface OnWifiListener {
    void onWifiConnectStatus(String ssid, boolean status);//wifi连接状态
    void getWifiSSID(String ssid);//获取wifi名称
    void getWifiList(List<String> list);//获取wifi列表
}
