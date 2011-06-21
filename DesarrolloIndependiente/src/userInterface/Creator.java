package userInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import signals.SignalManager;
public class Creator {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                UIManager.installLookAndFeel("NimROD", "com.nilo.plaf.nimrod.NimRODLookAndFeel");
                try {
                    UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JBedSideMonitorMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(JBedSideMonitorMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(JBedSideMonitorMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(JBedSideMonitorMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JBedSideMonitorMainWindow mainWindow = new JBedSideMonitorMainWindow(SignalManager.getInstance().getJSignalAdapter());
        mainWindow.setSize(800, 500);
        mainWindow.setVisible(true);
    }
}
