package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class SlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(event.getName().equals("이벤트목록")) {
            eventCheck.eventCrolling();
            event.reply("이벤트 목록 조회 중...").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                                    //editOriginalFormat 나만보이게
                                    event.getHook().editOriginal(String.valueOf(eventCheck.sb)) // then edit original
//                        getChannel().sendMessage(org.example.eventCheck.sb)
                    ).queue(); // Queue both reply and edit
        } else if(event.getName().equals("추옵")) {
            OptionMapping optionMapping = event.getOption("무기이름");
            String option = Objects.requireNonNull(optionMapping).getAsString();
            Options.weapon(option);
            System.out.println(Options.option);

            StringBuilder sb = new StringBuilder();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.MAGENTA);
            embedBuilder.addField("무기 이름",option, false);
            embedBuilder.setThumbnail(Options.imgURL);
            System.out.println(option);
            for (int i = Options.option.size()-1; i > 0; i--) {
                sb.append(Options.option.size() - i).append("추 : ").append(Options.option.get(i)).append("\n");
            }
            embedBuilder.addField("추옵", String.valueOf(sb), false);

            event.reply("무기 추옵 조회 중...").setEphemeral(false) // reply or acknowledge
                    .flatMap(v ->
                                    //editOriginalFormat 나만보이게
//                                    event.getHook().editOriginal(String.valueOf("asdad")) // then edit original
                            event.getChannel().sendMessage(embedBuilder.build())
                    ).queue(); // Queue both reply and edit
        }
    }
}
