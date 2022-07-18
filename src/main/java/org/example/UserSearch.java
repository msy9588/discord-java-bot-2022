package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.Objects;

public class UserSearch {
    static EmbedBuilder userBuider = new EmbedBuilder();
    static EmbedBuilder userBuiderMr = new EmbedBuilder();
    static EmbedBuilder userBuiderCoin = new EmbedBuilder();
    public static void main(String[] args) {
        Search("진동아델");
    }
    public static void Search(String userName) {
        try {

            String url = "https://maple.gg/u/" + userName; // 크롤링할 url지정
            System.out.println("==================>" + url);

            Document doc = Jsoup.connect(url).get();

            //정보
            Element user_pic = doc.select("img[class=\"character-image\"]").get(0);
            System.out.println("URL : " + user_pic.attr("src"));
            String img = user_pic.attr("src");

            Elements gg_name = doc.select("b[class=\"align-middle\"]");
            String name = null;
            name = gg_name.select("b[class=\"align-middle\"]").text();
            System.out.println("닉네임 : " + name);

            Elements gg_sever = doc.select("img[class=\"align-middle\"]");
            String serverStr = gg_sever.attr("alt");
            System.out.println("sever_url : " + gg_sever.attr("src"));

            Elements gg_Lv_op_pop = doc.select("li[class=\"user-summary-item\"]");
            String user_Lv = gg_Lv_op_pop.select("li[class=\"user-summary-item\"]").get(0).text();
            String user_op = gg_Lv_op_pop.select("li[class=\"user-summary-item\"]").get(1).text();
            String user_pop = gg_Lv_op_pop.select("li[class=\"user-summary-item\"]").get(2).text();
            System.out.println("직업 : " + user_op);
            System.out.println("Lv : " + user_Lv);
            System.out.println("인기도 : " + user_pop);

            Elements gg_guild = doc.select("a[class=\"text-yellow text-underline\"]");

            String user_guild;
            if (gg_guild.select("a[class=\"text-yellow text-underline\"]").text().equals("")) {
                user_guild = "없음";
            } else {
                user_guild = (gg_guild.select("a[class=\"text-yellow text-underline\"]").text());
            }

            String overall_ranking = doc.select("div[class=\"col-lg-2 col-md-4 col-sm-4 col-6 mt-3\"]").get(0).select("span").text();
            String world_ranking = doc.select("div[class=\"col-lg-2 col-md-4 col-sm-4 col-6 mt-3\"]").get(1).select("span").text();
            String job_ranking_world = doc.select("div[class=\"col-lg-2 col-md-4 col-sm-4 col-6 mt-3\"]").get(2).select("span").text();
            String job_ranking_overall = doc.select("div[class=\"col-lg-2 col-md-4 col-sm-4 col-6 mt-3\"]").get(3).select("span").text();
            String listUpdate = doc.select("span[class=\"d-block font-weight-light \"]").text();
            System.out.println("종합 랭킹 : " + overall_ranking);

            System.out.println(user_guild);


            Elements gg_synthesis = doc.select("h1[class=\"user-summary-floor font-weight-bold\"]");
            Elements gg_synthesis_hr = doc.select("small[class=\"user-summary-duration\"]");
            Elements gg_synthesis_div = doc.select("div[class=\"col-lg-3 col-6 mt-3 px-1\"]");

            // 마지막 업데이트
            String lastUpateJ = doc.select("span[class=\"d-block font-weight-light\"]").text();

            System.out.println(lastUpateJ);

            //=============================무릉==================================
            String user_mr = "-";
            String user_mr_hr = "-";
            Element user_mr_img = null;
            String userMrImg = null;
            String mrDate = "-";
            String worldRanking = "-";
            String ranking = "-";
            try {
                Element gg_synthesis_mr_div = gg_synthesis_div.select("div[class=\"col-lg-3 col-6 mt-3 px-1\"]").get(0);
                Element gg_synthesis_mr_div_footer = gg_synthesis_mr_div.select("footer[class=\"user-summary-box-footer\"]").get(0);
                user_mr_img = doc.select("img[class=\"d-inline-block mr-1\"]").get(0);

                user_mr = gg_synthesis.get(0).text();
                user_mr_hr = gg_synthesis_hr.get(0).text();
                String user_mr_info = gg_synthesis_mr_div_footer.select("span").get(0).text();
                String user_mr_world_ranking = ("월드랭킹 " + gg_synthesis_mr_div_footer.select("span").get(1).text());
                String user_mr_ranking = ("랭킹 " + gg_synthesis_mr_div_footer.select("span").get(2).text());
                System.out.println("user_mr_img : " + user_mr_img.attr("src"));
                userMrImg = user_mr_img.attr("src");
                System.err.print(user_mr_info + ", ");
                System.err.print(user_mr_world_ranking + ", ");
                System.err.println(user_mr_ranking);
                mrDate = doc.select("div[class=\"user-summary-date\"]").get(0).select("span").text();

                worldRanking = doc.select("div[class=\"mb-2\"]").get(0).select("span").get(0).text();
                ranking = doc.select("div[class=\"mb-2\"]").get(0).select("span").get(1).text();
                System.out.println("무릉 : " + user_mr);
                System.out.println("무릉 시간 : " + user_mr_hr);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("무릉 : (정보없음)");
            }

            //=============================더시드==================================
            String user_the_hr = "-";
            String user_the = "-";
            try {
                Element gg_synthesis_the_div = gg_synthesis_div.select("div[class=\"col-lg-3 col-6 mt-3 px-1\"]").get(1);
                Element gg_synthesis_the_div_footer = gg_synthesis_the_div.select("footer[class=\"user-summary-box-footer\"]").get(0);

                user_the = gg_synthesis.get(1).text();
                user_the_hr = gg_synthesis_hr.get(1).text();

                String user_the_info = gg_synthesis_the_div_footer.select("span").get(0).text();
                String user_the_world_ranking = ("월드랭킹 " + gg_synthesis_the_div_footer.select("span").get(1).text());
                String user_the_ranking = ("랭킹 " + gg_synthesis_the_div_footer.select("span").get(2).text());

                System.out.println("==============================================================");
                System.out.print(user_the_info + ", ");
                System.out.print(user_the_world_ranking + ", ");
                System.out.println(user_the_ranking);
                System.out.println("더시드 : " + user_the);
                System.out.println("더시드 시간 : " + user_the_hr);
                System.out.println("==============================================================");

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("더시드 : (정보없음)");
            }

            //=============================유니온===================================
            String user_un_img = "-";
            String user_un = "-";
            String user_un_lv = "-";
            String user_un_info = "0";
            String user_un_info_1 = "-";
            String user_un_info_2 = "-";
            String user_un_info_3 = "-";
            int coinUn = 0;
            try {
                Element gg_synthesis_un_div = gg_synthesis_div.select("div[class=\"col-lg-3 col-6 mt-3 px-1\"]").get(2);
                Element gg_synthesis_un_div_footer = gg_synthesis_un_div.select("footer[class=\"user-summary-box-footer\"]").get(0);
                Element gg_synthesis_un_img = gg_synthesis_un_div.selectFirst("img[class=\"user-summary-tier\"]");

                user_un_info = gg_synthesis_un_div_footer.select("span").get(0).text();
                user_un_info_1 = gg_synthesis_un_div_footer.select("span").get(1).text();
                user_un_info_2 = gg_synthesis_un_div_footer.select("span").get(2).text();
                user_un_info_3 = gg_synthesis_un_div_footer.select("span").get(3).text();
                String user_un_world_ranking = ("월드랭킹 " + gg_synthesis_un_div_footer.select("span").get(1).text());
                String user_un_ranking = ("랭킹 " + gg_synthesis_un_div_footer.select("span").get(2).text());
                user_un_img = gg_synthesis_un_img.attr("src");

                System.out.println("유니온 URL : " + user_un_img);
                System.out.print(user_un_info + ", ");
                System.out.print(user_un_world_ranking + ", ");
                System.out.println(user_un_ranking);

                Elements gg_synthesis_un = doc.select("div[class=\"user-summary-tier-string font-weight-bold\"]");
                Element gg_synthesis_un_lv = doc.select("span[class=\"user-summary-level\"]").get(0);
                user_un_lv = gg_synthesis_un_lv.text();
                user_un = gg_synthesis_un.get(0).text();
                coinUn = Integer.parseInt(user_un_info.replace("전투력","").replace(",","").replace(" ",""));
                System.out.println("유니온 레벨 : " + user_un_lv);
                System.out.println("유니온 : " + user_un);

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("유니온 : (정보없음)");
            }

            //=============================업적===================================
            try {
                Element gg_synthesis_gread_div = gg_synthesis_div.select("div[class=\"col-lg-3 col-6 mt-3 px-1\"]").get(3);
                Element gg_synthesis_gread_div_footer = gg_synthesis_gread_div.select("footer[class=\"user-summary-box-footer\"]").get(0);
                Element gg_synthesis_gread_img = gg_synthesis_gread_div.selectFirst("img[class=\"user-summary-tier\"]");

                String user_gread_score = gg_synthesis_gread_div.select("span").get(0).text();
                String user_gread_info = gg_synthesis_gread_div_footer.select("span").get(0).text();
                String user_gread_world_ranking = ("월드랭킹 " + gg_synthesis_gread_div_footer.select("span").get(1).text());
                String user_gread_ranking = ("랭킹 " + gg_synthesis_gread_div_footer.select("span").get(2).text());
                String user_gread_img = gg_synthesis_gread_img.attr("src");


                System.out.println(user_gread_score);
                System.out.print(user_gread_info + ", ");
                System.out.print(user_gread_world_ranking + ", ");
                System.out.println(user_gread_ranking);
                System.out.println("업적 URL : " + user_gread_img);

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("업적 : (정보없음)");
            }
            double coin = coinUn * 8.64 / 10000000;

            userBuider.clear();
            userBuider.setColor(Color.MAGENTA);
            userBuider.setTitle(userName + "님의 검색 내용");

            userBuider.setThumbnail(img.replace("https://", "http://"));
            userBuider.appendDescription(serverStr + " | " + name + "\n");
            userBuider.appendDescription(user_Lv + " | " + user_op + " | " + user_pop + "\n" + listUpdate);
            userBuider.addField("길드", user_guild, true);
            userBuider.addField("종합랭킹", overall_ranking, true);
            userBuider.addField("월드랭킹", world_ranking, true);

            userBuider.addField("무릉도장", user_mr + "\n" + user_mr_hr, true);
            userBuider.addField("유니온", user_un + "\n" + user_un_lv, true);
            userBuider.addField("더시드", user_the + "\n" + user_the_hr, true);

            userBuider.addField("직업랭킹\n(월드)", job_ranking_world, true);
            userBuider.addField("", "", true);
            userBuider.addField("직업랭킹\n(전체)", job_ranking_overall, true);
            userBuider.setFooter(url, "https://cdn.maple.gg/apple-icon-57x57.png");

            //==================================================================================

            userBuiderMr.clear();
            userBuiderMr.setColor(Color.MAGENTA);
            userBuiderMr.setTitle(userName + "님의 무릉 정보");
            userBuiderMr.setThumbnail(userMrImg);
            userBuiderMr.appendDescription(mrDate);
            userBuiderMr.addField("최고기록", user_mr, true);
            userBuiderMr.addField("", "", true);
            userBuiderMr.addField("시간", user_mr_hr, true);
            userBuiderMr.addField("월드랭킹", worldRanking, true);
            userBuiderMr.addField("", "", true);
            userBuiderMr.addField("랭킹", ranking, true);
            userBuiderMr.setFooter(url, "https://cdn.maple.gg/apple-icon-57x57.png");

            //==================================================================================

            userBuiderCoin.clear();
            userBuiderCoin.setColor(Color.MAGENTA);
            userBuiderCoin.setTitle(userName + "님의 유니온 정보");
            userBuiderCoin.setThumbnail(user_un_img);
            userBuiderCoin.appendDescription(user_un_info_3+"\n");
            userBuiderCoin.appendDescription("1일 획득 가능 코인은 "+String.format("%.2f", coin) + "개 입니다. ");
            userBuiderCoin.addField("등급", user_un_lv, true);
            userBuiderCoin.addField("", "", true);
            userBuiderCoin.addField("전투력", user_un_info.replace("전투력 ",""), true);
            userBuiderCoin.addField("월드랭킹", user_un_info_1.replace("월드랭킹 ", ""), true);
            userBuiderCoin.addField("", "", true);
            userBuiderCoin.addField("랭킹", user_un_info_2.replace("랭킹 ", ""), true);

        } catch (Exception e) {
            userBuider.clear();
            userBuider.setColor(Color.MAGENTA);
            userBuider.addField("잘못 검색 하셨습니다.", "Tip. Maple.gg에서 업데이트 하셨나요..?", true);
        }
    }
}
