package info.meihua.message.service.websocket.handler;

import constant.GlobalConstants;
import entity.message.Message;
import info.meihua.message.service.service.IMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import util.lang.GsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author sunwellgu
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
    }

    @Autowired
    private IMsgService msgService = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        String userId = String.valueOf(session.getAttributes().get(GlobalConstants.ENCRYPT_KEY_FOR_WEB_SOCKET_SESSION));
        if (!users.contains(session)) {
            //每个用户一个session
            users.add(session);
        }

        Integer scope = Integer.valueOf(String.valueOf(session.getAttributes().get("scope")));
        String appCode = String.valueOf(session.getAttributes().get("app"));
        if (userId != null) {
            Map<String, Object> result = msgService.list(Message.STATUS_UNREAD, Message.TYPE_ALL, scope, Long.valueOf(userId), appCode,
                    null, null);
            session.sendMessage(new TextMessage(GsonUtils.object2json(result)));
        }
    }

    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) {
//        TextMessage returnMessage = new TextMessage(wsm.getPayload()
//                + " received at server");
//        wss.sendMessage(returnMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if (wss.isOpen()) {
            wss.close();
        }
        users.remove(wss);//失联
//        System.out.println("websocket connection closed......");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) {
        users.remove(wss);//失联
//        System.out.println("websocket connection closed......");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, String scope, String app, TextMessage message) {
        try {
            for (WebSocketSession user : users) {
                if (user == null) {
                    continue;
                }
                String uid = String.valueOf(user.getAttributes().get(GlobalConstants.ENCRYPT_KEY_FOR_WEB_SOCKET_SESSION));
                String sessionScope = String.valueOf(user.getAttributes().get("scope"));
                String appCode = String.valueOf(user.getAttributes().get("app"));

                if (uid.equals(userId) && sessionScope.equals(scope) && appCode.equals(app)) {
                    try {
                        if (user.isOpen()) {
                            user.sendMessage(message);
                        }
                    } catch (IOException | Error e) {
//                        logger.error("1");
//                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception | Error e) {
//            logger.error("3");
//            e.printStackTrace();
        }

    }
}
