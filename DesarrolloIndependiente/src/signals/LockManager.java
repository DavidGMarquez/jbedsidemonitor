/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *@todo nuevamente, me parece Intuitivo implementar esto partir del
 * identificador de las señales.
 * @author USUARIO
 */
public class LockManager {

    private static final LockManager INSTANCE = new LockManager();

    private ArrayList<ReentrantReadWriteLock> signalsLocks;

    public LockManager() {
        signalsLocks = new ArrayList<ReentrantReadWriteLock>();
    }

    public static LockManager getInstance() {
        return INSTANCE;
    }

    public boolean addLock() {
        return signalsLocks.add(new ReentrantReadWriteLock());
    }
//no entiendo qué poltica desincronizacion vas a usar.
    //tenemos que hablar de ese tema
    public void addNLocks(int N) {
        for (int i = 0; i < N; i++) {
            this.addLock();
        }
    }
    public int getNumberOfLocks(){
        return this.signalsLocks.size();
    }

    public void getReadLock(int index)    {
        this.signalsLocks.get(index).readLock().lock();
    }
    public void releaseReadLock(int index){
        this.signalsLocks.get(index).readLock().unlock();
    }
    public void getWriteLock(int index){
        this.signalsLocks.get(index).writeLock().lock();
    }
    public void releaseWriteLock(int index){
        this.signalsLocks.get(index).writeLock().unlock();
    }
}
