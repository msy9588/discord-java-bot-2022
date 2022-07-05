package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;

public class Melon_chart {
    static EmbedBuilder embedBuilde = new EmbedBuilder();
    public static void main(String[] args) {
    }
    public static void Melon(int index) {
        try {
            String[] Music = new String[index];
            String[] img = new String[index];
            String[] name = new String[index];

            String url = "https://www.melon.com/chart/";
            Document doc = Jsoup.connect(url).get();

            for(int i = 0; i < index; i++) {
                Music[i] = doc.select("div[class=\"ellipsis rank01\"]").get(i).text();
                img[i] = doc.select("a[class=\"image_typeAll\"]").get(i).select("img").attr("src");
                name[i] = doc.select("div[class=\"ellipsis rank02\"]").get(i).select("a").get(0).text();
                System.out.println(name[i]);
            }

            embedBuilde.clear();
            embedBuilde.setColor(Color.MAGENTA);
            embedBuilde.setTitle("멜론 차드 " + index + "위");

            StringBuilder musicBul = new StringBuilder();
            StringBuilder nameBul = new StringBuilder();

            for(int i = 0; i < index; i++) {
                musicBul.append(Music[i]).append("\n \n");
                nameBul.append(name[i]).append("\n \n");
            }

            embedBuilde.setThumbnail("https://cdnimg.melon.co.kr/resource/image/web/common/logo_melon142x99.png");
            embedBuilde.addField("곡 정보", String.valueOf(musicBul), true);
            embedBuilde.addBlankField(true);
            embedBuilde.addField("가수", String.valueOf(nameBul), true);
            embedBuilde.addBlankField(true);
            embedBuilde.set

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
