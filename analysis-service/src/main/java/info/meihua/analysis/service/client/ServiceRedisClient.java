package info.meihua.analysis.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sunwell
 */
@FeignClient(name = "redis-service", fallback = ServiceRedisClient.ServiceRedisClientFallback.class)
public interface ServiceRedisClient {

    /**
     * 获取
     *
     * @param db
     * @param key
     * @return
     */
    @GetMapping(value = "/redis/get/{db}/{key}")
    String redisGet(@PathVariable("db") Integer db, @PathVariable("key") String key);

    /**
     * 设置
     *
     * @param db
     * @param key
     * @param value
     * @return
     */
    @PostMapping(value = "/redis/set/{db}/{key}")
    Boolean redisSet(@PathVariable("db") Integer db, @PathVariable("key") String key, @RequestParam("value") String value);


    /**
     * 删除
     *
     * @param db
     * @param key
     * @return
     */
    @GetMapping(value = "/redis/del/{db}/{key}")
    Boolean redisDel(@PathVariable("db") Integer db, @PathVariable("key") String key);

    /**
     * 队列
     *
     * @param db
     * @param key
     * @param value
     * @return
     */
    @PostMapping(value = "/redis/push/{db}/{key}")
    Long redisLPush(@PathVariable("db") Integer db, @PathVariable("key") String key, @RequestParam("value") String value);

    @Component
    class ServiceRedisClientFallback implements ServiceRedisClient {

        private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRedisClientFallback.class);

        @Override
        public String redisGet(Integer db, String key) {
            LOGGER.info("异常发生，进入redisGet fallback方法");
            return null;
        }

        @Override
        public Boolean redisSet(Integer db, String key, String value) {
            LOGGER.info("异常发生，进入redisSet fallback方法");
            return false;
        }

        @Override
        public Boolean redisDel(Integer db, String key) {
            LOGGER.info("异常发生，进入redisDel fallback方法");
            return false;
        }

        @Override
        public Long redisLPush(Integer db, String key, String value) {
            LOGGER.info("异常发生，进入redisLPush fallback方法");
            return null;
        }
    }
}