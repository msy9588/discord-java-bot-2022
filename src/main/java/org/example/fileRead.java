package org.example;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class fileRead extends ListenerAdapter {
    public static void main(String[] args) {
        fileReadCommand("여로");
    }
    static String commandValue = null;

    public static void fileReadCommand(String msg) {
        commandValue = null;
        try {
            System.out.println(msg);
            mySQL mySQL = new mySQL();
            Connection connection = DriverManager.getConnection(mySQL.url, mySQL.userName, mySQL.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from discord.command");

            while (resultSet.next()) {
                String commandKey = resultSet.getString("commandKey");
                String commandVal = resultSet.getString("commandVal");
                if(msg.equals(commandKey)) {
                    commandValue = commandVal;
                    System.out.println("commandKey : " +commandKey);
                    System.out.println("commandVal : " + commandVal);
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            commandValue = "Not Found Command";
            e.printStackTrace();
        }

    }

//    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
//        try {
//            JSONParser parser = new JSONParser();
//
//            Reader reader = new FileReader("src/main/java/org/example/command.json");
//
//            JSONObject jsonObject = (JSONObject) parser.parse(reader);
//
//            String keyJSON = (String) jsonObject.get(event.getMessage().getContentDisplay());
//            if(keyJSON != null) {
//                event.getChannel().sendMessage(keyJSON).queue();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
