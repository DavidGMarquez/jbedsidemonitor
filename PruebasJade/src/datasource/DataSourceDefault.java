package datasource;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import signals.SignalManager;
import signals.WriterRunnable;

public abstract class DataSourceDefault implements DataSource {

    public boolean waitAndSendWriterRunable(WriterRunnable writerRunnable) {
        while (!SignalManager.getInstance().isRunning()) {
            synchronized (SignalManager.getInstance().getLockWaitRunning()) {
                try {
                    SignalManager.getInstance().getLockWaitRunning().wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DataSourceDefault.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return sendWriterRunnable(writerRunnable);
    }

    public boolean sendWriterRunnable(WriterRunnable writerRunnable) {
        return SignalManager.getInstance().encueWriteOperation(writerRunnable);
    }

    public boolean hasConfigurationGui() {
        return false;
    }

    public void showConfigurationGui(JFrame jframe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIdentifier() {
        return "DefaultDataSource";
    }

    public boolean registerThis() {
        return SignalManager.getInstance().registerDataSource(this);
    }

    public boolean desactivate() {
        return SignalManager.getInstance().inactiveDataSource(this);
    }

    public ArrayList<String> getSeriesGenerated() {
        return new ArrayList<String>();
    }
    public boolean start(){
        return true;
    }
}
