package com.example.socketserver.dto.request;

import com.example.socketserver.dto.TableDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChangeFireStatusRequest {

    private Long targetId;
}
