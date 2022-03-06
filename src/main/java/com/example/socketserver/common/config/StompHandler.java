package com.example.socketserver.common.config;

import com.example.socketserver.common.GlobalVariables;
import com.example.socketserver.common.event.DisconnectEvent;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@RequiredArgsConstructor
@Configuration
public class StompHandler implements ChannelInterceptor {

    private final ApplicationEventPublisher publisher;

    /**
     * 서버에서 클라이언트로 메시지 송신 후 호출됨 만약 도착지가 DISCONNECT 되었다면 세션을 삭제
     */
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
                publisher.publishEvent(new DisconnectEvent());
                break;
            default:
                break;
        }
    }


}
