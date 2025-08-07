package com.dawn.library;

import java.io.FileReader;
import java.io.Reader;

public class LDeviceUtil {

    /**
     * 获取主板序列号
     */
    public static String getDeviceId() {
        String deviceId = null;
        try {//有线的mac
            deviceId = loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            deviceId = deviceId.trim().replace(":", "").replace("-", "") + "00000000";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * 根据路径获取文件内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    /**
     * 根据流获取内容
     * @param reader
     * @return
     * @throws Exception
     */
    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 获取CPU序列号
     * @return CPU序列号
     */
    public static String getCpuSerial() {
        try {
            return loadFileAsString("/proc/cpuinfo").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取内存信息
     * @return 内存信息
     */
    public static String getMemInfo() {
        try {
            return loadFileAsString("/proc/meminfo").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取系统版本信息
     * @return 系统版本信息
     */
    public static String getSystemVersion() {
        try {
            return loadFileAsString("/proc/version").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取WIFI MAC地址
     * @return WIFI MAC地址
     */
    public static String getWifiMac() {
        try {
            String mac = loadFileAsString("/sys/class/net/wlan0/address");
            if (mac != null) {
                return mac.toUpperCase().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备制造商
     * @return 设备制造商
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * @return 设备型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取设备品牌
     * @return 设备品牌
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
}
