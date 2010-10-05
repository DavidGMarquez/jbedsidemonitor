/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author USUARIO
 */
public class CompletionExecutorServiceReader {
private ExecutorCompletionService executorCompletionService;
    public CompletionExecutorServiceReader() {
        executorCompletionService=new ExecutorCompletionService(Executors.newCachedThreadPool());
    }

    
    public void executeReaderRunnable(ReaderCallable readerRunnable)
    {
        this.executorCompletionService.submit(readerRunnable);
    }
}
