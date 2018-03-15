
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
 * @author rafael
 */
public class WiFiFinderMacImpl implements WiFiFinder {

    private List<Interface> list = new ArrayList<>();

    private enum States {
        AGRCTLRSSI, BSSID, SSID
    }

//    private enum Status {
//        INDISPONIVEL, DISPONIVEL, PALUNO, AULA
//    }
    @Override
    public List<Interface> getList() {
        Process p;
        String[] command = {"airport --getinfo"};
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            States state = States.AGRCTLRSSI;
            Interface interf = new Interface();
            while ((s = stdInput.readLine()) != null) {
                switch (state) {
                    case AGRCTLRSSI:
                        if (s.trim().startsWith("agrCtlRSSI")) {
                            // pega a força do sinal
                            interf.setSignal_level(s.substring(s.indexOf(":") + 2));
                            state = States.BSSID;
                        }
                        break;
                    case BSSID:
                        if (s.trim().startsWith("BSSID")) {
                            // pega o mac
                            interf.setAddress(s.substring(s.indexOf(":") + 2));
                            state = States.SSID;
                        }
                        break;
                    case SSID:
                        if (s.trim().startsWith("SSID")) {
                            // pega o name
                            interf.setEssid(s.substring(s.indexOf(":") + 2));
                            // grava o cara completo
                            this.list.add(interf);
                            // cria um novo cara
                            interf = new Interface();
                            state = States.AGRCTLRSSI;
                        }
                        break;
                }
            }
            stdInput.close();
        } catch (IOException ex) {
            Logger.getLogger(WiFiFinderLinuxImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.list;
    }

}
