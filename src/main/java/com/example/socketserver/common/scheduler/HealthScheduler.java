package com.example.socketserver.common.scheduler;

import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HealthScheduler {

    private static final String URL = "https://kkakdduk.herokuapp.com/health";
    private final RestTemplate restTemplate = new RestTemplate();

    // 매일 pm2:00 ~ am2:00 20분마다 실행됨
    @Scheduled(cron = "0 0/20 0-2,14-23 * * *", zone = "Asia/Seoul")
    public void healthCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        log.info("{}", response.getBody());
    }

}
