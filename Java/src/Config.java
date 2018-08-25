
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class Config {

    private static final String FILE_PATH = "config.properties";
    public static final String HOST = "http://valentin.com.br/dacom/";

    private Properties prop = new Properties();
    private InputStream input = null;
    private OutputStream output = null;
    private String status;
    private String[] status_priority = null;
    private String actionURL;
    private String login;
    private String password;
    private String room;
    private Map<String, Integer> statusMap = new LinkedHashMap<>();
    private int status_default_index;
    private boolean exists = false;

    public boolean isExists() {
        return exists;
    }

    public Config() {
        readConfProperties();
    }

    //String url, String name, String password, String room
    public void writeConfProperties(String login, String password, String sala, String status_default) {
        try {
            output = new FileOutputStream(FILE_PATH);
            // set the properties value
            prop.setProperty("actionURL", HOST + "/login.php");
            prop.setProperty("status", "dacom:1" + ',' + "UTFPRADM:3");
            prop.setProperty("user.login", login);
            prop.setProperty("user.password", password);
            prop.setProperty("user.room", sala);
            prop.setProperty("user.status_default", status_default);
            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readConfProperties() {
        try {
            input = new FileInputStream(FILE_PATH);
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            this.actionURL = prop.getProperty("actionURL");
            this.status = prop.getProperty("status");
            this.login = prop.getProperty("user.login");
            this.password = prop.getProperty("user.password");
            this.room = prop.getProperty("user.room");

            this.status_default_index = Integer.parseInt(prop.getProperty("user.status_default"));

            this.status_priority = this.status.split(",");
            this.statusMap.clear();

            for (String str : this.status.split(",")) {
                String key = str.split(":")[0];
                String value = str.split(":")[1];
                this.statusMap.put(key, Integer.parseInt(value));
            }

            for (Map.Entry<String, Integer> entry : this.statusMap.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            this.exists = true;

        } catch (IOException ex) {
            ex.printStackTrace();
            this.exists = false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getStatus_default_index() {
        return status_default_index;
    }

    public void setStatus_default_index(int status_default_index) {
        this.status_default_index = status_default_index;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String[] getStatus_priority() {
        return status_priority;
    }

    Map<String, Integer> getStatusMap() {
        return this.statusMap;
    }

}
