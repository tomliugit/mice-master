package info.meihua.media.service.controller;

import entity.ResponseEntity;
import info.meihua.media.service.service.IDemoService;
import info.meihua.media.service.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.exception.ExceptionMsg;


/**
 * @author sunwell
 */
@CrossOrigin
@RestController
public class DemoController {
    private final IDemoService demoService;
    private final IRedisService redisService;

    @Autowired
    public DemoController(IDemoService demoService, IRedisService redisService) {
        this.demoService = demoService;
        this.redisService = redisService;
    }

    /**
     * Demo
     */
    @RequestMapping(value = {"/demo"}, method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(name = "token", required = false) String token) {
//        User user;
//        try {
//            user = redisService.checkToken(token);
//        } catch (Exception e) {
//            return new ResponseEntity(false, new ExceptionMsg(ExceptionMsg.INTERNAL_SERVER_ERROR, e.getMessage()));
//        }
        try {
            return new ResponseEntity(true, demoService.get());
        } catch (Exception e) {
            return new ResponseEntity(false, new ExceptionMsg(ExceptionMsg.INTERNAL_SERVER_ERROR, "内部服务器错误"));
        }
    }
}
