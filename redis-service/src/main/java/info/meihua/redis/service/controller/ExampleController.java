package info.meihua.redis.service.controller;

import info.meihua.redis.service.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sunwell
 */
@RestController
public class ExampleController {

    @Autowired
    private IRedisService redisService;

    @RequestMapping(value = "/redis/set/{db}/{key}")
    public Boolean redisSet(@PathVariable("db") Integer db, @PathVariable("key") String key, @RequestParam("value") String value) {
        if (db == null) {
            db = 5;
        }
        return redisService.set(db, key, value);
    }


    @RequestMapping(value = "/redis/get/{db}/{key}")
    public String redisGet(@PathVariable("db") Integer db, @PathVariable("key") String key) {
        if (db == null) {
            db = 5;
        }
        return redisService.get(db, key);
    }

    @RequestMapping(value = "/redis/del/{db}/{key}")
    public Boolean redisDel(@PathVariable("db") Integer db, @PathVariable("key") String key) {
        if (db == null) {
            db = 5;
        }
        return redisService.del(db, key);
    }

    @RequestMapping(value = "/redis/push/{db}/{key}")
    public Long redisLPush(@PathVariable("db") Integer db, @PathVariable("key") String key, @RequestParam("value") String value) {
        if (db == null) {
            db = 5;
        }
        return redisService.lpush(db, key, value);
    }

    @RequestMapping(value = "/redis/rpush/{db}/{key}")
    public Long redisRPush(@PathVariable("db") Integer db, @PathVariable("key") String key, @RequestParam("value") String value) {
        if (db == null) {
            db = 5;
        }
        return redisService.rpush(db, key, value);
    }

    @RequestMapping(value = "/redis/pop/{db}/{key}")
    public String redisLPop(@PathVariable("db") Integer db, @PathVariable("key") String key) {
        if (db == null) {
            db = 5;
        }
        return redisService.lpop(db, key);
    }

}
