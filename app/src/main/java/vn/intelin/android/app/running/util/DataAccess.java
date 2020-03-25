package vn.intelin.android.app.running.util;

import java.util.HashMap;
import java.util.List;

import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.util.JsonConverter;

public final class DataAccess {
    public enum DataKey {
        USER("userInfo", String.class), //string class-> json
        EVENT("event", List.class);
        public String key;
        public Class clazz;

        DataKey(String key, Class clazz) {
            this.key = key;
            this.clazz = clazz;
        }
    }

    private static HashMap<String, Object> data = new HashMap<>();


    public static void push(DataAccess.DataKey key, Object value) {
        data.put(key.key, value);
    }

    public static Object get(DataKey key){
        return data.get(key.key);
    }

    public static <T> T getJsonAs(DataAccess.DataKey key, Class<T> clazz) {
        return JsonConverter.fromJson(String.valueOf(data.get(key.key)), clazz);
    }

    public static <T> T getAs(DataAccess.DataKey dataKey,Class<T> clazz) {
        return (T) data.get(dataKey.key);
    }
}
