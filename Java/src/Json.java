
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class Json {

    public List<Sala> loadJson() {
        String json = null;
        try {
            URL url = new URL(Config.HOST + "/status.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            json = String.valueOf(content);
            in.close();
            con.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Json.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Json.class.getName()).log(Level.SEVERE, null, ex);
        }

        Gson gson = new Gson();
        System.out.println(json);
        StatusJson status = gson.fromJson(json, StatusJson.class);

        return status.getSalas();
    }
}
