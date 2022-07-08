package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter  {
    public static void main(String[] args) throws LoginException {
        // 봇의 토근을 저장한다.
        token token = new token();
        JDA jda = JDABuilder.createDefault(token.tokenStr).build();
        System.out.println("======접속 완료======");
        // / 커멘드1
        jda.addEventListener(new Main());
        jda.getPresence().setActivity(Activity.playing("MapleStory"));

        // 대화 로고
        jda.addEventListener(new Log4());
        // / 커멘드 실행
        jda.addEventListener(new SlashCommand());
        // 1초마다 이벤트 불러오기 + 공지 띄우기
        jda.addEventListener(new EvemtReady());
        // json 파일 읽기
        jda.addEventListener(new fileRead());

        jda.addEventListener(new Notice());

        jda.updateCommands()
                .addCommands(Commands.slash("이벤트목록", "지금 진행중인 이벤트 목록 조회"))
                .addCommands(Commands.slash("추옵", "무기 추옵 검색")
                        .addOption(OptionType.STRING ,"무기이름", "무기이름")
                )
                .addCommands(Commands.slash("커맨드", "커맨드 검색")
                        .addOption(OptionType.STRING ,"전부", "전부")
                        .addOption(OptionType.STRING, "히든", "히든")
                )
                .addCommands(Commands.slash("농장", "농장 조합 및 보유 농장 검색")
                        .addOption(OptionType.STRING ,"몬스터이름", "몬스터이름")
                        .addOption(OptionType.STRING ,"조합식", "조합식")
                )
                .queue();
    }
}