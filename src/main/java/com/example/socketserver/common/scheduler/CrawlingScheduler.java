package com.example.socketserver.common.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CrawlingScheduler {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${SLACK_URL}")
    private String BOT;

//    private static final String NAVER_URL = "http://api.booking.naver.com/v3.0/businesses/562202/biz-items/4030151/daily-schedules?lang=ko&endDateTime=2022-11-05T00:00:00&isRestaurant=false&startDateTime=2022-09-25T00:00:00";
//
//    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
//    public void naver() throws JsonProcessingException {
//        log.info("산너머 스케줄러 실행");
//
//        ResponseEntity<String> response = restTemplate.getForEntity(NAVER_URL, String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());
//
//        JsonNode target1 = root.path("2022-10-08");
//        int stock1 = target1.path("stock").asInt();
//        int bookingCount1 = target1.path("bookingCount").asInt();
//        log.info("예약 정보 확인 / stock1 : {} / bookingCount1 : {}", stock1, bookingCount1);
//
//        JsonNode target2 = root.path("2022-10-09");
//        int stock2 = target2.path("stock").asInt();
//        int bookingCount2 = target2.path("bookingCount").asInt();
//        log.info("예약 정보 확인 / stock2 : {} / bookingCount2 : {}", stock2, bookingCount2);
//
//        Map<String,Object> request = new HashMap<>();
//        if(stock1 > bookingCount1){
//            request.put("text", "산너머(토요일) 예약뜸~!~ https://m.booking.naver.com/booking/3/bizes/562202/items/4030151 여기서 빨리 예약 ㄱㄱ");
//            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
//            restTemplate.exchange(BOT, HttpMethod.POST, entity, String.class);
//        }
//        if(stock2 > bookingCount2){
//            request.put("text", "산너머(일요일) 예약뜸~!~ https://m.booking.naver.com/booking/3/bizes/562202/items/4030151 여기서 빨리 예약 ㄱㄱ");
//            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
//            restTemplate.exchange(BOT, HttpMethod.POST, entity, String.class);
//        }
//    }

    private static final String CAMP_URL_B = "https://baekdo.ticketplay.zone/portal/realtime/productSelect?room_area_no=11&stay_cnt=1&check_in=20221008&check_out=20221009";
    private static final String CAMP_URL_C = "https://baekdo.ticketplay.zone/portal/realtime/productSelect?room_area_no=12&stay_cnt=1&check_in=20221008&check_out=20221009";

    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    public void camp() throws IOException {
        log.info("백도 스케줄러 실행");
        StringBuilder sb = new StringBuilder();

        boolean siteB = searchCamp("B", CAMP_URL_B, sb);
//        boolean siteC = searchCamp("C", CAMP_URL_C, sb);

        if (!siteB) {
            return;
        }

        Map<String, Object> request = new HashMap<>();
        request.put("text", sb.toString());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);
        restTemplate.exchange(BOT, HttpMethod.POST, entity, String.class);
    }

    public boolean searchCamp(String type, String url, StringBuilder sb) throws IOException {
        Connection conn = Jsoup.connect(url);
        Document document = conn.get();
        Elements campSites = document.select("div.right_box ul li label");

        log.info("{}-캠핑장 예약 정보 확인 / 사이트 수 : {}", type, campSites.size());

        if (campSites.size() == 0) {
            return false;
        }

        sb.append(type).append("-캠핑장 떴다! 예약 -> ").append(url).append("\n");

        for (Element site : campSites) {
            String target = site.text().split(" ")[0];

            if (target.equals("B캠핑장-7") ||
                target.equals("B캠핑장-5") ||
                target.equals("B캠핑장-4") ||
                target.equals("B캠핑장-2") ||
                target.equals("B캠핑장-1")
            ) {
                sb.append(target).append("\n");
            }
            else{
                return false;
            }
        }

        sb.append("\n");
        return true;
    }
}
