package com.dawn.library;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * 文件工具类
 */
@SuppressWarnings("unused")
public class LFileUtil {
    /**
     * 关闭io流
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     * @param filename 文件名称
     *
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }

    /**
     * 删除文件夹中的所有文件
     * @param directory 文件夹
     *
     * @return 是否删除成功
     */
    public static boolean deleteFileByDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            boolean isSuccess = true;
            for (File file : directory.listFiles()) {
                boolean singleSuccess = file.delete();
                if (!singleSuccess) {
                    isSuccess = false;
                }
            }
            return isSuccess;
        }
        return true;
    }

    /**
     * 删除文件夹下的所有文件
     * @param folder 文件路径
     *
     * @return 是否删除成功
     */
    public static boolean clearDirectory(String folder) {
        if (folder == null || folder.isEmpty()) {
            return true;
        }
        File file = new File(folder);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 文件是否存在
     * @param filePath 文件路径
     *
     * @return 是否存在
     */
    public static boolean isFileExist(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 将字符串写入到文件中
     * @param content 上下文
     * @param filename 文件名称
     * @param append 是否在文件后继续写
     *
     * @return 是否写入成功
     */
    public static boolean writeFile(String content, String filename, boolean append) {
        boolean isSuccess = false;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filename, append));
            bufferedWriter.write(content);
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedWriter);
        }
        return isSuccess;
    }

    /**
     * 从文件中读取字符串
     * @param filename 文件名称
     *
     * @return 读取的字符串
     */
    public static String readFile(String filename) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        String str = null;
        try {
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(filename));
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(bufferedReader);
        }
        return str;
    }

    /**
     * 文件复制
     * @param inFile 输入文件
     * @param outFile 输出文件
     */
    public static void copyFile(File inFile, File outFile) {
        FileChannel filein = null;
        FileChannel fileout = null;
        try {
            filein = new FileInputStream(inFile).getChannel();
            fileout = new FileOutputStream(outFile).getChannel();
            filein.transferTo(0, filein.size(), fileout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(filein, fileout);
        }
    }

    /**
     * 将输入流写入到文件
     * @param is 输入流
     * @param file 文件
     */
    public static void streamToFile(InputStream is, File file) {
        byte[] b = new byte[1024];
        int len;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is, os);
        }
    }

    /**
     * 创建文件夹（支持覆盖已存在的同名文件夹）
     * @param filePath 文件夹目录
     * @param recreate
     *
     * @return 是否创建成功
     */
    @SuppressWarnings("WeakerAccess")
    public static boolean createFolder(String filePath, boolean recreate) {
        String folderName = getFolderName(filePath);
        if (folderName == null || folderName.isEmpty()) {
            return false;
        }
        File folder = new File(folderName);
        if (folder.exists()) {
            if (recreate) {
                deleteFile(folderName);
                return folder.mkdirs();
            } else {
                return true;
            }
        } else {
            return folder.mkdirs();
        }
    }

    /**
     * 从文件路径中获取文件名
     * @param filePath 文件目录
     *
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        if (LStringUtil.isEmpty(filePath))
            return filePath;
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? filePath : filePath.substring(filePos + 1);
    }

    /**
     * 获取文件夹名称
     * @param filePath 文件名称
     */
    @SuppressWarnings("WeakerAccess")
    public static String getFolderName(String filePath) {
        if (filePath == null || filePath.isEmpty())
            return filePath;
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(0, filePos);
    }

    /**
     * 获取文件大小
     * @param filepath 文件名
     *
     * @return 文件大小
     */
    public static long getFileSize(String filepath) {
        if (TextUtils.isEmpty(filepath))
            return -1;
        File file = new File(filepath);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 重命名文件|文件夹
     * @param filepath 原来文件路径
     * @param newName 新文件路径
     *
     * @return 是否重命名成功
     */
    public static boolean renameFile(String filepath, String newName) {
        File file = new File(filepath);
        return file.exists() && file.renameTo(new File(newName));
    }

    /**
     * 获取文件夹下所有文件
     * @param path 文件路径
     *
     * @return 文件列表
     */
    @SuppressWarnings("WeakerAccess")
    public static ArrayList<File> getFilesArray(String path) {
        File file = new File(path);
        File files[] = file.listFiles();
        ArrayList<File> listFile = new ArrayList<>();
        if (files != null) {
            for(File fileSingle: files){
                if(fileSingle.isFile())
                    listFile.add(fileSingle);
                if(fileSingle.isDirectory())
                    listFile.addAll(getFilesArray(fileSingle.toString()));
            }
        }
        return listFile;
    }

    /**
     * 文件转字节数组
     * @param file 文件
     *
     * @return 字节数组
     */
    public static byte[] fileToByteAry(File file) {
        RandomAccessFile rf = null;
        byte[] data = null;
        try {
            rf = new RandomAccessFile(file, "r");
            data = new byte[(int) rf.length()];
            rf.readFully(data);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (rf != null) {
                    rf.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return data;
    }
}
