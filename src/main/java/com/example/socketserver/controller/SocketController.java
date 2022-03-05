package com.example.socketserver.controller;

import com.example.socketserver.dto.request.ChangeFireStatusRequest;
import com.example.socketserver.dto.TableDto;
import com.example.socketserver.dto.request.ConnectRequest;
import com.example.socketserver.dto.response.ChangeFireStatusResponse;
import com.example.socketserver.dto.response.ConnectResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    // 상태관리용 테이블
    private static List<TableDto> serverTables = new ArrayList<>();

    /**
     * @MessageMapping : 클라이언트 -> 서버로 요청하는 주소
     * @SendTo : 서버 -> 클라언트로 보내는 주소 /ws/~ 주소로 받은후 /topic/~ 주소로 내보냄
     */

    // 소켓 연결확인
    @MessageMapping("/connect")
    @SendTo("/topic/connect")
    public ConnectResponse connect(ConnectRequest request) {
        // 만약 서버 테이블이 초기화되지 않았다면 클라이언트 상태로 초기화
        if(serverTables.isEmpty()){
            serverTables = request.getTables();
        }
        return ConnectResponse.of(request.getClientTime(), serverTables);
    }

    // 테이블 status 변경
    @MessageMapping("/change/fireStatus")
    @SendTo("/topic/change/fireStatus")
    public ChangeFireStatusResponse changeFireStatus(ChangeFireStatusRequest request) {
        final Long targetId = request.getTargetId();

        serverTables.forEach(table -> {
            if (table.getId().equals(targetId)) {
                table.changeFireStatus();
            }
        });

        return ChangeFireStatusResponse.of(serverTables);
    }
}