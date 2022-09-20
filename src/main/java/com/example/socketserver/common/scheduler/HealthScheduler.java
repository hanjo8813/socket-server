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

    private static final String NAVER_URL = "http://api.booking.naver.com/v3.0/businesses/562202/biz-items/4030151/daily-schedules?lang=ko&endDateTime=2022-11-05T00:00:00&isRestaurant=false&startDateTime=2022-09-25T00:00:00";

    @Value("${SLACK_URL}")
    private String BOT;

    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    public void temp() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(NAVER_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode target1 = root.path("2022-10-08");
        int stock1 = target1.path("stock").asInt();
        int bookingCount1 = target1.path("bookingCount").asInt();
        log.info("예약 정보 확인 / stock1 : {} / bookingCount1 : {}", stock1, bookingCount1);

        JsonNode target2 = root.path("2022-10-09");
        int stock2 = target2.path("stock").asInt();
        int bookingCount2 = target2.path("bookingCount").asInt();
        log.info("예약 정보 확인 / stock2 : {} / bookingCount2 : {}", stock2, bookingCount2);

        Map<String,Object> request = new HashMap<>();
        if(stock1 > bookingCount1){
            request.put("text", "토요일 예약뜸~!~ https://m.booking.naver.com/booking/3/bizes/562202/items/4030151 여기서 빨리 예약 ㄱㄱ");
            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
            restTemplate.exchange(BOT, HttpMethod.POST, entity, String.class);
        }
        if(stock2 > bookingCount2){
            request.put("text", "일요일 예약뜸~!~ https://m.booking.naver.com/booking/3/bizes/562202/items/4030151 여기서 빨리 예약 ㄱㄱ");
            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
            restTemplate.exchange(BOT, HttpMethod.POST, entity, String.class);
        }
    }

}
