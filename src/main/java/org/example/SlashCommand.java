package org.example;

import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.InteractPage;
import com.github.ygimenez.model.Page;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.Nexon_Probability.probability;

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
                MapleFarm app = new MapleFarm();
                app.printData(option);

                ArrayList<Page> pages = new ArrayList<>();
                EmbedBuilder mesokrBuild  = new EmbedBuilder();
                for (int i = 0; i < MapleFarm.userArrayList.size(); i++) {
                    mesokrBuild.clear();
                    mesokrBuild.setTitle(option+ "에 대한 검색 결과\n" +"Page : " + (i+1) + "/" + MapleFarm.userArrayList.size());
                    mesokrBuild.setColor(Color.MAGENTA);
                    mesokrBuild.addField("농장 이름",MapleFarm.userArrayList.get(i), true);
                    mesokrBuild.addField("유호 기간",MapleFarm.dayArrayList.get(i), true);
                    mesokrBuild.setFooter("https://meso.kr", "https://meso.kr/apple-icon-57x57.png");
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
        } else if(event.getName().equals("정보")) {
            OptionMapping userName = event.getOption("닉네임");
            String userNameStr = Objects.requireNonNull(userName).getAsString();
            UserSearch.Search(userNameStr);

            event.reply("검색 내용").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessageEmbeds(UserSearch.userBuider.build())
                    ).queue();
        } else if(event.getName().equals("무릉")) {
            OptionMapping userName = event.getOption("닉네임");
            String userNameStr = Objects.requireNonNull(userName).getAsString();
            UserSearch.Search(userNameStr);
            event.reply("검색 내용").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessageEmbeds(UserSearch.userBuiderMr.build())
                    ).queue();
        } else if(event.getName().equals("유니온")) {
            OptionMapping userName = event.getOption("닉네임");
            String userNameStr = Objects.requireNonNull(userName).getAsString();
            UserSearch.Search(userNameStr);
            event.reply("검색 내용").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessageEmbeds(UserSearch.userBuiderCoin.build())
                    ).queue();
        } else if(event.getName().equals("help")) {
            event.reply("DM으로 관련 메시지를 보냈습니다.").setEphemeral(true).queue();
            event.getUser().openPrivateChannel().queue((channel) ->
            {
                EmbedBuilder helpBuild  = new EmbedBuilder();
                helpBuild.clear();
                helpBuild.setTitle("봇 사용서");
                helpBuild.setColor(Color.MAGENTA);
                helpBuild.setThumbnail("https://github.com/dudqls5271/discord-java-bot-2022/blob/master/img/bag.png?raw=true");
                helpBuild.setImage("https://github.com/dudqls5271/discord-java-bot-2022/blob/master/img/main.jpg?raw=true");
                helpBuild.addField("/ 명령어 설명", "설명란에 있는 []는 옵션입니다. \n 해당 명령어를 입력하면 자동으로 선택창이 나오게 됩니다. \n 무조건 1개는 선택 해야 합니다.", false);
                helpBuild.addField("1. /이벤트목록", "º 진행 중인 이벤트를 리스트 형식으로 보여준다.", false);
                helpBuild.addField("2. /추옵", "º 파프니르 ~ 에케인셰이드 까지의 무기의 추옵을 확인 가능 하다. \n → 사용법 : /추옵 [무기이름] (내용)", false);
                helpBuild.addField("3. /커맨드", "º 봇에 있는 커맨드를 불러온다.\n  → 사용법 : /커맨드 [전부, 히든] (내용) \n 전부 or 히든 채팅창 공개 여부 이다.\n * 해당 봇은 원래 메이플스토리 디스코드 아부부에 포함 되어있는 봇 이였다. * \nURL : https://docs.google.com/spreadsheets/d/1v3jZMoB1TAa6HUw8F1qUztVRSHo9xGEcHyCi64TnIVk/edit?usp=sharing", false);
                helpBuild.addField("4. /농장", "º 해당 몬스터 보유 농장 및 조합식을 확인 가능하다.\n → 사용법 : /농장 [몬스터이름, 조합식] (내용)", false);
                helpBuild.addField("5. /정보", "º 해당 유저의 MapleGG 기준의 정보를 가지고 온다.\n  → 사용법 : /정보 [닉네임] (내용)", false);
                helpBuild.addField("6. /무릉", "º 해당 유저의 MapleGG 기준의 무릉 정보를 가지고 온다.\n → 사용법 : /무릉 [닉네임] (내용))", false);
                helpBuild.addField("6. /유니온", "º 해당 유저의 MapleGG 기준의 유니온 정보를 가지고 온다.\n 1일 최대 코인 개수 확인 가능\n → 사용법 : /유니온 [닉네임] (내용)", false);
                channel.sendMessageEmbeds(helpBuild.build()).queue();
            });
        } else if(event.getName().equals("도박")) {
            String userSelect = "";
            int countUser = 0;
            List<OptionMapping> select = event.getOptions();
            for (int i = 0; i < select.toArray().length; i++) {
                if(select.get(i).getName().equals("선택")) {
                    userSelect = Objects.requireNonNull(select.get(i).getAsString());
                } else if (select.get(i).getName().equals("횟수")) {
                    countUser = Integer.parseInt(Objects.requireNonNull(select.get(i).getAsString()));
                }
            }
            String userSelectStr = userSelect;
            if(userSelect.equals("")) {
                event.reply("항목을 선택해주세요.").setEphemeral(true).queue();
            } else if (countUser == 0 || countUser < 0) {
                event.reply("정확한 횟수를 입력해주세요.").setEphemeral(true).queue();
            } else {
                if(userSelect.equals("로얄")) {
                    userSelect = "RoyalStyle";
                } else if(userSelect.equals("골드애플")) {
                    userSelect = "GoldApple";
                } else if(userSelect.equals("원더베리")) {
                    userSelect = "WispsWonderBerry";
                }
                Nexon_Probability.Probability(userSelect, countUser);
            }
            event.reply(userSelectStr+" " + countUser+ "개 결과").setEphemeral(false)
                    .flatMap(v ->
                            event.getChannel().sendMessageEmbeds(probability.build())
                    ).queue();
        }
    }
}