/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import auxiliarTools.AuxTestUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class LockManagerTest {

    public LockManagerTest() {
    }

    @Test
    public void testInstance() {
        AuxTestUtilities.reset();
        LockManager lockManager = LockManager.getInstance();
        assertEquals(lockManager, LockManager.getInstance());
        assertEquals(lockManager, lockManager.getInstance());
    }

    @Test
    public void testAddLock() {
                AuxTestUtilities.reset();
        LockManager lockManager = LockManager.getInstance();
        assertTrue(lockManager.addLock("Series1"));
        assertTrue(lockManager.addLock("Series2"));
        assertTrue(lockManager.addLock("Series3"));
        assertFalse(lockManager.addLock("Series3"));
        lockManager.getReadLock("Series1");
        lockManager.getReadLock("Series2");
        lockManager.getReadLock("Series3");
        assertFalse(lockManager.tryWriteLock("Series2"));
        lockManager.releaseReadLock("Series2");
        assertTrue(lockManager.tryWriteLock("Series2"));
        assertTrue(lockManager.tryReadLock("Series2"));
        assertTrue(lockManager.tryWriteLock("Series2"));
        //Segun esto es correcto que el thread coja el lock de escritura y luego el de lectura o otro de escritura
        //Pero si comienza con el de lectura tendría que liberarlo para coger el de escritura
    }
}
