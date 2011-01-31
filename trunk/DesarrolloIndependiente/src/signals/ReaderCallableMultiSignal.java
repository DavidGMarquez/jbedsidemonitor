package signals;

import java.util.LinkedList;

class ReaderCallableMultiSignal extends ReaderCallable {

    protected LinkedList<ReaderCallableOneSignal> readerCallables;

    public ReaderCallableMultiSignal(String identifierOwner) {
        super(identifierOwner);
        readerCallables = new LinkedList<ReaderCallableOneSignal>();
    }

    @Override
    public ReadResult call() {//dar soporte a lectura de mltiples senhales
        this.getLocks();
        //Hacer un wait//@comentario ¿Y hay que hacer algo para ello?
        //Mirar que ocurre si hace un CompletionServide un wait
        //Thread.sleep(millis);
        this.read();
        this.releaseLocks();
        return this.getReadResult();
    }

    protected ReadResult read() {
        ReadResultMultiSignal readResultMulti=new ReadResultMultiSignal(this.getIdentifierOwner());
        for (ReaderCallableOneSignal readerCallableOneSignal : readerCallables) {
            //@pendiente Este cast no debería estar pero ya me estoy liando un poco
            readResultMulti.addReadResultOneSignal((ReadResultOneSignal) readerCallableOneSignal.read());
        }
        return readResultMulti;

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
}
