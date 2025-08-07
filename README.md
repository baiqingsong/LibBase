# LibBase工具类库说明文档

## 概述
LibBase是一个Android实用工具类库，包含了常用的工具类和方法，旨在提高Android开发效率。

## 工具类列表

### 1. LJsonUtil - JSON工具类
**依赖**: Gson库
**功能**: JSON与对象之间的转换
- `objToJson()` - 对象转JSON字符串
- `jsonToObj()` - JSON字符串转对象
- `listToJson()` - 集合转JSON字符串
- `jsonToList()` - JSON字符串转集合

### 2. LFileUtil - 文件工具类
**功能**: 文件和文件夹操作
- 文件删除、创建、重命名、复制
- 文件读写操作
- 文件大小获取和计算
- 文件格式判断
- 字节数组与文件互转
- **新增方法**:
    - `getFileExtension()` - 获取文件扩展名
    - `getFileNameWithoutExtension()` - 获取不含扩展名的文件名
    - `createFile()` - 创建文件
    - `readFileAll()` - 读取文件所有内容
    - `byteAryToFile()` - 字节数组写入文件
    - `getFolderSize()` - 计算文件夹大小

### 3. LDeviceUtil - 设备工具类
**功能**: 获取设备相关信息
- `getDeviceId()` - 获取设备ID（基于网卡地址）
- `loadFileAsString()` - 读取系统文件内容
- **新增方法**:
    - `getCpuSerial()` - 获取CPU序列号
    - `getMemInfo()` - 获取内存信息
    - `getSystemVersion()` - 获取系统版本信息
    - `getWifiMac()` - 获取WIFI MAC地址
    - `getManufacturer()` - 获取设备制造商
    - `getModel()` - 获取设备型号
    - `getBrand()` - 获取设备品牌

### 4. LDBOperationUtil - 数据库操作工具类
**依赖**: LiteOrm库
**功能**: 数据库CRUD操作的封装
- 数据库初始化和配置
- 增删改查操作
- 分页查询、条件查询
- 数据库管理和维护

### 5. LDateUtil - 日期工具类
**功能**: 日期时间处理
- 时间戳与日期字符串互转
- 获取当前时间/日期
- 自定义格式日期转换
- **新增方法**:
    - `dateTimeToLong()` - 日期字符串转时间戳
    - `dateToLong()` - 日期字符串转时间戳
    - `getCurrentTimeMillis()` - 获取当前时间戳
    - `getDaysBetween()` - 获取两个日期之间的天数差
    - `isToday()` - 判断是否为今天
    - `getDayOfWeek()` - 获取星期几

### 6. LCrashHandlerUtil - 崩溃处理工具类
**功能**: 应用崩溃信息收集和处理
- 自动捕获未处理异常
- 异常信息写入本地文件
- 设备信息收集
- 异常日志文件管理
- 支持自定义异常处理回调

### 7. LCipherUtil - 加密工具类
**功能**: 各种加密解密算法
- MD5、SHA1、SHA256、SHA512加密
- Base64编码解码
- AES对称加密解密
- 异或加密解密
- **新增方法**:
    - `crc32()` - CRC32校验
    - `encryptSHA256()` - SHA256加密
    - `encryptSHA512()` - SHA512加密
    - `generateRandomString()` - 生成随机字符串
    - `urlEncode()` / `urlDecode()` - URL编码解码

### 8. LAppUtil - 应用工具类
**功能**: 应用管理相关操作
- 获取应用版本信息
- 应用安装卸载（已标记为过时）
- 应用启动
- 缓存清理（已标记为过时）

### 9. LLog - 日志工具类
**功能**: 日志输出和文件保存
- 多级别日志输出（V/D/I/W/E）
- 日志写入本地文件
- JSON/XML格式化输出
- 日志文件大小管理
- 异常信息记录

