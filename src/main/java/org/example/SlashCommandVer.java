//package org.example;
//
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
//import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//import net.dv8tion.jda.api.interactions.commands.OptionMapping;
//import net.dv8tion.jda.api.interactions.components.buttons.Button;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Objects;
//
//public class SlashCommandVer extends ListenerAdapter {
//    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
//        if(event.getName().equals("이벤트목록")) {
//            eventCheck.eventCrolling();
//            event.reply("이벤트 목록 조회 중...").setEphemeral(true) // reply or acknowledge
//                    .flatMap(v ->
//                            //eventCheck.eventBuilder
//                            event.getHook().editOriginalEmbeds(eventCheck.eventBuilder.build())
//                    ).queue();
//        } else if(event.getName().equals("추옵")) {
//            OptionMapping optionMapping = event.getOption("무기이름");
//            String option = Objects.requireNonNull(optionMapping).getAsString();
//            Options.weapon(option);
//            System.out.println(Options.weaponException);
//            if(Options.weaponException) {
//                event.reply("무기 추옵 조회").setEphemeral(false)
//                        .flatMap(v ->
//                                event.getChannel().sendMessageEmbeds(Options.embedBuilderWeapon.build())
//                        ).queue();
//            } else {
//                event.reply("오류!! 다시 검색해주세요!").setEphemeral(true)
//                        .flatMap(v ->
//                                event.getHook().editOriginal("검색 내용 : " + option)
//                        ).queue();
//            }
//        } else if(event.getName().equals("커맨드")) {
//            boolean hidden = false;
//            OptionMapping optionMapping = null;
//            if(event.getOption("전부") != null) {
//                optionMapping = event.getOption("전부");
//            } else if((event.getOption("히든") != null)) {
//                hidden = true;
//                optionMapping = event.getOption("히든");
//            }
//            if(optionMapping == null) {
//                event.reply("오류!! 다시 검색해주세요!").setEphemeral(hidden)
//                        .flatMap(v ->
//                                event.getHook().editOriginal("오류!! 다시 검색해주세요!")
//                        ).queue();
//            }
//            String commandName = Objects.requireNonNull(optionMapping).getAsString();
//            fileRead.fileReadCommand(commandName);
//            if(!fileRead.commandValue.equals("Not Found Command")) {
//                event.reply("커맨드 조회").setEphemeral(hidden)
//                        .flatMap(v ->
//                                event.getHook().editOriginal(fileRead.commandValue)
//                        ).queue();
//            } else {
//                event.reply("오류!! 다시 검색해주세요!").setEphemeral(hidden)
//                        .flatMap(v ->
//                                event.getHook().editOriginal("검색 내용 : " + commandName)
//                        ).queue();
//            }
//        } else if(event.getName().equals("농장")) {
//            OptionMapping monsterName = event.getOption("몬스터이름");
//            String option = Objects.requireNonNull(monsterName).getAsString();
//            MapleFarm.mesoKr(option);
//
//            event.reply("Click the button to say hello")
//                    .addActionRow(
//                            Button.primary("Previous", "⏮"), // Button with only a label
//                            Button.primary("Next", "⏭")) // Button with only an emoji
//                    .queue();
//
//        }
//    }
//
//    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
//        EmbedBuilder test = new EmbedBuilder();
//        if (event.getComponentId().equals("Next")) {
//            test.clear();
//            test.setTitle("Test");
//            event.editMessageEmbeds(test.build()).queue();
//
//        }
//
//    }
//}
