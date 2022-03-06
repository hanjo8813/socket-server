package com.example.socketserver.dto.response;

import com.example.socketserver.dto.TableDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TableResponse {

    private List<TableDto> tables;

    public static TableResponse of(List<TableDto> tables) {
        return TableResponse.builder()
            .tables(tables)
            .build();
    }
}
