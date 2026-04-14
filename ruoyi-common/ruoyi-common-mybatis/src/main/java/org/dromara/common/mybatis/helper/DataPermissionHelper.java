package org.dromara.common.mybatis.helper;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.hutool.core.util.ObjectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.mybatis.annotation.DataPermission;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * 数据权限助手
 *
 * @author Lion Li
 * @version 3.5.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unchecked")
public class DataPermissionHelper {

    private static final String DATA_PERMISSION_KEY = "data:permission";

    private static final ThreadLocal<Stack<Integer>> REENTRANT_IGNORE = ThreadLocal.withInitial(Stack::new);

    private static final ThreadLocal<DataPermission> PERMISSION_CACHE = new ThreadLocal<>();

    public static DataPermission getPermission() {
        return PERMISSION_CACHE.get();
    }

    public static void setPermission(DataPermission dataPermission) {
        PERMISSION_CACHE.set(dataPermission);
    }

    public static void removePermission() {
        PERMISSION_CACHE.remove();
    }

    public static boolean isIgnore() {
        return !REENTRANT_IGNORE.get().isEmpty();
    }

    public static <T> T getVariable(String key) {
        Map<String, Object> context = getContext();
        return (T) context.get(key);
    }

    public static void setVariable(String key, Object value) {
        Map<String, Object> context = getContext();
        context.put(key, value);
    }

    public static Map<String, Object> getContext() {
        SaStorage saStorage = SaHolder.getStorage();
        Object attribute = saStorage.get(DATA_PERMISSION_KEY);
        if (ObjectUtil.isNull(attribute)) {
            saStorage.set(DATA_PERMISSION_KEY, new HashMap<>());
            attribute = saStorage.get(DATA_PERMISSION_KEY);
        }
        if (attribute instanceof Map map) {
            return map;
        }
        throw new NullPointerException("data permission context type exception");
    }

    private static void enableIgnore() {
        Stack<Integer> reentrantStack = REENTRANT_IGNORE.get();
        reentrantStack.push(reentrantStack.size() + 1);
    }

    private static void disableIgnore() {
        Stack<Integer> reentrantStack = REENTRANT_IGNORE.get();
        if (!reentrantStack.isEmpty()) {
            reentrantStack.pop();
        }
    }

    public static void ignore(Runnable handle) {
        enableIgnore();
        try {
            handle.run();
        } finally {
            disableIgnore();
        }
    }

    public static <T> T ignore(Supplier<T> handle) {
        enableIgnore();
        try {
            return handle.get();
        } finally {
            disableIgnore();
        }
    }

}
