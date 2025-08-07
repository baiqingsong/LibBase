package com.dawn.library;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 密码工具类
 */
@SuppressWarnings("unused")
public class LCipherUtil {
    /**
     * 字符串md5
     * @param input 要md5的字符串
     *
     * @return md5加密后的字符串
     */
    public static String encryptMD5(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            char[] resultCharArray = new char[resultByteArray.length * 2];
            int index = 0;
            for (byte b : resultByteArray) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            return new String(resultCharArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输入流md5
     * @param in 输入流
     *
     * @return md5加密后的字符串
     */
    public static String encryptMD5(InputStream in) {
        int bufferSize = 256 * 1024;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(in, messageDigest);
            byte[] buffer = new byte[bufferSize];
            if (digestInputStream.read(buffer) > 0)
                return null;
            messageDigest = digestInputStream.getMessageDigest();
            byte[] resultByteArray = messageDigest.digest();
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            char[] resultCharArray = new char[resultByteArray.length * 2];
            int index = 0;
            for (byte b : resultByteArray) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            return new String(resultCharArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (digestInputStream != null)
                    digestInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * base64加密
     * @param str 要加密的字符串
     *
     * @return base64加密后的字符串
     */
    public static String base64Encode(String str) {
        return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64解密
     * @param str 要解密的字符串
     *
     * @return base64解密后的字符串
     */
    public static String base64Decode(String str) {
        return new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
    }

    /**
     * SHA1加密
     * @param str 需要获取sha1的字符串
     *
     * @return sha1加密后的字符串
     */
    public static String encryptSHA1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA1加密
     * @param file 需要获取sha1的文件
     *
     * @return sha1加密后的字符串
     */
    public static String encryptSHA1(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] b = new byte[1024 * 1024 * 10];//10M memory
            int len;
            while ((len = in.read(b)) > 0) {
                messageDigest.update(b, 0, len);
            }
            return LStringUtil.toHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LFileUtil.closeIO(in);
        }
        return null;
    }

    /**
     * 异或加密
     * @param str 加密的字符串
     * @param privateKey 加密的key
     *
     * @return 异或加密后的字符串
     */
    public static String XorEncode(String str, String privateKey) {
        int[] snNum = new int[str.length()];
        String temp = "";
        for (int i = 0, j = 0; i < str.length(); i++, j++) {
            if (j == privateKey.length())
                j = 0;
            snNum[i] = str.charAt(i) ^ privateKey.charAt(j);
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < str.length(); k++) {
            if (snNum[k] < 10) {
                temp = "00" + snNum[k];
            } else {
                if (snNum[k] < 100) {
                    temp = "0" + snNum[k];
                }
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 异或解密
     * @param str 需要解密的字符串
     * @param privateKey 解密的key
     *
     * @return 异或解密后的字符串
     */
    public static String XorDecode(String str, String privateKey) {
        char[] snNum = new char[str.length() / 3];

        for (int i = 0, j = 0; i < str.length() / 3; i++, j++) {
            if (j == privateKey.length())
                j = 0;
            int n = Integer.parseInt(str.substring(i * 3, i * 3 + 3));
            snNum[i] = (char) ((char) n ^ privateKey.charAt(j));
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < str.length() / 3; k++) {
            sb.append(snNum[k]);
        }
        return sb.toString();
    }

    /**
     * AES加密
     * @param message 加密的字符串
     * @param passWord 加密的密码
     *
     * @return AES加密后的字符串
     */
    public static String encryptAES(String message, String passWord)
            throws GeneralSecurityException, UnsupportedEncodingException {
        return LStringUtil.toHexString(encryptAES(message.getBytes("UTF-8"), LStringUtil.toByteArray(passWord)));
    }

    /**
     * AES解密
     * @param message 需要解密的字符串
     * @param passWord 解密的密码
     *
     * @return AES解密后的字符串
     */
    public static String decryptAES(String message, String passWord)
            throws GeneralSecurityException {
        return new String(decryptAES(LStringUtil.toByteArray(message), LStringUtil.toByteArray(passWord)));
    }

    /**
     * AES加密
     * @param source 加密的数据
     * @param rawKeyData 加密的密码
     *
     * @return AES加密后的数据
     */
    @SuppressLint("GetInstance")
    private static byte[] encryptAES(byte[] source, byte rawKeyData[])
            throws GeneralSecurityException {
        // 处理密钥
        SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
        // 加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(source);
    }

    /**
     * AES解密
     * @param data 需要解密的数据
     * @param rawKeyData 解密的密码
     *
     * @return AES解密后的数据
     */
    @SuppressLint("GetInstance")
    private static byte[] decryptAES(byte[] data, byte rawKeyData[])
            throws GeneralSecurityException {
        // 处理密钥
        SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
        // 解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * CRC32校验
     * @param data 数据
     * @return CRC32校验值
     */
    public static long crc32(byte[] data) {
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    /**
     * CRC32校验
     * @param str 字符串
     * @return CRC32校验值
     */
    public static long crc32(String str) {
        try {
            return crc32(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * SHA256加密
     * @param str 需要加密的字符串
     * @return SHA256加密后的字符串
     */
    public static String encryptSHA256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes("UTF-8"));
            byte[] hash = digest.digest();
            return LStringUtil.toHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SHA512加密
     * @param str 需要加密的字符串
     * @return SHA512加密后的字符串
     */
    public static String encryptSHA512(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(str.getBytes("UTF-8"));
            byte[] hash = digest.digest();
            return LStringUtil.toHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成指定长度的随机字符串（用于密钥生成）
     * @param length 长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * URL编码
     * @param str 需要编码的字符串
     * @return URL编码后的字符串
     */
    public static String urlEncode(String str) {
        try {
            return java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * URL解码
     * @param str 需要解码的字符串
     * @return URL解码后的字符串
     */
    public static String urlDecode(String str) {
        try {
            return java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }


}
