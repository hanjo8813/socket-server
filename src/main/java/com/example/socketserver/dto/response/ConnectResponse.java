package com.example.socketserver.dto.response;

import com.example.socketserver.dto.TableDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConnectResponse {

    private String clientTime;
    private List<TableDto> tables;

    public static ConnectResponse of(String clientTime, List<TableDto> tables) {
        return ConnectResponse.builder()
            .clientTime(clientTime)
            .tables(tables)
            .build();
    }
}
