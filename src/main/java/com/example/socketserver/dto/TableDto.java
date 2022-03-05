package com.example.socketserver.dto;

import lombok.Getter;

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
