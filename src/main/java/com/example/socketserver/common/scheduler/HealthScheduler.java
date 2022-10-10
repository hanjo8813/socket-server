package com.example.socketserver.common.scheduler;

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

    // 25분마다 refresh
    @Scheduled(cron = "0 0/5 * * * *", zone = "Asia/Seoul")
    public void healthCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        log.info("{}", response.getBody());
    }
}
