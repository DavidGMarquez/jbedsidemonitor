package signals;

import algorithms.AlgorithmManager;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompletionExecutorServiceReader implements Runnable {

    private ExecutorCompletionService executorCompletionService;

    public CompletionExecutorServiceReader() {
        executorCompletionService = new ExecutorCompletionService(Executors.newCachedThreadPool());
    }

    public void executeReaderCallable(ReaderCallable readerCallable) {
        //@debug borrar
        System.out.println("Submit Callable");
        this.executorCompletionService.submit(readerCallable);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("RunCompletion");
            try {
                Future<ReadResult> futureReadResult = executorCompletionService.take();
                ReadResult readResult = futureReadResult.get();
                //@debug borrar
                if(readResult instanceof ReadResultTimeSeries)
                {
                    ReadResultTimeSeries readResultTimeSeries=(ReadResultTimeSeries)readResult;
                    System.out.println("Obteniendo un resultado Futuro de "+readResult.getIdentifierOwner()+"desde: "+readResultTimeSeries.getPosInitToRead()+"Size"+readResultTimeSeries.getData().length);
                }
 else
     System.out.println("Obteniendo un resultado Futuro de "+readResult.getIdentifierOwner()+"desde");
                AlgorithmManager.getInstance().processData(readResult);
            } catch (ExecutionException ex) {
                Logger.getLogger(CompletionExecutorServiceReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompletionExecutorServiceReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
