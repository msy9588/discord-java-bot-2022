package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Options extends ListenerAdapter {

    public static void main(String[] args) {
        weapon("아케인셰이드 튜너");
    }
    static ArrayList<String> option = new ArrayList<>();
    static EmbedBuilder embedBuilderWeapon = new EmbedBuilder();

    static boolean weaponException = true;

    static String imgURL = null;
    public static void weapon(String msg) {
        option.clear();
        try {
            String[] weapon = msg.split(" ");

            System.out.println(Arrays.toString(weapon));

            String url = "http://wachan.me/weapon.php?weapon=" + weapon[1];
            Document doc = Jsoup.connect(url).get();

            Elements weaponDiv = doc.select("div[class=\"table4\"]");

            for (int i = 0; i < weaponDiv.select("span[class=\"name\"]").size(); i++) {
                if(weaponDiv.select("span[class=\"name\"]").get(i).text().contains(weapon[0])) {
                    imgURL = "http://wachan.me/"+weaponDiv.select("img[class=\"Image\"]").get(i).attr("src");
                    for (int j = 0; j <weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").size(); j++) {
                        if(!Objects.equals(weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").get(j).text(), "")) {
                            option.add(weaponDiv.select("div[class=\"row\"]").get(i).select("span[class=\"Option\"]").get(j).text());
                        }
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = option.size()-1; i > 0; i--) {
                sb.append(option.size() - i).append("추 : ").append(option.get(i)).append("\n");
            }
            if(!String.valueOf(sb).isEmpty()) {
                weaponException = true;
                embedBuilderWeapon.clear();
                embedBuilderWeapon.setColor(Color.MAGENTA);
                embedBuilderWeapon.addField("무기 이름",msg, false);
                embedBuilderWeapon.setThumbnail(Options.imgURL);
                embedBuilderWeapon.addField("추옵", String.valueOf(sb), false);
            } else {
                weaponException = false;
            }
        } catch (Exception e) {
            weaponException = false;
        }
    }
}
