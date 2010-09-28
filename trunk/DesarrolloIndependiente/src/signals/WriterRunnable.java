/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import signals.LockManager;

/**
 *
 * @author USUARIO
 */
abstract class WriterRunnable implements Runnable {

    protected String identifier;
    protected LockManager lockManager;

    public WriterRunnable(String identifier) {
        this.identifier=identifier;
        this.lockManager = LockManager.getInstance();
    }

    public void run() {
        this.lockManager.getWriteLock(identifier);
        this.write();
        this.lockManager.releaseWriteLock(identifier);
    }

    abstract void write();
}
