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
//                                jda.getTextChannelsByName("테스트", true).get(0).sendMessage("@everyone").queue();
//                                jda.getTextChannelsByName("테스트", true).get(0).sendMessageEmbeds(eventD_Day.build()).queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(eventD_Day.build()).queue();

                            }
                        }
                    }
                    sunDayMaple.setColor(Color.MAGENTA);
                    JDA jda = event.getJDA();
                    if(s.equals("06:18:오후")) {
                        SimpleDateFormat day = new SimpleDateFormat("DD월 MM일");
                        String s2 = simpl.format(day);
                        if (eventCheck.eventNameList.get(i).contains("스페셜 썬데이 메이플")) {
                            if(s2.equals("07월 17일") || s2.equals("07월 31일") || s2.equals("08월 14일") || s2.equals("08월 28일") ) {
                                sunDayMaple.setTitle("스페셜 썬데이 메이플");
                                sunDayMaple.setImage(sunDayImg);
                                if(s2.equals("07월 17일") ) {

                                }

                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                                jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(sunDayMaple.build()).queue();
                            }

                        } else if (eventCheck.eventNameList.get(i).contains("썬데이 메이플")) {
                            sunDayMaple.setTitle("썬데이 메이플");
                            sunDayMaple.setImage(sunDayImg);
                            jda.getTextChannelsByName("봇-공지", true).get(0).sendMessage("@everyone").queue();
                            jda.getTextChannelsByName("봇-공지", true).get(0).sendMessageEmbeds(sunDayMaple.build()).queue();
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
