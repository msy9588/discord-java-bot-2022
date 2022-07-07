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

public class EvemtReady extends ListenerAdapter {
    public void onReady(@NotNull ReadyEvent event) {
        TimerTask task = new TimerTask() {
            public void run() {
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
                            if(s.equals("12:01:오전")) {
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
                    if(eventCheck.eventNameList.get(i).equals("썬데이 메이플")) {
                        sunDayMaple.setColor(Color.MAGENTA);
                        sunDayMaple.setTitle("썬데이 메이플");
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
