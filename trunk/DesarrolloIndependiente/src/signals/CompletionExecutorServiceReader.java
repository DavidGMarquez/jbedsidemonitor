/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CompletionExecutorServiceReader implements Runnable{
private ExecutorCompletionService executorCompletionService;
    public CompletionExecutorServiceReader() {
        executorCompletionService=new ExecutorCompletionService(Executors.newCachedThreadPool());
    }

    
    public void executeReaderRunnable(ReaderCallable readerRunnable)
    {
        System.out.println("Submit Runnable");
        this.executorCompletionService.submit(readerRunnable);
    }

    public void run() {
        while(true){
            System.out.println("Run");
            try {
                Future<ReadResult> futureReadResult = executorCompletionService.take();
                ReadResult readResult=futureReadResult.get();
                System.out.println("Operacion Lectura Completada");
                float array[]=readResult.getData();
                for (int i=0;i<array.length;i++)
                {
                    System.out.print(" "+array[i]);
                }
            } catch (ExecutionException ex) {
                Logger.getLogger(CompletionExecutorServiceReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompletionExecutorServiceReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
