/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author USUARIO
 */
public class LockManager {

    private ArrayList<ReentrantReadWriteLock> signalsLocks;

    public LockManager() {
        signalsLocks = new ArrayList<ReentrantReadWriteLock>();
    }
    private static final LockManager INSTANCE = new LockManager();

    public static LockManager getInstance() {
        return INSTANCE;
    }

    public boolean addLock() {
        return signalsLocks.add(new ReentrantReadWriteLock());
    }

    public void addNLocks(int N) {
        for (int i = 0; i < N; i++) {
            this.addLock();
        }
    }
    public int getNumberOfLocks(){
        return this.signalsLocks.size();
    }
        public void getReadLock(int index)
    {
        this.signalsLocks.get(index).readLock().lock();
    }
    public void releaseReadLock(int index)
    {
        this.signalsLocks.get(index).readLock().unlock();
    }
    public void getWriteLock(int index)
    {
        this.signalsLocks.get(index).writeLock().lock();
    }
    public void releaseWriteLock(int index)
    {
        this.signalsLocks.get(index).writeLock().unlock();
    }
}
