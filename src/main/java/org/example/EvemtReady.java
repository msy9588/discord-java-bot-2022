package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EvemtReady extends ListenerAdapter {
    public void onReady(@NotNull ReadyEvent event) {
        TimerTask task = new TimerTask() {
            public void run() {
                eventCheck.eventCrolling();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < eventCheck.eventNameList.size(); i++) {
                    if(eventCheck.d_dayList.get(i).equals("1")) {
                        if(!eventCheck.eventNameList.get(i).equals("썬데이 메이플")) {
                            sb.append("\r> ```ansi\n " + "> [이벤트] \u001B[1;5m").append(eventCheck.eventNameList.get(i)).append("\u001B[0m / \u001B[1;35m종료일 : ").append(eventCheck.strNowDateList.get(i)).append("(").append(eventCheck.eventDataList.get(i)).append(")\u001B[0m ```").append(eventCheck.eventUrlList.get(i));
                        } else {
                            System.out.println("1일 남은 이벤트 없음");
                        }
                    }
                }
                Date date = new Date();
                SimpleDateFormat simpl = new SimpleDateFormat("hh:a");
                String s = simpl.format(date);
                System.out.println(s);
                if(s.equals("12:오전")) {
                    System.out.println(sb);
                    JDA jda = event.getJDA();
                    if(!String.valueOf(sb).equals("") && !String.valueOf(sb).equals(" ") && String.valueOf(sb) != null) {
                        sb.insert(0, "> ```ansi\n" +
                                "> \u001B[1;35m이벤트 기간이 1일밖에 남지 않았습니다!!!\u001B[0m ```");
                      jda.getTextChannelsByName("아부부-봇-공지", true).get(0).sendMessage("@everyone").queue();
                      jda.getTextChannelsByName("아부부-봇-공지", true).get(0).sendMessage(sb).queue();
//                        jda.getTextChannelsByName("테스트", true).get(0).sendMessage("@everyone").queue();
//                        jda.getTextChannelsByName("테스트", true).get(0).sendMessage(sb).queue();
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 3600000L;
        System.out.println(LocalDateTime.now() + " : 갱신 대기중....");
        timer.scheduleAtFixedRate(task, delay, period);

    }
}
