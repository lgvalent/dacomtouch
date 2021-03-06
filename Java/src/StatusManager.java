
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
//import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class StatusManager {

    private final Config config;
//    private Logger log = Logger.getLogger(Status.class);
    private String currentStatus;

    public StatusManager() {
        this.config = Config.getInstance();
        this.config.readConfProperties();
    }

    static enum Status {
        INDISPONIVEL, DISPONIVEL, PALUNO, AULA
    }

    public void refreshStatus() throws Exception {
        this.config.readConfProperties();

        List<Interface> interfaces = WiFiFinder.getInstance().getList();

        for (Map.Entry<String, Integer> entry : this.config.getStatusMap().entrySet()) {
            for (Interface interf : interfaces) {
                if (interf.getEssid().equals(entry.getKey())) {
                    setStatus(entry.getValue());
                    setCurrentStatus(entry.getValue());
                    return;
                }
            }
        }
        setStatus(this.config.getStatus_default_index());
        setCurrentStatus(this.config.getStatus_default_index());
    }

    public void setStatus(Integer status) throws Exception {
        String str = "sala=" + this.config.getRoom() + "&nome=" + this.config.getLogin() + "&password=" + this.config.getPassword() + "&status=" + status;

        URL url = new URL(this.config.getActionURL());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);

//        log.debug("Sending data: " + str);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(str);

        out.flush();
        out.close();

        System.out.println(con.getResponseCode());

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        System.out.println(content);
        in.close();
        con.disconnect();
    }

    public void setCurrentStatus(int status) {
        switch (status) {
            case 0:
                this.currentStatus = "Indisponivel";
                break;
            case 1:
                this.currentStatus = "Presente";
                break;
            case 2:
                this.currentStatus = "PAluno";
                break;
            case 3:
                this.currentStatus = "Aula";
                break;
        }
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

}
