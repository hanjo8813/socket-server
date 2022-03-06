package com.example.socketserver.common.config;

import com.example.socketserver.common.GlobalVariables;
import java.util.Objects;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
public class StompHandler implements ChannelInterceptor {

    // 서버에서 클라이언트로 송신 후 호출됨
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT:
                GlobalVariables.addSession(sessionId);
                break;
            case DISCONNECT:
                GlobalVariables.removeSession(sessionId);
                break;
            default:
                break;
        }
    }
}
