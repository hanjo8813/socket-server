package com.example.socketserver.dto.response;

import com.example.socketserver.dto.TableDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangeFireStatusResponse {

    private List<TableDto> tables;

    public static ChangeFireStatusResponse of(List<TableDto> tables) {
        return ChangeFireStatusResponse.builder()
            .tables(tables)
            .build();
    }
}
