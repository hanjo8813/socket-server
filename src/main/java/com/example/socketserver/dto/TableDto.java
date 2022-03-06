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
        switch (this.fireStatus){
            case ON:
                this.fireStatus = FireStatus.OFF;
                break;
            case OFF:
                this.fireStatus = FireStatus.ON;
                break;
        }
    }
}
