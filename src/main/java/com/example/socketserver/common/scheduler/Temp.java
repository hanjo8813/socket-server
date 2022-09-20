//package com.example.socketserver.common.scheduler;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.client.RestTemplate;
//
//public class Temp {
//
//    //    private static final String CAMP_URL_B = "https://baekdo.ticketplay.zone/portal/realtime/productSelect?room_area_no=11&stay_cnt=1&check_in=20221008&check_out=20221009";
//    private static final String CAMP_URL_B = "https://baekdo.ticketplay.zone/portal/realtime/productSelect?room_area_no=11&stay_cnt=1&check_in=20221003&check_out=20221004";
//    private static final String CAMP_URL_C = "https://baekdo.ticketplay.zone/portal/realtime/productSelect?room_area_no=12&stay_cnt=1&check_in=20221008&check_out=20221009";
//
//    public static void main(String[] args) throws IOException {
//
//        StringBuilder sb = new StringBuilder();
//
//        boolean siteB = searchCamp(CAMP_URL_C, sb);
//        boolean siteC = searchCamp(CAMP_URL_C, sb);
//
//        if(!siteB && !siteC)
//            return;
//
//        Map<String,Object> request = new HashMap<>();
//        request.put("text",sb.toString());
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
//        restTemplate.exchange("https://hooks.slack.com/services/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", HttpMethod.POST, entity, String.class);
//    }
//
//    public static boolean searchCamp(String url, StringBuilder sb) throws IOException {
//        Connection conn = Jsoup.connect(url);
//        Document document = conn.get();
//        Elements campSites = document.select("div.right_box ul li label");
//
//        if(campSites.size() == 0)
//            return false;
//
//        sb.append("캠핑장 떴다! 예약 -> ").append(url).append("\n");
//
//        for(Element site : campSites){
//            String target = site.text().split(" ")[0];
//            sb.append(target).append("\n");
//        }
//
//        sb.append("\n");
//        return true;
//    }
//
//}
