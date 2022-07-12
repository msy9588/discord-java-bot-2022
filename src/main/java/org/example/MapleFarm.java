package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.util.ArrayList;

public class MapleFarm {
    static ArrayList<String> userArrayList = new ArrayList<>();
    static ArrayList<String> dayArrayList = new ArrayList<>();
    static ArrayList<String> numList = new ArrayList<>();
    static StringBuilder farmStringBuliderUser = new StringBuilder();
    static StringBuilder farmStringBuliderDay = new StringBuilder();
    static StringBuilder farmStringBuliderNum = new StringBuilder();

    static EmbedBuilder wachanBuild  = new EmbedBuilder();
    public static void mesoKr(String msg) {
        try {
            userArrayList.clear();
            dayArrayList.clear();
            numList.clear();
            String url = "https://meso.kr/monster.php?n="+msg;
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            int count_DIV1 = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("button").size();
            System.out.println(count_DIV1);
            for (int i = 0; i < count_DIV1; i++) {
                String userName = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("button").get(i).attr("dt-name");
                String d_day = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("tr").get(i+1).select("td").get(2).text();
                String num = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("tr").get(i+1).select("td").get(1).text();
                farmStringBuliderUser.append("\n").append(userName);
                farmStringBuliderDay.append("\n").append(d_day);
                farmStringBuliderNum.append("\n").append(num);
                System.out.println(i + " : " + userName);
                if(i != 0) {
                    if(i % 5 == 0) {
                        System.out.println(i);
                        userArrayList.add(String.valueOf(farmStringBuliderUser));
                        dayArrayList.add(String.valueOf(farmStringBuliderDay));
                        numList.add(String.valueOf(farmStringBuliderNum));
                        farmStringBuliderUser.setLength(0);
                        farmStringBuliderDay.setLength(0);
                        farmStringBuliderNum.setLength(0);
                    }
                }
            }
            System.out.println(userArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void wachan(String msg) {

        try {
            wachanBuild.clear();
            String url = "http://wachan.me/farm.php";
            Document doc = Jsoup.connect(url).get();
            int monsterSize = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").size();
            System.out.println(monsterSize);
            for (int i = 0; i < monsterSize; i++) {
                String monster = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).text();
                if(monster.equals(msg)) {
                    String monsterGss = doc.select("div[class=\"table fmob\"]").select("span").get(2).text();
                    String monsterAbility = doc.select("div[class=\"table fmob\"]").get(i).select("div").get(2).text();

                    String sum1 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").get(0).select("span[class=\"mob_name\"]").text();
                    String sum2 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").get(1).select("span[class=\"mob_name\"]").text();

                    String sumAbility1 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").select("div[class=\"detail\"]").select("span").get(0).text();
                    String sumAbility2 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").select("div[class=\"detail\"]").select("span").get(1).text();

                    String sumGss1 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").get(0).select("span").get(2).text();
                    String sumGss2 = doc.select("div[class=\"table fmob\"]").select("span[class=\"mob_name\"]").get(i).parent().parent().parent().children().select("div[class=\"table smob\"]").get(1).select("span").get(2).text();

                    System.out.println(sum1 + " / " + sumAbility1 + " / " + sumGss1);
                    System.out.println(sum2 + " / " + sumAbility2 + " / " + sumGss2);
                    System.out.println(monster + " / " + monsterAbility + " / " + monsterGss);
                    wachanBuild.setColor(Color.MAGENTA);
                    wachanBuild.setTitle(msg + "에 대한 검색 내용");
                    wachanBuild.addField("결과",monster+" ( "+monsterGss+" ) \n" + monsterAbility, false);
                    wachanBuild.addField("조합 몬스터",sum1+" ( "+sumGss1+" ) \n" + sumAbility1, true);
                    wachanBuild.addField("조합 몬스터",sum2+" ( "+sumGss2+" ) \n" + sumAbility2, true);
                    wachanBuild.setFooter("http://wachan.me/farm.php");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
