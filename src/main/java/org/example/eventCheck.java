package org.example;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class eventCheck extends Main {
    static StringBuilder sb = new StringBuilder();

    static ArrayList<String> eventNameList = new ArrayList<>();
    static ArrayList<String> eventUrlList = new ArrayList<>();
    static ArrayList<String> strNowDateList = new ArrayList<>();
    static ArrayList<String> eventDataList = new ArrayList<>();
    static ArrayList<String> d_dayList = new ArrayList<>();

    public static void eventCrolling() {
        eventNameList.clear();
        eventUrlList.clear();
        strNowDateList.clear();
        eventDataList.clear();
        try {
            sb.setLength(0);

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
                String eventURL2 = "<"+eventURL.attr("href")+">";

                Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.DATE, d_day); // 일 계산
                Date date = new Date(cal1.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                String strNowDate = simpleDateFormat.format(date);

                eventNameList.add(text_event);
                eventUrlList.add(eventURL2);
                strNowDateList.add(strNowDate);
                eventDataList.add(date_event);
                d_dayList.add(String.valueOf(d_day));

                if(!text_event.equals("썬데이 메이플")) {
                    sb.append("\n> ```ansi\n " + "> [이벤트] \u001B[1;5m").append(text_event).append("\u001B[0m / \u001B[1;35m종료일 : ").append(strNowDate).append("(").append(date_event).append(")\u001B[0m ```").append(eventURL2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
