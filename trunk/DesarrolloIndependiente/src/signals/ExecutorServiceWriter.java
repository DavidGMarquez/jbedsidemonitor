/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author USUARIO
 */
public class ExecutorServiceWriter {
private ExecutorService executorService;
    public ExecutorServiceWriter() {
        executorService=Executors.newCachedThreadPool();
    }

    
    public void executeWriterRunnable(WriterRunnable writerRunnable)
    {
        this.executorService.execute(writerRunnable);
    }
}
