# LibBase
 基础工具类调用

## 工具类

#### LAppUtil（完成）
应用相关工具类
* getVersionName 获取版本名称
* getVersionCode 获取版本号
* getAppSize 获取应用大小
* installApk 安装apk
* uninstallApk 卸载apk
* runApp 运行应用
* cleanCache 清除缓存
* cleanDatabases 清除数据库
* cleanSharedPreference 清除SharedPreferences

#### LCipherUtil（完成）
密码工具类
* encryptMD5 MD5加密
* base64Encode Base64加密
* base64Decode Base64解密
* encryptSHA1 SHA1加密
* XorEncode 异或加密
* XorDecode 异或解密
* encryptAES AES加密
* decryptAES AES解密

#### LCrashHandlerUtil
崩溃日志工具类
* init 初始化
* setListener 设置监听

#### LDateUtil（完成）
日期工具类
* longToDateTime 将时间戳转换成日期时间
* longToDate 将时间戳转换成日期
* longToTime 将时间戳转换成时间
* getTime 获取当前时间
* getDate 获取当前日期
* getDateTime 获取当前日期时间

#### LDBOperation
数据库工具类，需要lite-orm
* newSingleInstance 数据库的初始化，需要在application中调用
* getLiteOrm 获取liteOrm实体类
* insert 插入，传入实体类
* insertAll 插入，传入实体类的集合
* queryAll 查询所有，返回实体类集合
* queryByWhere 按照条件查询，返回实体类集合
* queryByWhere 多条件查询，返回实体类集合
* queryByWhereTwo 两个条件查询，返回实体类集合
* queryByWhereLength 分页查询，多个条件查询，返回实体类集合
* queryByWhereLength 分页查询，单个条件查询，返回实体类集合
* queryByWhereOrder 按照条件查询，并且排序
* deleteWhere 根据条件删除，多个条件
* deleteWhere 根据条件删除，单个条件
* delete 删除实体类
* deleteAll 删除所有的类
* deleteDatabase 删除数据库，包括文件
* reCreateDatabase 重建数据库
* deleteSection 删除某个区间的数据
* deleteList 删除实体类列表
* update 更新实体类
* updateALL 更新实体类集合
* queryCount 查询实体类数量

#### LFileUtil（完成）
文件工具类
* closeIO 关闭IO流
* deleteFile 删除文件
* deleteFileByDirectory 删除文件夹下的所有文件
* clearDirectory 清空文件夹
* isFileExist 判断文件是否存在
* writeFile 将字符串写入到文件中
* readFile 读取文件中的字符串
* copyFile 复制文件
* streamToFile 将输入流写入文件
* createFolder 创建文件夹,支持覆盖已存在的同名文件夹
* getFileName 获取文件名
* getFolderName 获取文件夹名称
* getFileSize 获取文件大小
* renameFile 重命名文件
* getFilesArray 获取文件夹下的所有文件
* fileToByteAry 文件转换成byte数组

#### LJsonUtil（完成）
json工具类
* objToJson 对象转json
* jsonToObj json转对象
* listToJson 集合转json
* jsonToList json转集合

#### LLogUtil
日志工具类
* init 初始化
* i 打印info级别的日志
* e 打印error级别的日志

#### LNetUtil
网络工具类
* getNetworkType 获取网络类型
* getNetworkTypeName 获取网络类型名称
* isConnected 判断网络是否连接
* isNetworkAvailable 判断网络是否可用
* isWiFi 判断是否是wifi连接
* isWifiConnected 判断wifi是否连接
* openNetSetting 打开网络设置界面
* setWifiEnabled 设置wifi是否可用
* getWifiScanResults 获取wifi列表
* getScanResultsByBSSID 过滤扫描结果
* getWifiConnectionInfo 获取wifi连接信息
* ping 检测网络是否可用
* changeWifi 切换WiFi
* getWifiState 获取WiFi状态

#### LSPUtil
SharedPreferences工具类
* setSP 保存数据
* getSp 获取数据
* cleanAllSP 清除所有数据

#### LStringUtil
字符串工具类
* getChsAscii 汉字转成ASCII码
* convert 单字解析
* getSelling 词组解析
* parseEmpty 将null转化为""
* isEmpty 是否是空字符串
* chineseLength 中文长度
* strLength 字符串长度
* subStringLength 获取指定长度的字符所在位置
* isChinese 是否是中文
* isContainChinese 是否包含中文
* strFormat2 不足2位前面补0
* convert2Int 类型安全转换
* decimalFormat 指定小数输出
* str2HexStr 字符串转换成十六进制字符串
* hexStr2Str 十六进制转换字符串
* byte2HexStr bytes转换成十六进制字符串
* hexStr2Bytes bytes字符串转换为Byte值
* strToUnicode String的字符串转换成unicode的String
* unicodeToString unicode的String转换成String的字符串
* toHexString 字节数组转换成字符串
* toByteArray 字符串转换成字节数组

#### LSystemUtil
系统工具类
* reboot 重启设备
* openSettings 打开设置界面
* hideKeyBoard 隐藏键盘
* installApk 安装apk

