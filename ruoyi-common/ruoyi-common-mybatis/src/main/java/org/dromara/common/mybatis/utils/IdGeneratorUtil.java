package org.dromara.common.mybatis.utils;

import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * ID 生成工具类
 *
 * @author AprilWind
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdGeneratorUtil {

    /**
     * 生成字符串类型主键 ID (雪花算法)
     *
     * @return 字符串格式主键 ID
     */
    public static String nextId() {
        return nextLongId().toString();
    }

    /**
     * 生成 Long 类型主键 ID (雪花算法)
     *
     * @return Long 类型主键 ID
     */
    public static Long nextLongId() {
        Object id = KeyGeneratorFactory.getKeyGenerator("flexId").generate(null, null);
        return ((Number) id).longValue();
    }

    /**
     * 生成 Number 类型主键 ID
     *
     * @return Number 类型主键 ID
     */
    public static Number nextNumberId() {
        return nextLongId();
    }

    /**
     * 根据实体生成数字型主键 ID
     *
     * @param entity 实体对象
     * @return Number 类型主键 ID
     */
    public static Number nextId(Object entity) {
        return nextLongId();
    }

    /**
     * 根据实体生成字符串主键 ID
     *
     * @param entity 实体对象
     * @return 字符串格式主键 ID
     */
    public static String nextStringId(Object entity) {
        return nextId();
    }

    /**
     * 生成 32 位 UUID
     *
     * @return 32 位 UUID 字符串
     */
    public static String nextUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 根据实体生成 32 位 UUID
     *
     * @param entity 实体对象
     * @return 32 位 UUID 字符串
     */
    public static String nextUUID(Object entity) {
        return nextUUID();
    }

    /**
     * 生成带指定前缀的字符串主键 ID
     *
     * @param prefix 自定义前缀
     * @return 带前缀的字符串主键 ID
     */
    public static String nextIdWithPrefix(String prefix) {
        return prefix + nextId();
    }

    /**
     * 生成带前缀的 UUID
     *
     * @param prefix 前缀
     * @return prefix + UUID
     */
    public static String nextUUIDWithPrefix(String prefix) {
        return prefix + nextUUID();
    }

}
