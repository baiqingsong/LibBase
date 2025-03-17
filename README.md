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

#### LDBOperation（完成）
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

#### LDeviceUtil（完成）
设备相关工具类
* getDeviceId 获取设备id

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

#### LLog（完成）
日志工具类
* init 初始化
* i 打印info级别的日志
* e 打印error级别的日志

#### LNetUtil（完成）
网络工具类
* getNetworkType 获取网络类型
* isNetworkAvailable 判断网络是否可用
* isWifi 是否是wifi
* openNetSetting 打开网络设置界面
* setWifiEnabled 设置wifi开关
* ping 检测网络是否连接

#### LSPUtil（完成）
SharedPreferences工具类
* setSP 保存数据
* getSp 获取数据
* cleanAllSP 清除所有数据

#### LStringUtil（完成）
字符串工具类
* parseEmpty 将null转换成空字符串
* isEmpty 判断字符串是否为空
* strLength 获取字符串的长度，中文算两个字符
* isChinese 判断字符串是否是中文
* isContainChinese 判断字符串是否包含中文
* decimalFormat 保留小数点后几位
* toHexString 字节数组转换成16进制字符串
* toByteArray 16进制字符串转换成字节数组
* format 十进制转换成十六进制
* parseHex2Opposite 将16进制字符串转换成相反的字符串
* getXor 获取异或值

#### LSystemUtil（完成）
系统工具类
* reboot 重启设备
* openSettings 打开设置界面
* installApk 安装apk
* showSoftInput 显示软键盘
* hideSoftInput 隐藏软键盘

