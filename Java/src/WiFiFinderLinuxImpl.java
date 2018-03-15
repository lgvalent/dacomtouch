
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class WiFiFinderLinuxImpl implements WiFiFinder {

//    private Config config = new Config();
//    private String room;
//    private String name;
//    private String password;
//    private String url;
//    private Status status;
//    private String[] interf;
    private enum States {
        ADDRESS, QUALITY, ESSID
    };

//    private enum Status {
//        INDISPONIVEL, DISPONIVEL, PALUNO, AULA
//    }
    @Override
    public List<Interface> getList() {
        List<Interface> list = new ArrayList<>();
        Process p;
        String[] command = {"/bin/bash", "-c", "iwlist scanning | egrep 'Cell |Address|Quality|Signal level|ESSID'"};
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            States state = States.ADDRESS;
            Interface interf = new Interface();

            while ((s = stdInput.readLine()) != null) {
                switch (state) {
                    case ADDRESS:
                        if (s.contains("Address")) {
                            // pega o mac
                            interf.setAddress(s.substring(s.indexOf(":") + 2));
                            state = States.QUALITY;
                        }
                        break;
                    case QUALITY:
                        if (s.trim().startsWith("Quality")) {
                            // pega a qualidade e a força do sinal
                            interf.setQuality(s.substring(s.indexOf("=") + 1, s.indexOf("/")));
                            interf.setQuality(String.valueOf((interf.getQuality() * 100) / 70));
                            interf.setSignal_level(s.substring(s.lastIndexOf("=") + 1, s.length() - 6));
                            state = States.ESSID;
                        }
                        break;
                    case ESSID:
                        if (s.trim().startsWith("ESSID")) {
                            // pega o name
                            interf.setEssid(s.substring(s.indexOf(":") + 2, s.length() - 1));
                            // grava o cara completo
                            list.add(interf);
                            // cria um novo cara
                            interf = new Interface();
                            state = States.ADDRESS;
                        }
                        break;
                }
            }
            stdInput.close();

//            Config config = new Config();
//            config.readConfProperties();
//            Person person = new Person(config);
//            Status status = WiFiFinder.getStatus(list, person, config);
//            String[] curl = {"/bin/bash", "-c", "curl -k --data \"room=" + person.getRoom() + "&name=" + person.getName() + "&password=" + person.getPassword() + "&status=" + status + "\" " + person.getUrl()};
//            System.out.println(curl[2]);
//            
//            try {
//                p = Runtime.getRuntime().exec(curl);
//                stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                while ((s = stdInput.readLine()) != null) {
//                    System.out.println(s);
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(WiFiFinderLinuxImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (IOException ex) {
            Logger.getLogger(WiFiFinderLinuxImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
