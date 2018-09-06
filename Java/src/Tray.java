
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class Tray {
    
    private TrayIcon trayIcon;
    
    public Tray() throws AWTException {

        if (SystemTray.isSupported()) {

            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("favicon.png"));
            
            PopupMenu popMenu = new PopupMenu();
            MenuItem item1 = new MenuItem("Update Properties");
            MenuItem item2 = new MenuItem("Exit");
            popMenu.add(item1);
            popMenu.add(item2);
            item1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new DataConfigJDialog(null, true);
                }
            });
            item2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            this.trayIcon = new TrayIcon(img, "dacomtouch" , popMenu);
            
            try {
                trayIcon.setImageAutoSize(true);
                SystemTray.getSystemTray().add(trayIcon);
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("TrayIcon sucessfully ");
        } else {
            System.out.println("SystemTray is not suported!");
        }
    }
    
    public void setTooltip(String tooltip){
        this.trayIcon.setToolTip(tooltip);
    }
}
