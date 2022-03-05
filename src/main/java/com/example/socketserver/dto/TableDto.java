package com.example.socketserver.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TableDto {

    private Long id;
    private FireStatus fireStatus;

    public void changeFireStatus() {
        if (this.fireStatus == FireStatus.ON) {
            this.fireStatus = FireStatus.OFF;
        } else {
            this.fireStatus = FireStatus.ON;
        }
    }
}
