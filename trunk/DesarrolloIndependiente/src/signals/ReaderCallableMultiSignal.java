package signals;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReaderCallableMultiSignal extends ReaderCallable {

    protected LinkedList<ReaderCallableOneSignal> readerCallables;

    public ReaderCallableMultiSignal(String identifierOwner) {
        super(identifierOwner);
        readerCallables = new LinkedList<ReaderCallableOneSignal>();
    }

    @Override
    public ReadResult call() {
       while(!this.getLocks()){
           //Aqui podriamos esperar un número de veces determinadas y sino luego lanzar una excepción.
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReaderCallableMultiSignal.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        this.read();
        this.releaseLocks();
        return this.getReadResult();
    }

    protected ReadResult read() {        
        ReadResultMultiSignal readResultMulti=new ReadResultMultiSignal(this.getIdentifierOwner());
        for (ReaderCallableOneSignal readerCallableOneSignal : readerCallables) {
            readResultMulti.addReadResultOneSignal((ReadResultOneSignal) readerCallableOneSignal.read());
        }
        this.readResult=readResultMulti;
        return this.readResult;

    }

    @Override
    protected boolean getLocks() {
        LinkedList<String> locksTemporal = new LinkedList<String>();
        for (ReaderCallableOneSignal readerCallableOneSignal : readerCallables) {
            if (this.lockManager.tryReadLock(readerCallableOneSignal.getIdentifierSignal())) {
                locksTemporal.add(readerCallableOneSignal.getIdentifierSignal());
            } else {
                break;
            }
        }
        if (locksTemporal.size() == readerCallables.size()) {
            return true;
        } else {
            for (String identifierSignal : locksTemporal) {
                this.lockManager.releaseReadLock(identifierSignal);
            }
            return false;
        }

    }

    @Override
    protected void releaseLocks() {
         for (ReaderCallableOneSignal readerCallableOneSignal : readerCallables) {
             this.lockManager.releaseReadLock(readerCallableOneSignal.getIdentifierSignal());
         }
    }
    public boolean addReaderCallableOneSignal(ReaderCallableOneSignal readerCallableOneSignal){
        return this.readerCallables.add(readerCallableOneSignal);
    }
        //@debug metodo de depuracion solamente
    public LinkedList<ReaderCallableOneSignal> getReaderCallables() {
        return new LinkedList<ReaderCallableOneSignal>(readerCallables);
    }

}
