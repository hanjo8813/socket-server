package com.example.socketserver.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    /**
     * @MessageMapping : 클라이언트 -> 서버로 요청하는 주소
     * @SendTo : 서버 -> 클라언트로 보내는 주소
     * /ws/connect 주소로 받은후 /topic/connect 주소로 내보냄
     */

    // 소켓 연결확인
    @MessageMapping("connect")
    @SendTo("/topic/connect")
    public Map<String, String> connect(String clientTime){
        Map<String, String> time = new HashMap<>();
        time.put("client", clientTime);
        time.put("server", LocalDateTime.now().toString());
        return time;
    }


}