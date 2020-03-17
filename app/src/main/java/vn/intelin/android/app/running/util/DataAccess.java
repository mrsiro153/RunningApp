package vn.intelin.android.app.running.util;

import java.util.HashMap;

public class DataAccess {
    public final static String USER = "userInfo";
    private static HashMap<String, Object> data = new HashMap<>();

    public static void push(String key, Object value) {
        data.put(key, value);
    }

    public static Object get(String key) {
        return data.get(key);
    }
}
