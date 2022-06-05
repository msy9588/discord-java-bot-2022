package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter  {
    public static void main(String[] args) throws LoginException {
        // 봇의 토근을 저장한다.
        token token = new token();
        JDA jda = JDABuilder.createDefault(token.tokenStr).build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new Main());
    }

    @Override
    // MessageReceivedEvent 메세지를 읽어오는 event 추정
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        }
        else
        {
            // event.getGuild().getName() => 채널 이름
            // event.getTextChannel().getName() => 채널 대화창 이름
            // event.getMember().getEffectiveName() => 사람 닉네임
            // event.getMessage().getContentDisplay() => 내용
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getTextChannel().getName() ,event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }

        if(event.getMessage().getContentDisplay().equals("문브")) {
            event.getChannel().sendMessage("https://media.discordapp.net/attachments/871699330011131935/872379092845076490/ced45d8ac51512cc.png?width=662&height=346").queue();
        }
    }
}