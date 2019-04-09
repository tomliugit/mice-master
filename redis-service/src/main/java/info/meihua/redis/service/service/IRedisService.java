package info.meihua.redis.service.service;

import java.util.List;

/**
 * @author sunwell
 * @desc redis service
 */
public interface IRedisService {

    boolean set(int dbIndex, String key, String value);

    boolean del(int dbIndex, String key);

    String get(int dbIndex, String key);

    <T> boolean setList(int dbIndex, String key, List<T> list);

    <T> List<T> getList(int dbIndex, String key, Class<T> clz);

    long lpush(int dbIndex, String key, Object obj);

    long rpush(int dbIndex, String key, Object obj);

    String lpop(int dbIndex, String key);

}