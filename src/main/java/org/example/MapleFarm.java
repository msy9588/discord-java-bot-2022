package org.example;

import net.dv8tion.jda.api.MessageBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MapleFarm {
    public static void mesoKr(String msg) {
        try {
            String url = "https://meso.kr/monster.php?n="+msg;
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            int count_DIV0 = doc.select("div[class=\"container\"]").get(0).select("table").get(0).select("button").size();
            int count_DIV1 = doc.select("div[class=\"container\"]").get(0).select("table").get(1).select("button").size();

//            for (int i = 0; i < count_DIV0; i++) {
//                String userName = doc.select("div[class=\"container\"]").get(0).select("table").get(0).select("button").get(i).attr("dt-name");
//                System.out.println(userName);
//            }

            for (int i = 0; i < count_DIV1; i++) {
                String userName = doc.select("div[class=\"container\"]").get(0).select("table").get(1).select("button").get(i).attr("dt-name");
                String d_day = doc.select("div[class=\"container\"]").get(0).select("table").get(1).select("tr").get(i+1).select("td").get(2).text();
                System.out.println(userName + " : " + d_day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
