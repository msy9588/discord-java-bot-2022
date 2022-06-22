package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.annotation.Documented;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class eventCheck {
//    public void eventList(MessageReceivedEvent event) {
//        try {
//            String url = "https://maple.gg/";
//
//            Document doc = Jsoup.connect(url).get();
//
//            int eventCount = doc.select("div[class=\"d-inline-block\"]").size();
//            System.out.println(eventCount);
//
//            for (int i = 0; i < eventCount; i++) {
//                Element event1_img = doc.select("div[class=\"d-inline-block\"]").get(i).select("img").get(0);
//                String text_event_1 = doc.select("div[class=\"d-inline-block\"]").get(i).select("div").get(0).text();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public static void main(String[] args) {
        try {
            String url = "https://maple.gg/";

            Document doc = Jsoup.connect(url).get();

            int eventCount = doc.select("div[class=\"d-inline-block\"]").size();
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.DATE, 6); // 일 계산
            Date date = new Date(cal1.getTimeInMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            String strNowDate = simpleDateFormat.format(date);
            System.out.println("현재시간 : " + new Date());
            System.out.println("연산시간 : " + strNowDate);

            String day = null;
            for (int i = 0; i < eventCount; i++) {
                String text_event = doc.select("div[class=\"d-inline-block\"]").get(i).select("div").get(0).text();
                String date_event = doc.select("small[class=\"text-secondary\"]").get(i).text();
                int d_day = Integer.parseInt(date_event.replace("일 남음", ""));

                String strNowDate = simpleDateFormat.format(date);
                System.out.println("현재시간 : " + new Date());
                System.out.println("연산시간 : " + strNowDate);

                System.out.println("[이벤트]" + text_event + " / ");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
