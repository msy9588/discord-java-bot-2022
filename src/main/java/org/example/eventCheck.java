package org.example;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class eventCheck extends Main {
    public void eventList(@NotNull MessageReceivedEvent event) {
        try {
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
                if(!text_event.equals("썬데이 메이플")) {
                    sb.append("```ansi\n " + "> [이벤트] \u001B[1;5m").append(text_event).append("\u001B[0m / \u001B[1;35m종료일 : ").append(strNowDate).append("(").append(date_event).append(")\u001B[0m ```").append("<").append(eventURL2).append(">");
                }
            }
            if(event.isFromType(ChannelType.PRIVATE)) {
                event.getChannel().sendMessage( "> " + sb).queue();
            } else {
                //관리자만 사용 할 수 있게 변경
                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("> " + sb)).queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
