package org.example;
import com.github.ygimenez.exception.InvalidHandlerException;
import com.github.ygimenez.method.Pages;
import com.github.ygimenez.model.PaginatorBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
//import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main extends ListenerAdapter  {
    public static void main(String[] args) throws LoginException, InvalidHandlerException {
        // 봇의 토근을 저장한다.
        token token = new token();
        JDA jda = JDABuilder.createDefault(token.tokenStr).build();
        System.out.println("======접속 완료======");

        // / 커멘드1
        jda.addEventListener(new Main());

        jda.getPresence().setActivity(Activity.playing("MapleStory"));

        // 대화 로고
//        jda.addEventListener(new Log4());
        // / 커멘드 실행
        jda.addEventListener(new SlashCommand());
         //1초마다 이벤트 불러오기 + 공지 띄우기
        jda.addEventListener(new EvemtReady());
        // 커멘드
        jda.addEventListener(new fileRead());
        // 공홈 공지
//        jda.addEventListener(new Notice());

        ArrayList<String> test = new ArrayList<>();

        Pages.activate(PaginatorBuilder.createSimplePaginator(jda));
        jda.updateCommands()
                .addCommands(new CommandData("이벤트목록", "지금 진행중인 이벤트 목록 조회"))
                .addCommands(new CommandData("추옵", "무기 추옵 검색")
                        .addOption(OptionType.STRING ,"무기이름", "무기이름")
                )
                .addCommands(new CommandData("커맨드", "커맨드 검색")
                        .addOption(OptionType.STRING ,"전부", "전부")
                        .addOption(OptionType.STRING, "히든", "히든")
                )
                .addCommands(new CommandData("농장", "농장 조합 및 보유 농장 검색")
                        .addOption(OptionType.STRING ,"몬스터이름", "몬스터이름")
                        .addOption(OptionType.STRING ,"조합식", "조합식")
                )
                .addCommands(new CommandData("정보", "메이플 인게임 정보 검색")
                        .addOption(OptionType.STRING ,"닉네임", "닉네임")
                )
                .addCommands(new CommandData("무릉", "해당 유저의 최고 무릉")
                        .addOption(OptionType.STRING ,"닉네임", "닉네임")
                )
                .addCommands(new CommandData("유니온", "해당 유저의 유니온 상세")
                        .addOption(OptionType.STRING ,"닉네임", "닉네임")
                )
                .addCommands(new CommandData("help", "도움말")
                )
                .addCommands(new CommandData("도박", "메이플 로얄, 골드애플, 원더베리등 시뮬레이터 입니다.")
                        .addOptions(
                                new OptionData(OptionType.STRING, "선택", "다음 항목을 선택 해주세요")
                                        .addChoice("로얄", "로얄")
                                        .addChoice("골드애플", "골드애플")
                                        .addChoice("원더베리", "원더베리"),
                                new OptionData(OptionType.INTEGER, "횟수", "횟수 설정(1 ~ 11)")
                        )
                )
                .queue();
    }

//        jda.updateCommands()
//                .addCommands(Commands.slash("이벤트목록", "지금 진행중인 이벤트 목록 조회"))
//                .addCommands(Commands.slash("추옵", "무기 추옵 검색")
//                        .addOption(OptionType.STRING ,"무기이름", "무기이름")
//                )
//                .addCommands(Commands.slash("커맨드", "커맨드 검색")
//                        .addOption(OptionType.STRING ,"전부", "전부")
//                        .addOption(OptionType.STRING, "히든", "히든")
//                )
//                .addCommands(Commands.slash("농장", "농장 조합 및 보유 농장 검색")
//                        .addOption(OptionType.STRING ,"몬스터이름", "몬스터이름")
//                        .addOption(OptionType.STRING ,"조합식", "조합식")
//                )
//                .queue();
//    }
}