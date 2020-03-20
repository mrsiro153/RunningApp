package vn.intelin.android.app.running.util;

import java.util.HashMap;

import vn.intelin.android.running.util.JsonConverter;

public class DataAccess {
    public final static String USER = "userInfo";
    private static HashMap<String, Object> data = new HashMap<>();

    public static void push(String key, Object value) {
        data.put(key, value);
    }

    public static Object get(String key) {
        return data.get(key);
    }

    public static <T> T getAs(String key, Class<T> clazz) {
        return JsonConverter.fromJson(String.valueOf(data.get(key)), clazz);
    }
}
