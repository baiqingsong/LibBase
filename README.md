# LibBase

Android 实用工具类库，封装了日常开发中常用的 18 个工具类，覆盖日志、网络、文件、加密、设备信息、数据库、UI 等场景，开箱即用。

---

## 目录

- [集成方式](#集成方式)
- [初始化配置](#初始化配置)
- [权限声明](#权限声明)
- [工具类一览](#工具类一览)
- [工具类详细说明](#工具类详细说明)
  - [LAppUtil - 应用工具类](#1-lapputil---应用工具类)
  - [LBitmapUtil - 图片工具类](#2-lbitmaputil---图片工具类)
  - [LCipherUtil - 加密工具类](#3-lcipherutil---加密工具类)
  - [LColorUtil - 颜色工具类](#4-lcolorutil---颜色工具类)
  - [LConvertUtil - 类型转换工具类](#5-lconvertutil---类型转换工具类)
  - [LCrashHandlerUtil - 崩溃处理工具类](#6-lcrashhandlerutil---崩溃处理工具类)
  - [LDateUtil - 日期工具类](#7-ldateutil---日期工具类)
  - [LDBOperationUtil - 数据库操作工具类](#8-ldboperationutil---数据库操作工具类)
  - [LDeviceUtil - 设备工具类](#9-ldeviceutil---设备工具类)
  - [LFileUtil - 文件工具类](#10-lfileutil---文件工具类)
  - [LJsonUtil - JSON工具类](#11-ljsonutil---json工具类)
  - [LLog - 日志工具类](#12-llog---日志工具类)
  - [LNetUtil - 网络工具类](#13-lnetutil---网络工具类)
  - [LSPUtil - SharedPreferences工具类](#14-lsputil---sharedpreferences工具类)
  - [LStringUtil - 字符串工具类](#15-lstringutil---字符串工具类)
  - [LSystemUtil - 系统工具类](#16-lsystemutil---系统工具类)
  - [LToastUtil - Toast工具类](#17-ltoastutil---toast工具类)
  - [LValidateUtil - 验证工具类](#18-lvalidateutil---验证工具类)
- [使用示例](#使用示例)
- [版本更新](#版本更新)
- [贡献](#贡献)

---

## 集成方式

### 1. 本地模块引用

将 `library` 模块拷贝到项目根目录，在 `settings.gradle` 中添加：

```groovy
include ':library'
```

在 app 模块的 `build.gradle` 中添加依赖：

```groovy
dependencies {
    implementation project(':library')
}
```

### 2. 库自身依赖

library 模块已内置以下依赖，引用后无需重复添加：

| 依赖 | 版本 | 说明 |
|------|------|------|
| `androidx.appcompat:appcompat` | 1.6.1 | AndroidX 兼容库 |
| `androidx.core:core` | 1.12.0 | AndroidX Core |
| `com.google.code.gson:gson` | 2.10.1 | JSON 解析（`api` 方式传递） |
| `lite-orm` | 1.9.1 | 轻量级 ORM（本地 JAR） |

> **说明**: Gson 以 `api` 方式引入，主工程可直接使用；LiteOrm 为 `libs/` 目录下的本地 JAR 包。

### 3. 环境要求

| 项目 | 要求 |
|------|------|
| compileSdk | 34 |
| minSdk | 21 (Android 5.0) |
| targetSdk | 34 |
| Java | 1.8+ |

---

## 初始化配置

在 `Application.onCreate()` 中初始化需要的工具类：

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 1. 日志工具（建议最先初始化）
        LLog.init(this, BuildConfig.DEBUG, "MyApp");
        // 或指定日志文件路径
        // LLog.init(this, BuildConfig.DEBUG, "MyApp", "/sdcard/logs/");

        // 2. 崩溃处理
        LCrashHandlerUtil.getInstance()
            .setListener(new LCrashHandlerUtil.OnSetCrashHandlerListener() {
                @Override
                public void onCrash(String crashInfo) {
                    // 可上传到服务器或弹出提示
                }
            })
            .init(this);

        // 3. 数据库（按需初始化）
        LDBOperationUtil.newSingleInstance(this, "my_database", BuildConfig.DEBUG);
    }
}
```

---

## 权限声明

根据实际使用的工具类，在 `AndroidManifest.xml` 中添加对应权限：

```xml
<!-- 网络相关 (LNetUtil) -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.INTERNET" />

<!-- 存储相关 (LLog, LCrashHandlerUtil, LFileUtil, LBitmapUtil) -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<!-- 安装应用 (LAppUtil, LSystemUtil) -->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />

<!-- 电话/短信 (LSystemUtil) -->
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.SEND_SMS" />
```

---

## 工具类一览

| 序号 | 类名 | 说明 | 方法数 |
|:----:|------|------|:------:|
| 1 | `LAppUtil` | 应用版本、安装/卸载、缓存清理 | 12 |
| 2 | `LBitmapUtil` | 图片缩放、旋转、裁剪、编解码 | 16 |
| 3 | `LCipherUtil` | MD5/SHA1/AES/Base64/XOR 加解密 | 10 |
| 4 | `LColorUtil` | 颜色转换、混合、深浅判断 | 17 |
| 5 | `LConvertUtil` | 基础类型转换、金额转换、温度转换 | 13 |
| 6 | `LCrashHandlerUtil` | 全局崩溃捕获与日志记录 | 6 |
| 7 | `LDateUtil` | 日期时间格式化、计算、友好显示 | 25 |
| 8 | `LDBOperationUtil` | LiteOrm 数据库 CRUD 封装 | 23 |
| 9 | `LDeviceUtil` | 设备硬件信息、内存/存储、模拟器检测 | 23 |
| 10 | `LFileUtil` | 文件读写、复制、删除、目录操作 | 16 |
| 11 | `LJsonUtil` | JSON 与对象/集合互转 | 4 |
| 12 | `LLog` | 多级别日志、JSON/XML 格式化、文件写入 | 17 |
| 13 | `LNetUtil` | 网络状态检测、WiFi 管理、Ping 测试 | 14 |
| 14 | `LSPUtil` | SharedPreferences 便捷存取 | 8 |
| 15 | `LStringUtil` | 字符串判断、转换、格式化 | 32 |
| 16 | `LSystemUtil` | 屏幕信息、单位转换、键盘、剪贴板 | 18 |
| 17 | `LToastUtil` | Toast 显示与取消 | 5 |
| 18 | `LValidateUtil` | 手机号/身份证/邮箱等格式验证 | 16 |

---

## 工具类详细说明

### 1. LAppUtil - 应用工具类

> 应用版本信息、安装卸载、缓存管理等。

| 方法签名 | 说明 |
|----------|------|
| `String getVersionName(Context context)` | 获取版本名称 |
| `int getVersionCode(Context context)` | 获取版本号 |
| `long getAppSize(Context context, String packageName)` | 获取应用大小（字节），失败返回 0 |
| `boolean installApk(Context context, String filePath, String authority)` | 安装 APK（支持 FileProvider） |
| `boolean uninstallApk(Context context, String packageName)` | 卸载应用 |
| `boolean runApp(Context context, String packageName)` | 启动指定应用 |
| `boolean isAppInstalled(Context context, String packageName)` | 判断应用是否已安装 |
| `String getAppName(Context context)` | 获取应用名称 |
| `void cleanCache(Context context)` | 清除应用内部缓存 |
| `void cleanDatabases(Context context)` | 清除应用内部数据库 |
| `void cleanSharedPreference(Context context)` | 清除应用 SharedPreferences |
| `boolean isDebug(Context context)` | 判断是否为 Debug 模式 |

---

### 2. LBitmapUtil - 图片工具类

> 图片缩放、旋转、裁剪、编解码、格式转换。

| 方法签名 | 说明 |
|----------|------|
| `Bitmap scaleBitmap(Bitmap bitmap, float ratio)` | 按比例缩放 |
| `Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight)` | 按指定尺寸缩放 |
| `Bitmap rotateBitmap(Bitmap bitmap, float degrees)` | 旋转图片 |
| `Bitmap toCircle(Bitmap bitmap)` | 裁剪为圆形 |
| `Bitmap toRoundCorner(Bitmap bitmap, float radius)` | 裁剪为圆角 |
| `byte[] bitmapToBytes(Bitmap bitmap, CompressFormat format, int quality)` | Bitmap 转 byte 数组 |
| `Bitmap bytesToBitmap(byte[] bytes)` | byte 数组转 Bitmap |
| `String bitmapToBase64(Bitmap bitmap, CompressFormat format, int quality)` | Bitmap 转 Base64 |
| `Bitmap base64ToBitmap(String base64)` | Base64 转 Bitmap |
| `boolean saveBitmap(Bitmap bitmap, File file, CompressFormat format, int quality)` | 保存到文件 |
| `Bitmap decodeSampledBitmap(String filePath, int reqWidth, int reqHeight)` | 从文件采样解码（防 OOM） |
| `Bitmap decodeSampledBitmapFromResource(Context ctx, int resId, int w, int h)` | 从资源采样解码 |
| `Bitmap flipHorizontal(Bitmap bitmap)` | 水平翻转 |
| `Bitmap flipVertical(Bitmap bitmap)` | 垂直翻转 |
| `int[] getImageSize(String filePath)` | 获取图片宽高 `[width, height]` |
| `void recycleBitmap(Bitmap bitmap)` | 安全回收 Bitmap |

---

### 3. LCipherUtil - 加密工具类

> 常用加密/解密/编码算法。

| 方法签名 | 说明 |
|----------|------|
| `String encryptMD5(String input)` | MD5 加密（字符串） |
| `String encryptMD5(InputStream in)` | MD5 加密（输入流） |
| `String base64Encode(String str)` | Base64 编码 |
| `String base64Decode(String str)` | Base64 解码 |
| `String encryptSHA1(String str)` | SHA1 加密（字符串） |
| `String encryptSHA1(File file)` | SHA1 加密（文件） |
| `String xorEncode(String str, String privateKey)` | 异或加密 |
| `String xorDecode(String str, String privateKey)` | 异或解密 |
| `String encryptAES(String message, String passWord)` | AES 加密 |
| `String decryptAES(String message, String passWord)` | AES 解密 |

---

### 4. LColorUtil - 颜色工具类

> 颜色值转换、分量操作、混合变换。

| 方法签名 | 说明 |
|----------|------|
| `String colorToHex(int color)` | 颜色值转十六进制字符串 |
| `int hexToColor(String hex)` | 十六进制字符串转颜色值 |
| `int rgbToColor(int red, int green, int blue)` | RGB 生成颜色值 |
| `int argbToColor(int alpha, int red, int green, int blue)` | ARGB 生成颜色值 |
| `int getRed(int color)` | 获取红色分量 (0-255) |
| `int getGreen(int color)` | 获取绿色分量 (0-255) |
| `int getBlue(int color)` | 获取蓝色分量 (0-255) |
| `int getAlpha(int color)` | 获取透明度分量 (0-255) |
| `int setAlpha(int color, int alpha)` | 设置透明度 |
| `int blendColors(int color1, int color2, float ratio)` | 颜色混合 |
| `int darkenColor(int color, float factor)` | 加深颜色 |
| `int lightenColor(int color, float factor)` | 变亮颜色 |
| `boolean isDarkColor(int color)` | 是否为深色 |
| `boolean isLightColor(int color)` | 是否为浅色 |
| `int getComplementaryColor(int color)` | 获取互补色 |
| `int randomColor()` | 随机颜色 |
| `int randomColor(int alpha)` | 指定透明度的随机颜色 |

---

### 5. LConvertUtil - 类型转换工具类

> 基础类型安全转换、金额/温度换算。

| 方法签名 | 说明 |
|----------|------|
| `int toInt(String str, int defaultValue)` | 字符串转 int（异常返回默认值） |
| `long toLong(String str, long defaultValue)` | 字符串转 long |
| `float toFloat(String str, float defaultValue)` | 字符串转 float |
| `double toDouble(String str, double defaultValue)` | 字符串转 double |
| `boolean toBoolean(String str, boolean defaultValue)` | 字符串转 boolean |
| `byte[] intToBytes(int value)` | int 转 byte 数组 |
| `int bytesToInt(byte[] bytes)` | byte 数组转 int |
| `byte[] longToBytes(long value)` | long 转 byte 数组 |
| `long bytesToLong(byte[] bytes)` | byte 数组转 long |
| `String fenToYuan(long fen)` | 分转元（如 `12345` → `"123.45"`） |
| `long yuanToFen(String yuan)` | 元转分 |
| `double celsiusToFahrenheit(double celsius)` | 摄氏度转华氏度 |
| `double fahrenheitToCelsius(double fahrenheit)` | 华氏度转摄氏度 |

---

### 6. LCrashHandlerUtil - 崩溃处理工具类

> 全局未捕获异常处理，自动采集设备信息并写入日志文件。

| 方法签名 | 说明 |
|----------|------|
| `static LCrashHandlerUtil getInstance()` | 获取单例实例 |
| `void setListener(OnSetCrashHandlerListener listener)` | 设置崩溃回调监听 |
| `LCrashHandlerUtil init(Context context)` | 初始化（注册为默认异常处理器） |
| `void uncaughtException(Thread thread, Throwable ex)` | 异常捕获回调（系统自动调用） |
| `String getFilePath(Context context)` | 获取日志文件存储路径 |
| `String getFileName(Date date)` | 获取日志文件名 |

---

### 7. LDateUtil - 日期工具类

> 日期时间格式化、时间戳转换、日期计算、友好显示。

| 方法签名 | 说明 |
|----------|------|
| `String longToDateTime(long timestamp)` | 时间戳转日期时间字符串 |
| `String longToDate(long timestamp)` | 时间戳转日期字符串 |
| `String longToTime(long timestamp)` | 时间戳转时间字符串 |
| `String getTime()` | 获取当前时间 |
| `String getDate()` | 获取当前日期 |
| `String getDateTime()` | 获取当前日期时间 |
| `String getDateTime(String format)` | 自定义格式获取日期时间 |
| `long dateTimeToLong(String dateTime)` | 日期时间字符串转时间戳 |
| `long dateToLong(String date)` | 日期字符串转时间戳 |
| `long getCurrentTimeMillis()` | 获取当前时间戳（毫秒） |
| `int getDaysBetween(long startDate, long endDate)` | 计算两个日期之间的天数差 |
| `boolean isToday(long timestamp)` | 判断是否为今天 |
| `int getDayOfWeek(long timestamp)` | 获取星期几 (1-7, 1=周一) |
| `String getDayOfWeekName(long timestamp)` | 获取星期几的中文名称 |
| `long addDays(long timestamp, int days)` | 增加天数 |
| `long addHours(long timestamp, int hours)` | 增加小时 |
| `long addMinutes(long timestamp, int minutes)` | 增加分钟 |
| `String formatDuration(long millis)` | 格式化时长（如 "2天3小时5分钟"） |
| `boolean isLeapYear(int year)` | 判断是否为闰年 |
| `int getAge(long birthdayTimestamp)` | 根据生日计算年龄 |
| `int getDaysInMonth(int year, int month)` | 获取指定月份天数 |
| `String getFriendlyTime(long timestamp)` | 友好时间描述（如 "3分钟前"） |
| `long getStartOfDay()` | 获取今天 00:00 时间戳 |
| `long getStartOfDay(long timestamp)` | 获取指定日期 00:00 时间戳 |
| `long getEndOfDay(long timestamp)` | 获取指定日期 23:59:59 时间戳 |

---

### 8. LDBOperationUtil - 数据库操作工具类

> 基于 LiteOrm 的数据库 CRUD 操作封装。

| 方法签名 | 说明 |
|----------|------|
| `void newSingleInstance(Context ctx, String liteName, boolean debug)` | 初始化数据库 |
| `static LiteOrm getLiteOrm()` | 获取 LiteOrm 实例 |
| `void insert(T t)` | 插入单条数据 |
| `void insertAll(List<T> list)` | 批量插入 |
| `List<T> queryAll(Class<T> cla)` | 查询全部 |
| `List<T> queryByWhere(Class<T> cla, String field, String value)` | 单条件查询 |
| `List<T> queryByWhere(Class<T> cla, String field, String[] value)` | 多值条件查询 |
| `List<T> queryByWhereTwo(Class<T> cla, ...)` | 双条件查询 |
| `List<T> queryByWhereLength(Class<T> cla, String field, String[] value, int start, int length)` | 分页 + 多值查询 |
| `List<T> queryByWhereLength(Class<T> cla, String field, String value, int start, int length)` | 分页 + 单值查询 |
| `List<T> queryByWhereOrder(Class<T> cla, String where, String[] values, String order)` | 条件查询 + 排序 |
| `List<T> queryByWhereLike(Class<T> cla, String field, String keyword)` | 模糊查询 |
| `void deleteWhere(Class<T> cla, String field, String[] value)` | 多值条件删除 |
| `void deleteWhere(Class<T> cla, String field, String value)` | 单值条件删除 |
| `void delete(T t)` | 删除单条数据 |
| `void deleteAll(Class<T> cla)` | 清空表数据 |
| `void deleteDatabase()` | 删除数据库文件 |
| `void reCreateDatabase()` | 重建数据库 |
| `void deleteSection(Class<T> cla, long arg1, long arg2, String arg3)` | 删除指定区间数据 |
| `void deleteList(List<T> list)` | 批量删除 |
| `void update(T t)` | 更新单条数据 |
| `void updateAll(List<T> list)` | 批量更新 |
| `long queryCount(Class<T> cla)` | 查询表中数据条数 |

---

### 9. LDeviceUtil - 设备工具类

> 设备硬件信息、系统参数、内存/存储状态。

| 方法签名 | 说明 |
|----------|------|
| `String getDeviceId()` | 获取主板序列号 |
| `String loadFileAsString(String fileName)` | 读取系统文件内容 |
| `String getCpuSerial()` | 获取 CPU 序列号 |
| `String getMemInfo()` | 获取内存信息 |
| `String getSystemVersion()` | 获取系统版本信息 |
| `String getWifiMac()` | 获取 WiFi MAC 地址 |
| `String getManufacturer()` | 获取设备制造商 |
| `String getModel()` | 获取设备型号 |
| `String getBrand()` | 获取设备品牌 |
| `String getAndroidVersion()` | 获取 Android 版本号（如 "13"） |
| `int getSDKVersion()` | 获取 SDK 版本号 |
| `String getFingerprint()` | 获取设备指纹 |
| `String getHardware()` | 获取硬件名称 |
| `String getDisplay()` | 获取显示名称 |
| `String getLanguage()` | 获取系统语言 |
| `String getCountry()` | 获取国家/地区代码 |
| `long getAvailableMemory(Context context)` | 获取可用内存大小 |
| `long getTotalMemory(Context context)` | 获取总内存大小 |
| `boolean isLowMemory(Context context)` | 判断是否低内存 |
| `long getInternalStorageAvailable()` | 获取可用存储空间 |
| `long getInternalStorageTotal()` | 获取总存储空间 |
| `String getDeviceInfo()` | 获取完整设备信息字符串 |
| `boolean isEmulator()` | 判断是否为模拟器 |

---

### 10. LFileUtil - 文件工具类

> 文件读写、复制、删除、目录操作。

| 方法签名 | 说明 |
|----------|------|
| `void closeIO(Closeable... closeables)` | 安全关闭 IO 流 |
| `boolean deleteFile(String filename)` | 删除文件 |
| `boolean deleteFileByDirectory(File directory)` | 删除目录下的文件 |
| `boolean clearDirectory(String folder)` | 清空目录 |
| `boolean isFileExist(String filePath)` | 判断文件是否存在 |
| `boolean writeFile(String content, String filename, boolean append)` | 写入文件 |
| `String readFile(String filename)` | 读取文件内容 |
| `void copyFile(File inFile, File outFile)` | 复制文件 |
| `void streamToFile(InputStream is, File file)` | 输入流写入文件 |
| `boolean createFolder(String filePath, boolean recreate)` | 创建文件夹 |
| `String getFileName(String filePath)` | 获取文件名 |
| `String getFolderName(String filePath)` | 获取文件夹名称 |
| `long getFileSize(String filepath)` | 获取文件大小 |
| `boolean renameFile(String filepath, String newName)` | 重命名文件 |
| `ArrayList<File> getFilesArray(String path)` | 获取目录下文件列表 |
| `byte[] fileToByteAry(File file)` | 文件转字节数组 |

---

### 11. LJsonUtil - JSON工具类

> 基于 Gson 的 JSON 序列化/反序列化。

| 方法签名 | 说明 |
|----------|------|
| `String objToJson(Object obj)` | 对象转 JSON 字符串 |
| `<T> T jsonToObj(String str, Class<T> type)` | JSON 字符串转对象 |
| `<T> String listToJson(List<T> list)` | 集合转 JSON 字符串 |
| `<T> List<T> jsonToList(String json, Class<T> clazz)` | JSON 字符串转集合 |

---

### 12. LLog - 日志工具类

> 多级别日志、JSON/XML 格式化输出、日志文件写入。

| 方法签名 | 说明 |
|----------|------|
| `void init(Context context, boolean isDebug, String tag)` | 初始化日志（指定开关和 TAG） |
| `void init(Context context, boolean isDebug, String tag, String logPath)` | 初始化日志（指定路径） |
| `void v(String msg)` | VERBOSE 日志 |
| `void v(String tag, String msg)` | 带 TAG 的 VERBOSE 日志 |
| `void d(String msg)` | DEBUG 日志 |
| `void d(String tag, String msg)` | 带 TAG 的 DEBUG 日志 |
| `void i(Object... msg)` | INFO 日志（支持多参数） |
| `void w(String msg)` | WARN 日志 |
| `void w(String tag, String msg)` | 带 TAG 的 WARN 日志 |
| `void e(String msg)` | ERROR 日志 |
| `void e(String tag, String msg)` | 带 TAG 的 ERROR 日志 |
| `void e(String msg, Throwable tr)` | ERROR 日志（带异常） |
| `void e(String tag, String msg, Throwable tr)` | 带 TAG 的 ERROR 日志（带异常） |
| `void json(String json)` | 格式化输出 JSON |
| `void json(String tag, String json)` | 带 TAG 格式化输出 JSON |
| `void xml(String xml)` | 格式化输出 XML |
| `void xml(String tag, String xml)` | 带 TAG 格式化输出 XML |

---

### 13. LNetUtil - 网络工具类

> 网络状态检测、WiFi 管理、连通性测试。

| 方法签名 | 说明 |
|----------|------|
| `int getNetworkType(Context context)` | 获取网络类型 |
| `boolean isNetworkAvailable(Context context)` | 判断网络是否可用 |
| `boolean isWiFi(Context context)` | 判断是否为 WiFi 连接 |
| `void openNetSetting(Activity activity)` | 打开网络设置界面 |
| `void setWifiEnabled(Context context, boolean enabled)` | 设置 WiFi 开关状态 |
| `boolean ping(String host, int pingCount, StringBuffer buffer)` | Ping 测试（详细） |
| `String getNetworkTypeName(Context context)` | 获取网络类型名称 |
| `boolean isMobileNetwork(Context context)` | 判断是否为移动网络 |
| `int getWifiSignalStrength(Context context)` | 获取 WiFi 信号强度 |
| `String getCurrentWifiName(Context context)` | 获取当前 WiFi 名称 |
| `String getLocalIPAddress(Context context)` | 获取本机 IP 地址 |
| `boolean simplePing(String host)` | 简单 Ping 测试 |
| `boolean isPortReachable(String host, int port, int timeout)` | 检查端口连通性 |
| `String getMacAddress()` | 获取 MAC 地址 |

---

### 14. LSPUtil - SharedPreferences工具类

> 简化 SharedPreferences 数据存取，支持自定义 SP 文件名。

| 方法签名 | 说明 |
|----------|------|
| `void setSP(Context context, String key, Object object)` | 存储值（默认 SP 文件） |
| `Object getSP(Context context, String key, Object defaultObject)` | 读取值（默认 SP 文件） |
| `void cleanAllSP(Context context)` | 清除所有 SP 数据 |
| `void removeSP(Context context, String key)` | 移除指定 key |
| `boolean containsKey(Context context, String key)` | 判断是否包含指定 key |
| `Set<String> getAllKeys(Context context)` | 获取所有 key |
| `void setSP(Context context, String spName, String key, Object object)` | 存储值（指定 SP 文件名） |
| `Object getSP(Context context, String spName, String key, Object defaultObject)` | 读取值（指定 SP 文件名） |

---

### 15. LStringUtil - 字符串工具类

> 字符串判断、转换、格式化、编解码。

| 方法签名 | 说明 |
|----------|------|
| `String parseEmpty(String str)` | 空字符串处理（null → ""） |
| `boolean isEmpty(String str)` | 判断是否为空字符串 |
| `int strLength(String str)` | 获取字符串长度 |
| `boolean isChinese(String str)` | 判断是否全是中文 |
| `boolean isContainChinese(String str)` | 判断是否包含中文 |
| `String decimalFormat(double s, String format)` | 数字格式化 |
| `String toHexString(byte[] byteArray)` | 字节数组转十六进制字符串 |
| `byte[] toByteArray(String hexString)` | 十六进制字符串转字节数组 |
| `String format(int hex)` | int 转十六进制字符串 |
| `String parseHex2Opposite(String str)` | 十六进制取反 |
| `String getXor(String str)` | 异或结果 |
| `String capitalizeFirst(String str)` | 首字母大写 |
| `String lowercaseFirst(String str)` | 首字母小写 |
| `String camelToUnderline(String str)` | 驼峰转下划线 |
| `String underlineToCamel(String str)` | 下划线转驼峰 |
| `boolean isNumeric(String str)` | 判断是否为数字 |
| `boolean isInteger(String str)` | 判断是否为整数 |
| `String removeSpaces(String str)` | 移除所有空格 |
| `String reverse(String str)` | 反转字符串 |
| `boolean equals(String s1, String s2)` | 判断是否相等（null 安全） |
| `boolean equalsIgnoreCase(String s1, String s2)` | 忽略大小写判断是否相等 |
| `String defaultIfEmpty(String str, String defaultValue)` | 为空时返回默认值 |
| `String join(String separator, String... parts)` | 字符串拼接（数组） |
| `String join(String separator, List<String> list)` | 字符串拼接（集合） |
| `String repeat(String str, int count)` | 重复字符串 |
| `String abbreviate(String str, int maxLength)` | 截断并添加省略号 |
| `boolean containsIgnoreCase(String str, String search)` | 忽略大小写包含判断 |
| `String safeSubstring(String str, int start, int end)` | 安全截取子串 |
| `String safeTrim(String str)` | 安全 trim |
| `int countMatches(String str, String sub)` | 统计子串出现次数 |
| `String padLeft(String str, int size, char padChar)` | 左填充 |
| `String padRight(String str, int size, char padChar)` | 右填充 |

---

### 16. LSystemUtil - 系统工具类

> 屏幕信息、单位转换、键盘控制、剪贴板、短信拨号。

| 方法签名 | 说明 |
|----------|------|
| `void reboot()` | 重启手机 |
| `void openSettings(Context context)` | 打开系统设置 |
| `void installApk(Context context, File apkfile, String authority)` | 安装 APK |
| `void showSoftInput(Context context)` | 显示软键盘 |
| `void hideSoftInput(Context context)` | 隐藏软键盘 |
| `int getScreenWidth(Context context)` | 获取屏幕宽度（px） |
| `int getScreenHeight(Context context)` | 获取屏幕高度（px） |
| `float getScreenDensity(Context context)` | 获取屏幕密度 |
| `int dp2px(Context context, float dpValue)` | dp 转 px |
| `int px2dp(Context context, float pxValue)` | px 转 dp |
| `int sp2px(Context context, float spValue)` | sp 转 px |
| `int px2sp(Context context, float pxValue)` | px 转 sp |
| `int getStatusBarHeight(Context context)` | 获取状态栏高度 |
| `int getNavigationBarHeight(Context context)` | 获取导航栏高度 |
| `void copyToClipboard(Context context, String text)` | 复制文本到剪贴板 |
| `String getFromClipboard(Context context)` | 从剪贴板获取文本 |
| `void openDialer(Context context, String phoneNumber)` | 打开拨号界面 |
| `void sendSMS(Context context, String phoneNumber, String message)` | 发送短信 |

---

### 17. LToastUtil - Toast工具类

> 全局 Toast 管理，避免重复弹出。

| 方法签名 | 说明 |
|----------|------|
| `void showShort(Context context, String message)` | 短时间显示 Toast |
| `void showShort(Context context, int resId)` | 短时间显示 Toast（资源 ID） |
| `void showLong(Context context, String message)` | 长时间显示 Toast |
| `void showLong(Context context, int resId)` | 长时间显示 Toast（资源 ID） |
| `void cancel()` | 取消当前 Toast |

---

### 18. LValidateUtil - 验证工具类

> 常用数据格式验证和脱敏格式化。

| 方法签名 | 说明 |
|----------|------|
| `boolean isValidEmail(String email)` | 验证邮箱 |
| `boolean isValidPhone(String phone)` | 验证手机号 |
| `boolean isValidIdCard(String idCard)` | 验证身份证号（含校验位） |
| `boolean isValidUrl(String url)` | 验证 URL |
| `boolean isValidIP(String ip)` | 验证 IP 地址 |
| `boolean isStrongPassword(String password)` | 验证强密码 |
| `boolean isValidBankCard(String bankCard)` | 验证银行卡号（Luhn 算法） |
| `boolean isValidPlateNumber(String plateNumber)` | 验证车牌号 |
| `boolean isValidQQ(String qq)` | 验证 QQ 号 |
| `boolean isValidWechat(String wechat)` | 验证微信号 |
| `boolean isValidZipCode(String zipCode)` | 验证邮政编码 |
| `String formatPhone(String phone)` | 手机号脱敏（138\*\*\*\*1234） |
| `String formatIdCard(String idCard)` | 身份证脱敏 |
| `String formatBankCard(String bankCard)` | 银行卡号脱敏 |
| `String formatEmail(String email)` | 邮箱脱敏 |

---

## 使用示例

### 日志输出

```java
// 初始化
LLog.init(context, true, "Demo");

// 基本使用
LLog.d("调试信息");
LLog.e("TAG", "错误信息", exception);

// JSON 格式化输出
LLog.json("{\"name\":\"张三\",\"age\":25}");
```

### JSON 序列化

```java
// 对象转 JSON
User user = new User("张三", 25);
String json = LJsonUtil.objToJson(user);

// JSON 转对象
User u = LJsonUtil.jsonToObj(json, User.class);

// 集合转 JSON
List<User> list = Arrays.asList(user);
String listJson = LJsonUtil.listToJson(list);

// JSON 转集合
List<User> users = LJsonUtil.jsonToList(listJson, User.class);
```

### 数据库操作

```java
// 初始化
LDBOperationUtil.newSingleInstance(context, "app_db", BuildConfig.DEBUG);

// 插入
LDBOperationUtil.insert(new User("张三", 25));

// 查询全部
List<User> all = LDBOperationUtil.queryAll(User.class);

// 条件查询
List<User> result = LDBOperationUtil.queryByWhere(User.class, "name", "张三");

// 模糊查询
List<User> search = LDBOperationUtil.queryByWhereLike(User.class, "name", "张");

// 分页查询
List<User> page = LDBOperationUtil.queryByWhereLength(User.class, "age", "25", 0, 10);
```

### 文件操作

```java
// 写入文件
LFileUtil.writeFile("Hello World", "/sdcard/test.txt", false);

// 读取文件
String content = LFileUtil.readFile("/sdcard/test.txt");

// 复制文件
LFileUtil.copyFile(new File(src), new File(dst));

// 获取文件大小
long size = LFileUtil.getFileSize("/sdcard/test.txt");
```

### 加密/解密

```java
// MD5
String md5 = LCipherUtil.encryptMD5("Hello");

// AES 加密解密
String encrypted = LCipherUtil.encryptAES("明文", "密码");
String decrypted = LCipherUtil.decryptAES(encrypted, "密码");

// Base64
String encoded = LCipherUtil.base64Encode("Hello");
String decoded = LCipherUtil.base64Decode(encoded);
```

### 网络检测

```java
// 判断网络是否可用
boolean available = LNetUtil.isNetworkAvailable(context);

// 判断是否 WiFi
boolean wifi = LNetUtil.isWiFi(context);

// 获取网络类型名称
String typeName = LNetUtil.getNetworkTypeName(context);

// Ping 测试
boolean reachable = LNetUtil.simplePing("www.baidu.com");
```

### 数据验证

```java
// 验证手机号
boolean valid = LValidateUtil.isValidPhone("13800138000");

// 验证身份证
boolean idOk = LValidateUtil.isValidIdCard("110101199001011234");

// 手机号脱敏
String masked = LValidateUtil.formatPhone("13800138000"); // 138****8000
```

### 日期处理

```java
// 获取当前日期时间
String now = LDateUtil.getDateTime();

// 时间戳转字符串
String dateStr = LDateUtil.longToDateTime(System.currentTimeMillis());

// 计算天数差
int days = LDateUtil.getDaysBetween(start, end);

// 友好时间显示
String friendly = LDateUtil.getFriendlyTime(timestamp); // "3分钟前"

// 计算年龄
int age = LDateUtil.getAge(birthdayTimestamp);
```

### 图片处理

```java
// 缩放
Bitmap scaled = LBitmapUtil.scaleBitmap(bitmap, 0.5f);

// 裁剪为圆形
Bitmap circle = LBitmapUtil.toCircle(bitmap);

// 从文件采样加载（防 OOM）
Bitmap sampled = LBitmapUtil.decodeSampledBitmap("/sdcard/photo.jpg", 200, 200);

// 保存到文件
LBitmapUtil.saveBitmap(bitmap, file, Bitmap.CompressFormat.JPEG, 90);

// 安全回收
LBitmapUtil.recycleBitmap(bitmap);
```

### Toast 提示

```java
LToastUtil.showShort(context, "操作成功");
LToastUtil.showLong(context, "这是一条长提示");
LToastUtil.cancel(); // 取消当前 Toast
```

### SharedPreferences

```java
// 存储
LSPUtil.setSP(context, "username", "张三");
LSPUtil.setSP(context, "age", 25);

// 读取
String name = (String) LSPUtil.getSP(context, "username", "");
int age = (int) LSPUtil.getSP(context, "age", 0);

// 使用自定义 SP 文件
LSPUtil.setSP(context, "config", "theme", "dark");
```

### 类型转换

```java
// 安全转换（异常返回默认值）
int num = LConvertUtil.toInt("123", 0);
double d = LConvertUtil.toDouble("3.14", 0.0);

// 金额转换
String yuan = LConvertUtil.fenToYuan(12345); // "123.45"
long fen = LConvertUtil.yuanToFen("123.45"); // 12345
```

---

## 版本更新

| 版本 | 更新内容 |
|------|----------|
| V1.2 | 新增 LBitmapUtil、LConvertUtil、LToastUtil；所有工具类方法补充完善；安全性与稳定性全面优化 |
| V1.1 | 新增 LValidateUtil、LColorUtil；优化现有工具类，增加常用方法 |
| V1.0 | 基础工具类库 |

---

## 贡献

欢迎提交 Issue 和 Pull Request 来完善这个工具库。
