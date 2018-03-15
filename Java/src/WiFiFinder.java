
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public interface WiFiFinder {

    public List<Interface> getList();
//    public void getStatus();

    public static WiFiFinder getInstance() {
        switch (System.getProperty("os.name").toLowerCase()) {
            case "linux":
                return new WiFiFinderLinuxImpl();
            case "mac os x":
                return new WiFiFinderMacImpl();
            default:
                return new WiFiFinderWindowsImpl();
        }
    }
}
