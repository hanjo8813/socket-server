package com.example.socketserver.controller;

import com.example.socketserver.dto.ChangeStatusRequest;
import com.example.socketserver.dto.TableDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    /**
     * @MessageMapping : 클라이언트 -> 서버로 요청하는 주소
     * @SendTo : 서버 -> 클라언트로 보내는 주소 /ws/~ 주소로 받은후 /topic/~ 주소로 내보냄
     */

    // 소켓 연결확인
    @MessageMapping("/connect")
    @SendTo("/topic/connect")
    public Map<String, String> connect(String clientTime) {
        Map<String, String> time = new HashMap<>();
        time.put("client", clientTime);
        time.put("server", LocalDateTime.now().toString());
        return time;
    }

    // 테이블 status 변경
    @MessageMapping("/change/fireStatus")
    @SendTo("/topic/change/fireStatus")
    public List<TableDto> changeFireStatus(ChangeStatusRequest request) {
        final Long targetId = request.getTargetId();
        List<TableDto> tables = request.getTables();

        return tables.stream()
            .peek(table -> {
                if (table.getId().equals(targetId)) {
                    table.changeFireStatus();
                }
            })
            .collect(Collectors.toList());
    }
}