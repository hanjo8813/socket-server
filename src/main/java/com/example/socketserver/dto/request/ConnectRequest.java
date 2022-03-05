package com.example.socketserver.dto.request;

import com.example.socketserver.dto.TableDto;
import java.util.List;
import lombok.Getter;

@Getter
public class ConnectRequest {

    private String clientTime;
    private List<TableDto> tables;
}
