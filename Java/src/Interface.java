/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafael
 */
public class Interface {
    private String address;
    private int quality;
    private int signal_level;
    private String essid;
    

    public Interface() {
    
    }

    public Interface(String address, String quality, String signal_level, String essid) {
        this.address = address;
        this.quality = Integer.parseInt(quality);
        this.signal_level = Integer.parseInt(signal_level);
        this.essid = essid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = Integer.parseInt(quality);
    }

    public int getSignal_level() {
        return signal_level;
    }

    public void setSignal_level(String signal_level) {
        this.signal_level = Integer.parseInt(signal_level);
    }

    public String getEssid() {
        return essid;
    }

    public void setEssid(String essid) {
        this.essid = essid;
    }

    @Override
    public String toString() {
        return "Interface{" + "address=" + address + ", quality=" + quality + ", signal_level=" + signal_level  + ", essid=" + essid + '}';
    }    
}
