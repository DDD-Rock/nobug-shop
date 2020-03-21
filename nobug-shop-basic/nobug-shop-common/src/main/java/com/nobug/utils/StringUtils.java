package com.nobug.utils;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 用于组织Redis中的key
     * @param prefix 前缀
     * @param key
     * @return
     */
    public static String getRedisKey(String prefix,String key){
        String redisKey = new StringBuilder().append(prefix).append("=").append(key).toString();

        return redisKey;
    }
}
