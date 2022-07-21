package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Nexon_Probability {
    public static void main(String[] args) {
        Probability("RoyalStyle");
    }
    public static void Probability(String optionStr) {
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
//            System.out.println(map);
            int tmpRandom = (int) (Math.random() * 1000);
            System.out.println(map.get(tmpRandom));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
