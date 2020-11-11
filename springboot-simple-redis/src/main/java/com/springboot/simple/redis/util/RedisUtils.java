package com.springboot.simple.redis.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作工具接口
 *
 * @author jgz
 * CreateTime 2020/5/25 17:48
 */
public interface RedisUtils {
    Boolean expire(String key, long time);

    Long getExpire(String key);

    Boolean existKey(String key);

    Long del(String... key);

    String get(String key);

    Boolean set(String key, String value);

    Boolean set(String key, String value, long time);

    Long incr(String key, long delta);

    Boolean hSet(String key, String item, String value);

    Boolean hSet(String key, String item, String value, long time);

    String hGet(String key, String item);

    Boolean hmSet(String key, Map<String, String> map);

    Boolean hmSet(String key, Map<String, String> map, long time);

    Long hDel(String key, String... item);

    Boolean hExistKey(String key, String item);

    Long hIncr(String key, String item, long by);

    Long sSet(String key, String... values);

    Long sSet(String key, long time, String... values);

    Set<String> sGet(String key);

    Boolean sExistValue(String key, String value);

    Long sSize(String key);

    Long sDel(String key, String... values);

    Boolean lSet(String key, String value);

    Boolean lSet(String key, String value, long time);

    Long lSet(String key, List<String> value);

    Long lSet(String key, List<String> value, long time);

    Long lSet(String key, String... value);

    Long lSet(String key, long time, String... value);

    List<String> lGet(String key, long start, long end);

    String lGet(String key, long index);

    Long lSize(String key);

    Boolean lUpdateIndex(String key, long index, String value);

    Long lDel(String key, String value, long count);
}
