package org.example;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class Options extends ListenerAdapter {
    static ArrayList<String> option = new ArrayList<>();

    static String imgURL = null;
    public static void weapon(String msg) {
        option.clear();
        try {
            System.out.println(msg);
            String[] weapon = msg.split(" ");


            String url = "http://wachan.me/weapon.php?weapon=" + weapon[1];
            Document doc = Jsoup.connect(url).get();

            System.out.println(url);

            Elements weaponDiv = doc.select("div[class=\"table4\"]");

            for (int i = 0; i < weaponDiv.select("span[class=\"name\"]").size(); i++) {
                if(weaponDiv.select("span[class=\"name\"]").get(i).text().contains(weapon[0])) {
                    imgURL = "http://wachan.me/"+weaponDiv.select("img[class=\"Image\"]").get(i).attr("src");
                    System.out.println( weaponDiv.select("img[class=\"Image\"]").attr("src"));
                    for (int j = 0; j <weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").size(); j++) {
                        if(!Objects.equals(weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").get(j).text(), "")) {
                            option.add(weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").get(j).text());
                        }
                    }
                }
            }
            System.out.println(imgURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
