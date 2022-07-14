package org.example;

import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.ButtonWrapper;
import com.github.ygimenez.model.InteractPage;
import com.github.ygimenez.model.Page;
import com.github.ygimenez.model.ThrowingConsumer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SlashCommand extends ListenerAdapter {
    public void onSlashCommand(SlashCommandEvent event) {
        if(event.getName().equals("이벤트목록")) {
            eventCheck.eventCrolling();
            event.reply("이벤트 목록 조회 중...").setEphemeral(true) // reply or acknowledge
                    .flatMap(v ->
                            //eventCheck.eventBuilder
                            event.getHook().editOriginalEmbeds(eventCheck.eventBuilder.build())
                    ).queue();
        } else if(event.getName().equals("추옵")) {
            OptionMapping optionMapping = event.getOption("무기이름");
            String option = Objects.requireNonNull(optionMapping).getAsString();
            Options.weapon(option);
            System.out.println(Options.weaponException);
            if(Options.weaponException) {
                event.reply("무기 추옵 조회").setEphemeral(false)
                        .flatMap(v ->
                                event.getChannel().sendMessageEmbeds(Options.embedBuilderWeapon.build())
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
        } else if(event.getName().equals("농장")) {
            if(event.getOption("몬스터이름")!= null) {
                OptionMapping monsterName = event.getOption("몬스터이름");
                String option = Objects.requireNonNull(monsterName).getAsString();
                MapleFarm.mesoKr(option);

                ArrayList<Page> pages = new ArrayList<>();
                EmbedBuilder mesokrBuild  = new EmbedBuilder();
                for (int i = 0; i < MapleFarm.userArrayList.size(); i++) {
                    mesokrBuild.clear();
                    mesokrBuild.setTitle(option+ "에 대한 검색 결과\n" +"Page : " + (i+1) + "/" + MapleFarm.userArrayList.size());
                    mesokrBuild.setColor(Color.MAGENTA);
                    mesokrBuild.addField("농장 이름",MapleFarm.userArrayList.get(i), true);
                    mesokrBuild.addField("마리수",MapleFarm.numList.get(i), true);
                    mesokrBuild.addField("유호 기간",MapleFarm.dayArrayList.get(i), true);
                    mesokrBuild.setFooter("https://meso.kr");
                    pages.add(new InteractPage(mesokrBuild.build()));
                }

                event.reply("농장 목록 조회 중...").setEphemeral(false).queue();
                event.getChannel().sendMessageEmbeds((MessageEmbed) pages.get(0).getContent()).queue(success -> {
                    Pages.paginate(success, pages, /* Use buttons? */ true);
                });

            }
            if(event.getOption("조합식") != null) {
                OptionMapping monsterName = event.getOption("조합식");
                String option = Objects.requireNonNull(monsterName).getAsString();
                MapleFarm.wachan(option);

                event.reply("검색 내용").setEphemeral(false)
                        .flatMap(v ->
                                event.getChannel().sendMessageEmbeds(MapleFarm.wachanBuild.build())
                        ).queue();
            }
        } else if(event.getName().equals("유저검색")) {
            OptionMapping userName = event.getOption("닉네임");
            String userNameStr = Objects.requireNonNull(userName).getAsString();
            UserSearch.Search(userNameStr);

            event.reply("검색 내용").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessageEmbeds(UserSearch.userBuider.build())
                    ).queue();
        }
    }
}