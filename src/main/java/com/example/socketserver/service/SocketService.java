package com.example.socketserver.service;

import com.example.socketserver.common.GlobalVariables;
import com.example.socketserver.dto.request.ChangeFireStatusRequest;
import com.example.socketserver.dto.request.ConnectRequest;
import com.example.socketserver.dto.response.ChangeFireStatusResponse;
import com.example.socketserver.dto.response.ConnectResponse;
import com.example.socketserver.dto.response.SocketResponse;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    public ConnectResponse connect(ConnectRequest request) {
        GlobalVariables.initTables(request.getTables());
        return ConnectResponse.of(request.getClientTime(), GlobalVariables.getTables());
    }

    public ChangeFireStatusResponse changeFireStatus(ChangeFireStatusRequest request) {
        GlobalVariables.changeTargetFireStatus(request.getTargetId());
        return ChangeFireStatusResponse.of(GlobalVariables.getTables());
    }

}
