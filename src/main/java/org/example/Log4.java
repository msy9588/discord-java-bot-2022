package org.example;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Log4 extends ListenerAdapter {

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
        }
    }
}
