package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
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
                    ).queue();
            // Queue both reply and edit
        } else if(event.getName().equals("추옵")) {
            OptionMapping optionMapping = event.getOption("무기이름");
            String option = Objects.requireNonNull(optionMapping).getAsString();
            Options.weapon(option);
            System.out.println(Options.weaponException);
            if(Options.weaponException) {
                event.reply("무기 추옵 조회").setEphemeral(false)
                        .flatMap(v ->
                                event.getChannel().sendMessage(Options.embedBuilderWeapon.build())
                        ).queue();
            } else {
                event.reply("오류!! 다시 검색해주세요!").setEphemeral(true)
                        .flatMap(v ->
                                event.getHook().editOriginal("검색 내용 : " + option)
                        ).queue();
            }
        } else if(event.getName().equals("커맨드")) {
            boolean hidden = false;
            OptionMapping optionMapping = null;
            if(event.getOption("전부") != null) {
                optionMapping = event.getOption("전부");
            } else if((event.getOption("히든") != null)) {
                hidden = true;
                optionMapping = event.getOption("히든");
            }
            if(optionMapping == null) {
                event.reply("오류!! 다시 검색해주세요!").setEphemeral(hidden)
                        .flatMap(v ->
                                event.getHook().editOriginal("오류!! 다시 검색해주세요!")
                        ).queue();
            }
            String commandName = Objects.requireNonNull(optionMapping).getAsString();
            fileRead.fileReadCommand(commandName);
            if(!fileRead.commandValue.equals("Not Found Command")) {
                event.reply("커맨드 조회").setEphemeral(hidden)
                        .flatMap(v ->
                                event.getHook().editOriginal(fileRead.commandValue)
                        ).queue();
            } else {
                event.reply("오류!! 다시 검색해주세요!").setEphemeral(hidden)
                        .flatMap(v ->
                                event.getHook().editOriginal("검색 내용 : " + commandName)
                        ).queue();
            }

        } else if(event.getName().equals("멜론")) {
            OptionMapping optionMapping = event.getOption("n위");
            int index = Integer.parseInt(Objects.requireNonNull(optionMapping).getAsString());
            System.out.println(index);
            Melon_chart.Melon(index);
            event.reply("차트 조회 중...").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessage(Melon_chart.embedBuilde.build())
                    ).queue();
        }
    }
}