### 10. LSystemUtil - 系统工具类
**功能**: 系统级操作和信息获取
- 软键盘显示/隐藏
- 系统设置页面打开
- APK安装
- **新增方法**:
    - `getScreenWidth/Height()` - 获取屏幕尺寸
    - `getScreenDensity()` - 获取屏幕密度
    - `dp2px/px2dp()` - 像素单位转换
    - `sp2px/px2sp()` - 字体单位转换
    - `getStatusBarHeight()` - 获取状态栏高度
    - `getNavigationBarHeight()` - 获取导航栏高度
    - `copyToClipboard()` - 复制到剪贴板
    - `getFromClipboard()` - 从剪贴板获取
    - `openDialer()` - 打开拨号界面
    - `sendSMS()` - 发送短信

### 11. LStringUtil - 字符串工具类
**功能**: 字符串处理和格式化
- 空字符串判断和处理
- 中文字符判断
- 数字格式化
- 十六进制转换
- **新增方法**:
    - `capitalizeFirst()` - 首字母大写
    - `lowercaseFirst()` - 首字母小写
    - `camelToUnderline()` - 驼峰转下划线
    - `underlineToCamel()` - 下划线转驼峰
    - `isNumeric()` - 判断是否为数字
    - `isInteger()` - 判断是否为整数
    - `removeSpaces()` - 移除空格
    - `reverse()` - 反转字符串

### 12. LSPUtil - SharedPreferences工具类
**功能**: 简化SP数据存储操作
- 支持多种数据类型的存储/读取
- 自动类型识别
- **新增方法**:
    - `removeSP()` - 清除指定key
    - `containsKey()` - 检查是否包含指定key
    - `getAllKeys()` - 获取所有key
    - 支持自定义SP文件名的方法重载

### 13. LNetUtil - 网络工具类
**功能**: 网络状态检测和管理
- 网络类型检测
- 网络可用性判断
- WiFi状态管理
- 网络连通性测试
- **新增方法**:
    - `getNetworkTypeName()` - 获取网络类型名称
    - `isMobileNetwork()` - 是否是移动网络
    - `getWifiSignalStrength()` - 获取WiFi信号强度
    - `getCurrentWifiName()` - 获取当前WiFi名称
    - `getLocalIPAddress()` - 获取本机IP地址
    - `simplePing()` - 简单ping测试
    - `isPortReachable()` - 检查端口连通性

### 14. LValidateUtil - 验证工具类 ⭐新增
**功能**: 常用数据格式验证和格式化
- 邮箱、手机号、身份证号验证
- URL、IP地址验证
- 密码强度验证
- 银行卡号、车牌号验证
- QQ号、微信号验证
- 敏感信息格式化显示

### 15. LColorUtil - 颜色工具类 ⭐新增
**功能**: 颜色处理和转换
- 颜色值与十六进制字符串互转
- RGB/ARGB颜色值生成
- 颜色分量提取
- 颜色混合和变换
- 深浅色判断
- 随机颜色生成

## 使用建议

### 1. 初始化
在Application中初始化必要的工具类：
```java
// 日志工具初始化
LLog.init(context, BuildConfig.DEBUG, "YourTag");

// 崩溃处理初始化
LCrashHandlerUtil.getInstance().init(context);

// 数据库初始化（如果使用）
LDBOperationUtil.newSingleInstance(context, "your_db_name", BuildConfig.DEBUG);
```

### 2. 权限要求
某些工具类需要相应的Android权限：
- LNetUtil: 需要网络权限
- LSystemUtil: 部分方法需要系统权限
- LCrashHandlerUtil: 需要存储权限
- LLog: 需要存储权限

### 3. 依赖管理
- LJsonUtil 需要添加 Gson 依赖
- LDBOperationUtil 需要添加 LiteOrm 依赖

### 4. 注意事项
- 标记为 @Deprecated 的方法建议使用新的API替代
- 部分方法在高版本Android系统中可能需要适配
- 文件操作相关方法注意权限检查

## 版本更新
- **V1.0**: 基础工具类
- **V1.1**: 新增 LValidateUtil 和 LColorUtil，优化现有工具类，增加常用方法

## 贡献
欢迎提交Issue和Pull Request来完善这个工具库。
