package com.example.socketserver.common.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
