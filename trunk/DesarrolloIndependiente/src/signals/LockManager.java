package signals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *@todo Es necesario emplear una colección de datos con soporte a concurrencia
 * o esta clase no es thread safe
 * @author USUARIO
 */
public class LockManager {

    private static final LockManager INSTANCE = new LockManager();
    private Map<String, ReentrantReadWriteLock> signalsLocks;

    public LockManager() {
        signalsLocks = new HashMap<String, ReentrantReadWriteLock>();
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
