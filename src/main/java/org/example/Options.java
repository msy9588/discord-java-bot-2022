package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Options extends ListenerAdapter {
    static ArrayList<String> option = new ArrayList<>();
    static EmbedBuilder embedBuilderWeapon = new EmbedBuilder();
    static boolean weaponException = true;

    public static void weapon(String msg) {
        option.clear();
        String[] weapon = msg.split(" ");
        String url = null;
        try {

            mySQL mySQL = new mySQL();
            Connection connection = DriverManager.getConnection(mySQL.url, mySQL.userName, mySQL.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from discord.weapon");

            while (resultSet.next()) {
                String weaponClass = resultSet.getString("weaponClass");
                String weaponName = resultSet.getString("weaponName");

                if(weapon[0].equals(weaponClass)) {
                    if(msg.replace(weapon[0], "").trim().equals(weaponName)) {
                        System.out.println(resultSet.getString("weaponBasics"));
                        option.add(resultSet.getString("weapon_1"));
                        option.add(resultSet.getString("weapon_2"));
                        option.add(resultSet.getString("weapon_3"));
                        option.add(resultSet.getString("weapon_4"));
                        option.add(resultSet.getString("weapon_5"));
                        url = "https://raw.githubusercontent.com/dudqls5271/discord-java-bot-2022/master/img/weapon/"+resultSet.getString("engName")+".png";
                    }
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < option.size(); i++) {
            sb.append(i+1).append("추 : ").append(option.get(i)).append("\n");
        }

        if(!String.valueOf(sb).isEmpty()) {
            weaponException = true;
            embedBuilderWeapon.clear();
            embedBuilderWeapon.setColor(Color.MAGENTA);
            embedBuilderWeapon.addField("무기 이름",msg, false);
            embedBuilderWeapon.setThumbnail(url);
            embedBuilderWeapon.addField("추옵", String.valueOf(sb), false);
        } else {
            weaponException = false;
        }
    }
}
