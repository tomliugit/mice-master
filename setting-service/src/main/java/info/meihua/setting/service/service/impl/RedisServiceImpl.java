package info.meihua.setting.service.service.impl;


import constant.GlobalConstants;
import entity.user.User;
import info.meihua.setting.service.client.ServiceRedisClient;
import info.meihua.setting.service.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.lang.GsonUtils;
import util.token.TokenUtils;


/**
 * @author sunwell
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class RedisServiceImpl implements IRedisService {
    private final ServiceRedisClient serviceRedisClient;


    @Autowired
    public RedisServiceImpl(ServiceRedisClient serviceRedisClient) {
        this.serviceRedisClient = serviceRedisClient;
    }

    @Override
    public void set(String key, String value) {
        serviceRedisClient.redisSet(GlobalConstants.REDIS_DB_INDEX_MH, key.replace("/", "").replace(".", "").replace("_", ""), value);
    }

    @Override
    public String get(String key) {
        return serviceRedisClient.redisGet(GlobalConstants.REDIS_DB_INDEX_MH, key.replace("/", "").replace(".", "").replace("_", ""));
    }

    @Override
    public User checkToken(String token) throws Exception {
        String userStr = serviceRedisClient.redisGet(GlobalConstants.REDIS_DB_INDEX_MH, TokenUtils.getUidByToken(token));
        User user = (User) GsonUtils.json2object(userStr, User.class);
        if (user == null) {
            throw new Exception("Error Token");
        }
        return user;
    }

    @Override
    public void lPush(String key, String value) {
        serviceRedisClient.redisLPush(GlobalConstants.REDIS_DB_INDEX_MH, key, value);
    }
}
