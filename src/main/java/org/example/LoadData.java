package org.example;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author CGaren
 *
 */

public class LoadData {
    public void getLatestCurrency(String url,String param) {
        String line = "";
        int responseCode = 0;
        try {
            URL currencyUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)currencyUrl.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
            wr.write(param.getBytes("utf-8"));
            wr.flush();
            wr.close();

            responseCode = httpConn.getResponseCode();
            System.out.println("responseCode: " + responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader inReader = new
                        InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inReader);
                while((line = reader.readLine()) != null) {
                    System.out.println(StringEscapeUtils.unescapeJava(line));
                }
                httpConn.disconnect();
                inReader.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        getLatestCurrency("http://wachan.me/farm_read.php","monster=쁘띠 은월");
    }

    public static void main(String[] args) {
        LoadData app = new LoadData();
        app.printData();
    }
}