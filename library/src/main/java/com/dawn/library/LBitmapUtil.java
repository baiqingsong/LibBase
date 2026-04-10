package com.dawn.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bitmap工具类
 */
@SuppressWarnings("unused")
public class LBitmapUtil {

    /**
     * 按比例缩放图片
     * @param bitmap 原始图片
     * @param ratio 缩放比例（0.0-1.0）
     * @return 缩放后的图片
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float ratio) {
        if (bitmap == null || ratio <= 0) return bitmap;
        int width = (int) (bitmap.getWidth() * ratio);
        int height = (int) (bitmap.getHeight() * ratio);
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 按指定宽高缩放图片
     * @param bitmap 原始图片
     * @param newWidth 目标宽度
     * @param newHeight 目标高度
     * @return 缩放后的图片
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        if (bitmap == null || newWidth <= 0 || newHeight <= 0) return bitmap;
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    /**
     * 旋转图片
     * @param bitmap 原始图片
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        if (bitmap == null || degrees == 0) return bitmap;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 裁剪图片为圆形
     * @param bitmap 原始图片
     * @return 圆形图片
     */
    public static Bitmap toCircle(Bitmap bitmap) {
        if (bitmap == null) return null;
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (size - bitmap.getWidth()) / 2f,
                (size - bitmap.getHeight()) / 2f, paint);
        return output;
    }

    /**
     * 裁剪图片为圆角
     * @param bitmap 原始图片
     * @param radius 圆角半径
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float radius) {
        if (bitmap == null) return null;
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * Bitmap转byte数组
     * @param bitmap 图片
     * @param format 格式
     * @param quality 质量（0-100）
     * @return byte数组
     */
    public static byte[] bitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat format, int quality) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, quality, baos);
        return baos.toByteArray();
    }

    /**
     * byte数组转Bitmap
     * @param bytes byte数组
     * @return Bitmap
     */
    public static Bitmap bytesToBitmap(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Bitmap转Base64字符串
     * @param bitmap 图片
     * @param format 格式
     * @param quality 质量（0-100）
     * @return Base64字符串
     */
    public static String bitmapToBase64(Bitmap bitmap, Bitmap.CompressFormat format, int quality) {
        byte[] bytes = bitmapToBytes(bitmap, format, quality);
        if (bytes == null) return "";
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * Base64字符串转Bitmap
     * @param base64 Base64字符串
     * @return Bitmap
     */
    public static Bitmap base64ToBitmap(String base64) {
        if (base64 == null || base64.isEmpty()) return null;
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 保存Bitmap到文件
     * @param bitmap 图片
     * @param file 目标文件
     * @param format 格式
     * @param quality 质量（0-100）
     * @return 是否保存成功
     */
    public static boolean saveBitmap(Bitmap bitmap, File file, Bitmap.CompressFormat format, int quality) {
        if (bitmap == null || file == null) return false;
        FileOutputStream fos = null;
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(format, quality, fos);
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            LFileUtil.closeIO(fos);
        }
    }

    /**
     * 从文件加载Bitmap（按目标尺寸采样，避免OOM）
     * @param filePath 文件路径
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmap(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 从资源加载Bitmap（按目标尺寸采样，避免OOM）
     * @param context 上下文
     * @param resId 资源ID
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Context context, int resId,
                                                          int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    /**
     * 计算采样率
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                              int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 水平翻转图片
     * @param bitmap 原始图片
     * @return 翻转后的图片
     */
    public static Bitmap flipHorizontal(Bitmap bitmap) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 垂直翻转图片
     * @param bitmap 原始图片
     * @return 翻转后的图片
     */
    public static Bitmap flipVertical(Bitmap bitmap) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 获取图片宽高（不加载到内存）
     * @param filePath 文件路径
     * @return int[2]，[0]为宽，[1]为高
     */
    public static int[] getImageSize(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 安全回收Bitmap
     * @param bitmap 需要回收的Bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
