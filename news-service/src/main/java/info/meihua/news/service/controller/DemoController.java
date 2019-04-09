package info.meihua.news.service.controller;

import constant.GlobalConstants;
import entity.ResponseEntity;
import info.meihua.news.service.conf.datasource.DbIdentifier;
import info.meihua.news.service.service.IDemoService;
import info.meihua.news.service.service.IRedisService;
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
    public ResponseEntity get(@RequestParam(value = "projectCode") String projectCode) {

        DbIdentifier.setProjectDb(GlobalConstants.DEFAULT_PROJECT_PRE + projectCode + "#" + GlobalConstants.ENV_DEV_P + "#" + GlobalConstants.DEFAULT_MYSQL_READ);

        try {
            return new ResponseEntity(true, demoService.get());
        } catch (Exception e) {
            return new ResponseEntity(false, new ExceptionMsg(ExceptionMsg.INTERNAL_SERVER_ERROR, "内部服务器错误"));
        }
    }
}
