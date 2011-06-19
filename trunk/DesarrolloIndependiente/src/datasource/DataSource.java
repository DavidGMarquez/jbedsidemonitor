package datasource;

import javax.swing.JFrame;
import signals.WriterRunnable;

public interface DataSource {

    public boolean waitAndSendWriterRunable(WriterRunnable writerRunnable);

    public boolean sendWriterRunnable(WriterRunnable writerRunnable);

    public boolean hasConfigurationGui();

    public void showConfigurationGui(JFrame jframe);

    public String getIdentifier();

    public boolean registerThis();
}
