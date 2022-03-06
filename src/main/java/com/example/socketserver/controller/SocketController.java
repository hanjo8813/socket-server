package com.example.socketserver.controller;

import com.example.socketserver.dto.request.ChangeFireStatusRequest;
import com.example.socketserver.dto.request.ConnectRequest;
import com.example.socketserver.dto.response.TableResponse;
import com.example.socketserver.dto.response.ConnectResponse;
import com.example.socketserver.dto.response.SocketResponse;
import com.example.socketserver.service.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SocketController {

    private final SocketService service;

    /**
     * @MessageMapping : 클라이언트 -> 서버로 요청하는 주소
     * @SendTo : 서버 -> 클라언트로 보내는 주소 /ws/~ 주소로 받은후 /topic/~ 주소로 내보냄
     */

    // 소켓 연결확인
    @MessageMapping("/connect")
    @SendTo("/topic/connect")
    public SocketResponse<ConnectResponse> connect(ConnectRequest request) {
        ConnectResponse response = service.connect(request);
        return SocketResponse.of(response);
    }

    // 테이블 status 변경
    @MessageMapping("/change/fireStatus")
    @SendTo("/topic/change/fireStatus")
    public SocketResponse<TableResponse> changeFireStatus(ChangeFireStatusRequest request) {
        TableResponse response = service.changeFireStatus(request);
        return SocketResponse.of(response);
    }
}