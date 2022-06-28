package org.example;

import java.io.FileReader;
import java.io.Reader;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class fileRead extends ListenerAdapter {
    static String commandValue = null;

    public static void fileReadCommand(String msg) {
        try {
            commandValue = null;
            JSONParser parser = new JSONParser();

            Reader reader = new FileReader("src/main/java/org/example/command.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            String keyJSON = (String) jsonObject.get(msg);
            if(keyJSON != null) {
                commandValue = keyJSON;
            } else {
                commandValue = "Not Found Command";
            }
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
