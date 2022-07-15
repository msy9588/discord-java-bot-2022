package org.example;

import java.awt.*;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class eventCheck extends Main {
    public static EmbedBuilder eventBuilder = new EmbedBuilder();
    static ArrayList<String> eventNameList = new ArrayList<>();
    static ArrayList<String> eventUrlList = new ArrayList<>();
    static ArrayList<String> strNowDateList = new ArrayList<>();
    static ArrayList<String> eventDataList = new ArrayList<>();
    static ArrayList<String> d_dayList = new ArrayList<>();

    static String sunDayImg = "";

    public static void eventCrolling() {
        sunDayImg = "";
        eventNameList.clear();
        eventUrlList.clear();
        strNowDateList.clear();
        eventDataList.clear();
        try {
            String url = "https://maple.gg/";
            Document doc = Jsoup.connect(url).get();
            int eventCount = doc.select("div[class=\"d-inline-block\"]").size();

            for (int i = 0; i < eventCount; i++) {
                // 이벤트 이름 및 날짜
                String text_event = doc.select("div[class=\"d-inline-block\"]").get(i).select("div").get(0).text();
                String date_event = doc.select("small[class=\"text-secondary\"]").get(i).text();
                int d_day = Integer.parseInt(date_event.replace("일 남음", ""));

                // 이벤트 URL
                Element eventURL = doc.select("div[class=\"d-inline-block\"]").get(i).select("a").get(0);
                String eventURL2 = eventURL.attr("href");

                Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.DATE, d_day-1); // 일 계산
                Date date = new Date(cal1.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                String strNowDate = simpleDateFormat.format(date);

                eventNameList.add(text_event);
                eventUrlList.add(eventURL2);
                strNowDateList.add(strNowDate);
                eventDataList.add(date_event);
                d_dayList.add(String.valueOf(d_day));
            }

            for (int i = 0; i < eventNameList.size(); i++) {
                if(eventNameList.get(i).contains("스페셜 썬데이 메이플")){
                    sunDayImg = doc.select("div[class=\"d-inline-block\"]").get(i).select("a").select("img").attr("src").replace("https", "http");
                    System.out.println(sunDayImg);
                    System.out.println("스페셜 썬데이 메이플");
                } else if(eventNameList.get(i).contains("썬데이 메이플")) {
                    sunDayImg = doc.select("div[class=\"d-inline-block\"]").get(i).select("a").select("img").attr("src").replace("https", "http");
                }
            }
            eventBuilder.clear();
            eventBuilder.setColor(Color.MAGENTA);
            eventBuilder.setTitle("진행중인 이벤트 목록");

            for (int i = 0; i < eventNameList.size(); i++) {
                eventBuilder.appendDescription(i+1+". "+"["+ eventNameList.get(i) +"]("+ eventUrlList.get(i) +") \n" + strNowDateList.get(i)+" ("+ eventDataList.get(i) + ") \n \n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
