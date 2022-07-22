package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.util.HashMap;


public class Nexon_Probability {
    static EmbedBuilder probability  = new EmbedBuilder();
    public static void main(String[] args) {
        Probability("RoyalStyle", 10);
    }
    public static void Probability(String optionStr, int count) {
        probability.clear();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        int index = 1;
        try {
            String url = "https://maplestory.nexon.com/Guide/CashShop/Probability/" + optionStr;
            Document doc = Jsoup.connect(url).get();
            int tableSize = doc.select("table[class=\"my_page_tb2\"]").select("tr").size();
            System.out.println(tableSize);

            for (int i = 1; i < tableSize; i++) {
                int fiestIndex = doc.select("table[class=\"my_page_tb2\"]").select("tr").get(i).select("td").size();

                String probabilityItem = doc.select("table[class=\"my_page_tb2\"]").select("tr").get(i).select("td").get(1).text();
                String item = doc.select("table[class=\"my_page_tb2\"]").select("tr").get(i).select("td").get(0).text();

                if(fiestIndex  == 3) {
                    probabilityItem = doc.select("table[class=\"my_page_tb2\"]").select("tr").get(i).select("td").get(2).text();
                    item = doc.select("table[class=\"my_page_tb2\"]").select("tr").get(i).select("td").get(1).text();
                }
                double test = Double.parseDouble(probabilityItem.replace("%",""));
                for (int j = 0; j < test*10; j++) {
                    map.put(index, item);
                    index++;
                }
            }
            for (int i = 0; i < count; i++) {
                int tmpRandom = (int) (Math.random() * 1000);
                probability.setThumbnail("https://github.com/dudqls5271/discord-java-bot-2022/blob/master/img/Nexon_Probability.png?raw=true");
                probability.setColor(Color.MAGENTA);
                probability.setTitle("결과");
                probability.appendDescription(map.get(tmpRandom)+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
