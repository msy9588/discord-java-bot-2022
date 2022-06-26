package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.JDAImpl;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Main extends ListenerAdapter  {
    public static void main(String[] args) throws LoginException {
        // 봇의 토근을 저장한다.
        token token = new token();
        JDA jda = JDABuilder.createDefault(token.tokenStr).build();
        jda.addEventListener(new Main());


//        JDA jda = JDABuilder.createLight(token.tokenStr, Collections.emptyList())
//                .addEventListeners(new Main())
//                .setActivity(Activity.playing("명령어 만드는 중"))
//                .build();

        jda.upsertCommand("이벤트목록", "지금 진행중인 이벤트 목록 조회").queue(); // This can take up to 1 hour to show up in the client
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (!event.getName().equals("이벤트목록")) return; // make sure we handle the right command
        event.reply("이벤트 목록 조회중").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        //editOriginalFormat 나만보이게
                        event.getHook().editOriginalFormat("ㅁㄴㅇㅁㄴㅇㅁㄴ") // then edit original
                ).queue(); // Queue both reply and edit

    }

    public void onReady(@NotNull ReadyEvent event) {
        try {
            System.out.println("접속");
            String url = "https://maple.gg/";
            Document doc = Jsoup.connect(url).get();

            int eventCount = doc.select("div[class=\"d-inline-block\"]").size();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < eventCount; i++) {
                // 이벤트 이름 및 날짜
                String text_event = doc.select("div[class=\"d-inline-block\"]").get(i).select("div").get(0).text();
                String date_event = doc.select("small[class=\"text-secondary\"]").get(i).text();
                int d_day = Integer.parseInt(date_event.replace("일 남음", ""));

                // 이벤트 URL
                Element eventURL = doc.select("div[class=\"d-inline-block\"]").get(i).select("a").get(0);
                String eventURL2 = eventURL.attr("href");

                Calendar cal1 = Calendar.getInstance();
                cal1.add(Calendar.DATE, d_day); // 일 계산
                Date date = new Date(cal1.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                String strNowDate = simpleDateFormat.format(date);

                if (d_day == 1) {
                    if(text_event.equals("썬데이 메이플")) {
                        sb.append("[이벤트] \u001B[1;5m").append(text_event).append("\u001B[0m / \u001B[1;35m종료일 : ").append(strNowDate).append("(").append(date_event).append(")\u001B[0m").append("\n").append("\u001B[1;36m").append(eventURL2).append("\u001B[0m").append("\n \n");
                    }
                }
                if(text_event.equals("썬데이 메이플")) {
                    System.out.println("썬데이");
                }
            }
            TimerTask task = new TimerTask() {
                public void run() {
                    Date date = new Date();
                    SimpleDateFormat simpl = new SimpleDateFormat("hh");
                    String s = simpl.format(date);
                    if(s.equals("00")) {
                        JDA jda = event.getJDA();
                        jda.getTextChannelsByName("공지", true).get(0).sendMessage("@everyone").queue();
                        jda.getTextChannelsByName("공지", true).get(0).sendMessage("```ansi\n \n \u001B[1;35m이벤트 기간이 얼마 남지 않았습니다 !!!\u001B[0m \n \n"+sb+"```").queue();
                    }
                }
            };
            Timer timer = new Timer("Timer");
            long delay = 1000L;
            long period = 600000L;
            System.out.println(LocalDateTime.now() + " : 갱신 대기중....");
            timer.scheduleAtFixedRate(task, delay, period);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE))  {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        } else {
            // event.getGuild().getName() => 채널 이름
            // event.getTextChannel().getName() => 채널 대화창 이름
            // event.getMember().getEffectiveName() => 사람 닉네임
            // event.getMessage().getContentDisplay() => 내용
            System.out.printf("[%s][%s] %s [%s] : %s\n", event.getGuild().getName(), event.getMember().getRoles(),
                    event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
            String message = "test";

            for (TextChannel ch : event.getGuild().getTextChannelsByName("테스트", true)) {
                System.out.println(ch);
            }
        }

        fileRead fileRead = new fileRead();
        fileRead.command(event);

        eventCheck eventCheck = new eventCheck();

        if(event.getMessage().getContentDisplay().equals("이벤트 목록") || event.getMessage().getContentDisplay().equals("이벤트목록") || event.getMessage().getContentDisplay().equals("ㅇㅂㅌㅁㄹ")  ) {
            System.out.println("이벤트");
            eventCheck.eventList(event);
        }
    }
}