package com.example.socketserver.dto.response;

import com.example.socketserver.common.GlobalVariables;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class SocketResponse<T> {

    private String serverTime;
    private int sessionCount;
    private T data;

    public static <T> SocketResponse<T> of(T data){
        return SocketResponse.<T>builder()
            .serverTime(LocalDateTime.now().toString())
            .sessionCount(GlobalVariables.getSessionCount())
            .data(data)
            .build();
    }
}
