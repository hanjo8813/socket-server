package com.example.socketserver.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChangeStatusRequest {

    private Long targetId;
    private List<TableDto> tables;
}
