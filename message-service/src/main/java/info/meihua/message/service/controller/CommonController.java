package info.meihua.message.service.controller;

import entity.ResponseEntity;
import entity.message.Message;
import info.meihua.message.service.service.IMsgService;
import info.meihua.message.service.websocket.handler.SystemWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import util.lang.GsonUtils;
import util.mh.MhUtils;
import util.token.TokenUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ************************************************************************************************
 * 基础 APIs
 * ws://127.0.0.1:6513/server?token=4d00589065e90365cffc8653add174c19736b7692c092135c627ac4c54407c4919d7dbac1117250a&scope=1&app=599c3f4e511d4c358a8e1540507ff29e&token_type=0
 * **************************************************************************************************
 *
 * @author sunwell
 */
@RestController
@SuppressWarnings({"unchecked", "serial"})
public class CommonController {
    private final IMsgService msgService;

    @Autowired
    public CommonController(IMsgService msgService) {
        this.msgService = msgService;
    }

    @Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }

    @RequestMapping(value = "/push/{scope}/{type}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity doPush(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "token_type", defaultValue = "0", required = false) Integer tokenType,
                                 @RequestParam(value = "content") String content,
                                 @PathVariable(value = "type") Integer type,
                                 @PathVariable(value = "scope") Integer scope,
                                 @RequestParam(value = "appCode") String appCode) {
        try {
            Long userId;
            if (tokenType == Message.TOKEN_TYPE_USERNAME) {
                userId = MhUtils.getUserId(token);
            } else {
                userId = Long.valueOf(TokenUtils.getUidByToken(token));
            }
            if (type == null) {
                type = Message.TYPE_DEFAULT;
            }
            if ("".equals(content)) {
                return new ResponseEntity(false, "请填写消息内容！");
            }
            long id = msgService.add(content, type, scope, userId, appCode);
            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("id", id);
            msgMap.put("content", content);
            msgMap.put("scope", scope);
            msgMap.put("type", type);
            systemWebSocketHandler().sendMessageToUser(String.valueOf(userId), String.valueOf(scope), appCode, new TextMessage(GsonUtils.object2json(msgMap)));
            return new ResponseEntity(true, id);
        } catch (Exception e) {
            return new ResponseEntity(false, e.getMessage());
        }

    }

    @RequestMapping(value = "/push/{userId}/{scope}/{type}")
    public void doPushUser(@RequestParam(value = "content") String content,
                           @PathVariable(value = "type") Integer type,
                           @PathVariable(value = "scope") Integer scope,
                           @PathVariable(value = "userId") Long userId,
                           @RequestParam(value = "appCode") String appCode) {
        try {
            if (type == null) {
                type = Message.TYPE_DEFAULT;
            }
            long id = msgService.add(content, type, scope, userId, appCode);
            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("id", id);
            msgMap.put("content", content);
            msgMap.put("scope", scope);
            msgMap.put("type", type);
            systemWebSocketHandler().sendMessageToUser(String.valueOf(userId), String.valueOf(scope), appCode, new TextMessage(GsonUtils.object2json(msgMap)));
        } catch (Exception e) {
        }

    }

    @RequestMapping(value = "/messages", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity list(@RequestParam(value = "token") String token,
                               @RequestParam(value = "token_type", defaultValue = "0", required = false) Integer tokenType,
                               @RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "type", required = false) Integer type,
                               @RequestParam(value = "scope", required = false) Integer scope,
                               @RequestParam(value = "appCode") String appCode,
                               @RequestParam(value = "page", required = false) Integer pageIndex,
                               @RequestParam(value = "limit", required = false) Integer limit) {
        try {
            Long userId;
            if (tokenType == Message.TOKEN_TYPE_USERNAME) {
                userId = MhUtils.getUserId(token);
            } else {
                userId = Long.valueOf(TokenUtils.getUidByToken(token));
            }

            if (type == null) {
                type = Message.TYPE_ALL;
            }
            if (scope == null) {
                scope = Message.SCOPE_ALL;
            }
            if (status == null) {
                status = Message.STATUS_ALL;
            }

            Integer start = null;
            if (pageIndex != null && limit != null) {
                start = (pageIndex - 1) * limit;
            }
            Map<String, Object> result = msgService.list(status, type, scope, userId, appCode, start, limit);
            return new ResponseEntity(true, result);
        } catch (Exception e) {
            return new ResponseEntity(false, e.getMessage());
        }

    }


    @RequestMapping(value = "/read", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity doRead(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "token_type", defaultValue = "0", required = false) Integer tokenType,
                                 @RequestParam(value = "id") Long id,
                                 @RequestParam(value = "appCode") String appCode) {
        try {
            Long userId;
            if (tokenType == Message.TOKEN_TYPE_USERNAME) {
                userId = MhUtils.getUserId(token);
            } else {
                userId = Long.valueOf(TokenUtils.getUidByToken(token));
            }
            msgService.doRead(id, userId, appCode);
            return new ResponseEntity(true, true);
        } catch (Exception e) {
            return new ResponseEntity(false, e.getMessage());
        }

    }


    @RequestMapping(value = "/batch-read", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity doReadBatch(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "token_type", defaultValue = "0", required = false) Integer tokenType,
                                      @RequestParam(value = "ids") List<Long> ids,
                                      @RequestParam(value = "appCode") String appCode) {
        try {
            Long userId;
            if (tokenType == Message.TOKEN_TYPE_USERNAME) {
                userId = MhUtils.getUserId(token);
            } else {
                userId = Long.valueOf(TokenUtils.getUidByToken(token));
            }
            msgService.doReadBatch(ids, userId, appCode);
            return new ResponseEntity(true, true);
        } catch (Exception e) {
            return new ResponseEntity(false, e.getMessage());
        }

    }


    @RequestMapping(value = "/read/{scope}/{type}", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseEntity doReadBatch(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "token_type", defaultValue = "0", required = false) Integer tokenType,
                                      @PathVariable(value = "type") Integer type,
                                      @PathVariable(value = "scope") Integer scope,
                                      @RequestParam(value = "appCode") String appCode) {
        try {
            Long userId;
            if (tokenType == Message.TOKEN_TYPE_USERNAME) {
                userId = MhUtils.getUserId(token);
            } else {
                userId = Long.valueOf(TokenUtils.getUidByToken(token));
            }

            msgService.doReadBatchByTypeScope(type, scope, userId, appCode);
            return new ResponseEntity(true, true);
        } catch (Exception e) {
            return new ResponseEntity(false, e.getMessage());
        }

    }


}
