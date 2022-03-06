package com.example.socketserver.common.event;

import com.example.socketserver.dto.response.SocketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventHandler {

    private final SimpMessagingTemplate template;

    @Async
    @EventListener
    public void sendDisconnectMessage(DisconnectEvent event){
        template.convertAndSend("/topic/disconnect", SocketResponse.of(null));
    }

}
