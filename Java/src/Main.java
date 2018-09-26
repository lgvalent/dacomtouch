
import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael
 */
public class Main {

    public static void main(String[] args) {

        Tray tray = null;

        try {
            tray = new Tray();
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!Config.getInstance().isExists()) {
            JOptionPane.showMessageDialog(null, "Arquivo de configuração inexistente!!!");
            new DataConfigJDialog(null, true);
        }

        StatusManager statusManager = new StatusManager();
        
        try {
            statusManager.refreshStatus();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        
        while (true) {

            try {
                if (GraphicsEnvironment.isHeadless()) {
                    statusManager.refreshStatus();
                } else {
                    Point currentMousePoint = MouseInfo.getPointerInfo().getLocation();
                    if (mousePoint.x != currentMousePoint.x || mousePoint.y != currentMousePoint.y) {
                        statusManager.refreshStatus();
                        mousePoint = currentMousePoint;
                        System.out.println("Diferente");
                    }
                }

                tray.setTooltip("Dacomtouch (" + statusManager.getCurrentStatus() + ")");

            } catch (Exception e) {
                System.out.println("Não foi possivel atuaizar o status");
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(Config.getInstance().getTimer());
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
