package info.meihua.message.service.websocket.interceptor;

import constant.GlobalConstants;
import entity.message.Message;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import util.mh.MhUtils;
import util.token.TokenUtils;

import java.util.Map;


/**
 * @author sunwell
 */
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

        String token = servletRequest.getServletRequest().getParameter("token");
        int scope = Integer.valueOf(servletRequest.getServletRequest().getParameter("scope"));
        String appCode = servletRequest.getServletRequest().getParameter("app");
        int tokenType = Integer.valueOf(servletRequest.getServletRequest().getParameter("token_type"));

        if (tokenType == Message.TOKEN_TYPE_USERNAME) {
            attributes.put(GlobalConstants.ENCRYPT_KEY_FOR_WEB_SOCKET_SESSION, MhUtils.getUserId(token).toString());
        } else {
            attributes.put(GlobalConstants.ENCRYPT_KEY_FOR_WEB_SOCKET_SESSION, TokenUtils.getUidByToken(token));
        }
        attributes.put("scope", scope);
        attributes.put("app", appCode);
        //建立连接
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
