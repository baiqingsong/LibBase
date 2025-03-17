package com.dawn.library;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json工具类（需要依赖Gson2.0以上）
 */
@SuppressWarnings("unused")
public class LJsonUtil {
    /**
     * 对象转json
     * @param obj 实体类或集合
     *
     * @return json字符串
     */
    public static String objToJson(Object obj) {
        if(obj == null)
            return "";
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json转对象
     * @param str 转换的字符串
     * @param type 类
     *
     * @return 实体类
     */
    public static <T> T jsonToObj(String str, Class<T> type) {
        if(str == null)
            return null;
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * 实体类集合转json
     * @param list 实体类集合
     *
     * @return json字符串
     */
    public static <T> String listToJson(List<T> list) {
        if (list == null || list.isEmpty())
            return "[]";
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    /**
     * json转实体类集合
     * @param json json字符串
     * @param clazz 实体类
     *
     * @return 实体类集合
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        if (json == null)
            return null;
        Type type = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

}
