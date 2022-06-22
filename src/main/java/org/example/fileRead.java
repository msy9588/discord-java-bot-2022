package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class fileRead {
    public void command(MessageReceivedEvent event) {
        try {
            JSONParser parser = new JSONParser();

            Reader reader = new FileReader("D:\\GItHub\\discord-java-bot-2022\\src\\main\\java\\org\\example\\command..json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            String keyJSON = (String) jsonObject.get(event.getMessage().getContentDisplay());
            if(keyJSON != null) {
                event.getChannel().sendMessage(keyJSON).queue();
            }
//            if(event.getMessage().getContentDisplay().equals("커멘드")) {
//                event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("")).queue();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
