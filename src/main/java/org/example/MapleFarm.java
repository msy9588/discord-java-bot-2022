package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class MapleFarm {
    public static void main(String[] args) {
        printData("쁘띠 은월");
    }
    static ArrayList<String> userArrayList = new ArrayList<>();
    static ArrayList<String> dayArrayList = new ArrayList<>();
    static StringBuilder farmStringBuliderUser = new StringBuilder();
    static StringBuilder farmStringBuliderDay = new StringBuilder();
    static StringBuilder farmStringBuliderNum = new StringBuilder();

    static EmbedBuilder wachanBuild  = new EmbedBuilder();
    public static void mesoKr(String msg) {
        try {
            userArrayList.clear();
            dayArrayList.clear();
            String urls = "https://meso.kr/monster.php?n="+msg;
            System.out.println(urls);
            Document doc = Jsoup.connect(urls).userAgent("Mozilla/5.0").timeout(10000).get();
            Thread.sleep(5000);
            int count_DIV1 = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("button").size();
            System.out.println(count_DIV1);
            for (int i = 0; i < count_DIV1; i++) {
                String userName = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("button").get(i).attr("dt-name");
                String d_day = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("tr").get(i+1).select("td").get(2).text();
                String num = doc.select("div[class=\"m-5\"]").get(0).select("table").get(1).select("tr").get(i+1).select("td").get(1).text();
                farmStringBuliderUser.append("\n").append(userName);
                farmStringBuliderDay.append("\n").append(d_day);
                farmStringBuliderNum.append("\n").append(num);
                if(i != 0) {
                    if(i % 5 == 0) {
                        userArrayList.add(String.valueOf(farmStringBuliderUser));
                        dayArrayList.add(String.valueOf(farmStringBuliderDay));
                        farmStringBuliderUser.setLength(0);
                        farmStringBuliderDay.setLength(0);
                        farmStringBuliderNum.setLength(0);
                    }
                }
                System.out.println(farmStringBuliderNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getLatestCurrency(String url, String param) {
        String line;
        int responseCode = 0;
        try {
            URL currencyUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)currencyUrl.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
            wr.write(param.getBytes("utf-8"));
            wr.flush();
            wr.close();

            responseCode = httpConn.getResponseCode();
            System.out.println("responseCode: " + responseCode);
            StringBuilder sb = new StringBuilder();
            ArrayList<String> arr = new ArrayList<>();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader inReader = new
                        InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inReader);
                while((line = reader.readLine()) != null) {
                    sb.append(StringEscapeUtils.unescapeJava(line));
                }
                httpConn.disconnect();
                inReader.close();
                reader.close();
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(sb));
            String jsonStr = jsonObject.get("farm_list").toString().replace("[","").replace("]","").replace("\"", "");

            String[] jsonArr = jsonStr.split(",");

            System.out.println(Arrays.toString(jsonArr));
            System.out.println(jsonArr.length);
            for (int i = 0; i < jsonArr.length; i++) {
                if(i % 2 == 0) {
                    System.out.println(jsonArr[i] + ":" + jsonArr[i+1]);
                    farmStringBuliderUser.append(jsonArr[i]);
                    userArrayList.add((jsonArr[i]));
                    dayArrayList.add(jsonArr[i+1]);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printData(String msg) {
        getLatestCurrency("http://wachan.me/farm_read.php","monster="+msg);
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
                    wachanBuild.setFooter("http://wachan.me/farm.php", "http://wachan.me/%EC%95%84%EB%AC%B4_%EC%99%80%EC%A8%A9.png");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
