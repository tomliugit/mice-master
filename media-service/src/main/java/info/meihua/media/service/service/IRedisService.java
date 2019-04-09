package info.meihua.media.service.service;


import entity.user.User;

/**
 * @author sunwell
 */
public interface IRedisService {
    /**
     * 设置
     *
     * @param key
     * @param value
     * @throws Exception
     */
    void set(String key, String value) throws Exception;

    /**
     * 获取
     *
     * @param key
     * @return
     * @throws Exception
     */
    String get(String key) throws Exception;

    /**
     * 从redis获取用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    User checkToken(String token) throws Exception;


    /**
     * 队列
     *
     * @param key
     * @param value
     * @throws Exception
     */
    void lPush(String key, String value) throws Exception;
}
