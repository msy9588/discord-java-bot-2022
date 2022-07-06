package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Notice {
    public static void main(String[] args) {
        try {
            String url = "https://maplestory.nexon.com/News/Notice";
            Document doc = Jsoup.connect(url).get();
            int noticeSize = doc.select("div[class=\"news_board\"]").select("ul").select("li").size();
            System.out.println(noticeSize);


            TimerTask task = new TimerTask() {
                public void run() {

                }

            };
            Timer timer = new Timer("Timer");
            long delay = 1000L;
            long period = 60000L;
            System.out.println(LocalDateTime.now() + " : 갱신 대기중....");
            timer.scheduleAtFixedRate(task, delay, period);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
