package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Notice extends EvemtReady{
    public void onReady(@NotNull ReadyEvent event) {
        HashMap<String, String> map = new HashMap<>();
            TimerTask task = new TimerTask() {
                public void run() {
                    try {
                        String url = "https://maplestory.nexon.com/News/Update";
                        Document doc = Jsoup.connect(url).get();
                        int noticeSize = doc.select("div[class=\"update_board\"]").select("ul").select("li").size();

                        EmbedBuilder notice = new EmbedBuilder();

                        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
                        Date date = new Date();
                        SimpleDateFormat simpl = new SimpleDateFormat("yyyy.MM.dd");
                        String s = simpl.format(date);

                        String noticeGet0 = doc.select("div[class=\"update_board\"]").select("ul").select("li").get(0).select("span").text();
                        String noticeURL = doc.select("div[class=\"update_board\"]").select("ul").select("li").get(0).select("a").attr("href");
                        String noticeDateGet0 = doc.select("div[class=\"update_board\"]").select("ul").select("li").get(0).select("div").text();

                        map.put(noticeGet0, noticeDateGet0);

//                        System.out.println(noticeURL);
//                        System.out.println(noticeGet0);
//                        System.out.println(noticeDateGet0);
//                        System.out.println(map.size());
//                        System.out.println(s);

                        if(map.size() == 2) {
                            if(noticeDateGet0.equals(s)) {
                                notice.setColor(Color.MAGENTA);
                                notice.setTitle("메이플 뉴스 - 업데이트");
                                notice.appendDescription(noticeGet0 + " - " + noticeDateGet0+"\n");
                                notice.appendDescription("[바로가기]" + "(https://maplestory.nexon.com/"+noticeURL+")");
                                JDA jda = event.getJDA();
//                                jda.getTextChannelsByName("테스트", true).get(0).sendMessage("@everyone").queue();
//                                jda.getTextChannelsByName("테스트", true).get(0).sendMessageEmbeds(notice.build()).queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(notice.build()).queue();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Timer timer = new Timer("Timer");
            long delay = 1000L;
            long period = 60000L;
            System.out.println(LocalDateTime.now() + " : 업데이트 목록 갱신 대기중....");
            timer.scheduleAtFixedRate(task, delay, period);
    }
}
