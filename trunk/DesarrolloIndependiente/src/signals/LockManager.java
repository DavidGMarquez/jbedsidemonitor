package signals;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockManager {

    private static final LockManager INSTANCE = new LockManager();
    private ConcurrentMap<String, ReentrantReadWriteLock> signalsLocks;

    public LockManager() {
        signalsLocks = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
    }

    public static LockManager getInstance() {
        return INSTANCE;
    }

    public boolean addLock(String identifier) {
        if (signalsLocks.put(identifier, new ReentrantReadWriteLock()) == null) {
            return true;
        } else {
            return false;
        }
    }

    public void getReadLock(String identifier) {
        this.signalsLocks.get(identifier).readLock().lock();
    }

    public void releaseReadLock(String identifier) {
        this.signalsLocks.get(identifier).readLock().unlock();
    }

    public void getWriteLock(String identifier) {
        this.signalsLocks.get(identifier).writeLock().lock();
    }

    public void releaseWriteLock(String identifier) {
        this.signalsLocks.get(identifier).writeLock().unlock();
    }
}
