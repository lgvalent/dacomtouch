
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
 * @author Rafael
 */
public class WiFiFinderWindowsImpl implements WiFiFinder {

    private List<Interface> list = new ArrayList<>();

    private enum States {
        SSID, BSSID, QUALITY
    }

    @Override
    public List<Interface> getList() {
        Process p;
        String command = "netsh wlan show networks mode=bssid";
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            String ssid = "";
            States state = States.SSID;
            Interface interf = new Interface();
            while ((s = stdInput.readLine()) != null) {
                switch (state) {
                    case BSSID:
                        if (s.trim().startsWith("BSSID")) {
                            // pega o mac
                            interf.setAddress(s.substring(s.indexOf(":") + 2));
                            interf.setEssid(ssid);
                            state = States.QUALITY;
                        }
                    case SSID:
                        if (s.startsWith("SSID")) {
                            // pega o nome
                            if (s.indexOf(":") + 2 < s.length()) {
                                ssid = s.substring(s.indexOf(":") + 2);
                            }
                            state = States.BSSID;
                        }
                        break;
                    case QUALITY:
                        if (s.trim().contains("%")) {
                            // pega o sinal
                            interf.setQuality(s.substring(s.indexOf(":") + 2, s.indexOf("%")));
                            // grava o cara completo
                            this.list.add(interf);
                            // cria um novo cara
                            interf = new Interface();
                            state = States.BSSID;
                        }
                        break;
                }
            }
            stdInput.close();
        } catch (IOException ex) {
            Logger.getLogger(WiFiFinderWindowsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.list;
    }
}
