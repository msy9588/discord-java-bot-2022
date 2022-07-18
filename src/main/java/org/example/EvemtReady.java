package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static org.example.eventCheck.sunDayImg;

public class EvemtReady extends ListenerAdapter {
    static boolean sunDayMapleBooleon = true;
    public void onReady(@NotNull ReadyEvent event) {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("==========이벤트 확인 로고==========");
                eventCheck.eventCrolling();
                EmbedBuilder eventD_Day = new EmbedBuilder();
                EmbedBuilder sunDayMaple = new EmbedBuilder();
                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
                Date date = new Date();
                SimpleDateFormat simpl = new SimpleDateFormat("hh:mm:a");
                String s = simpl.format(date);
                for (int i = 0; i < eventCheck.eventNameList.size(); i++) {
                    if(eventCheck.d_dayList.get(i).equals("1")) {
                        if(!eventCheck.eventNameList.get(i).equals("썬데이 메이플")) {
                            if(s.equals("06:18:오후")) {
                                eventD_Day.setColor(Color.red);
                                eventD_Day.setTitle("이벤트 기간이 1일밖에 남지 않았습니다!!! ");
                                eventD_Day.appendDescription(i+1+". "+"["+ eventCheck.eventNameList.get(i) +"]("+ eventCheck.eventUrlList.get(i) +") \n" + eventCheck.strNowDateList.get(i)+" ("+ eventCheck.eventDataList.get(i) + ") \n \n");
                                JDA jda = event.getJDA();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(eventD_Day.build()).queue();

                            }
                        }
                    }
                    sunDayMaple.setColor(Color.MAGENTA);
                    JDA jda = event.getJDA();
                    if(s.equals("12:00:오전")) {
                        SimpleDateFormat dateS_SunDay = new SimpleDateFormat("MM월 dd일");
                        String sun = dateS_SunDay.format(date);
                        System.out.println(sun);
                        // 나중에 지워야 함 ===========================================================>
                        if (eventCheck.eventNameList.get(i).contains("스페셜 썬데이 메이플")) {
                            if(sun.equals("7월 31일") || sun.equals("8월 14일") || sun.equals("8월 28일")) {
                                sunDayMaple.clear();
                                sunDayMaple.setTitle("스페셜 썬데이 메이플");
                                switch (sun) {
                                    case "7월 31일":
                                        sunDayMaple.addField("샤이닝 스타포스 타임", "스타포스 강화를 위한 \n 절호의 기회! 5/10/15 100%, \n 강화비용 30% 할인", true);
                                        break;
                                    case "8월 14일":
                                        sunDayMaple.addField("레이디 블레어의 코디 타임", "스페셜 썬데이 메이플 \n <코디 상자>를 통해 마스터 라벨 \n 풀패키지의 주인공이 되어보세요.", true);
                                        break;
                                    case "8월 28일":
                                        sunDayMaple.addField("로드 블레어의 헤어 타임", "스페셜 썬데이 메이플 \n <헤어 상자>를 통해 마스터 라벨 \n 헤어의 주인공이 되어보세요.", true);
                                        break;
                                }
                                sunDayMaple.setImage(sunDayImg);
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(sunDayMaple.build()).queue();
                            }
                        }
                    }
                    //==============================================>
                    if(sunDayMapleBooleon) {
                        if (eventCheck.eventNameList.get(i).equals("썬데이 메이플")) {
                            sunDayMaple.clear();
                            sunDayMaple.setTitle("썬데이 메이플");
                            sunDayMaple.setImage(sunDayImg);
                            jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                            jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(sunDayMaple.build()).queue();
                            sunDayMapleBooleon = false;
                        }
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 60000L;
        System.out.println(LocalDateTime.now() + " : 갱신 대기중....");
        timer.scheduleAtFixedRate(task, delay, period);

    }
}
