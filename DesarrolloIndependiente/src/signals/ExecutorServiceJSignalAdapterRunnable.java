package signals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceJSignalAdapterRunnable {

    private final ExecutorService executorService;

    public ExecutorServiceJSignalAdapterRunnable() {
        executorService = Executors.newCachedThreadPool();
    }

    public void executeRunnable(Runnable runnable) {
        this.executorService.execute(runnable);
    }
}
