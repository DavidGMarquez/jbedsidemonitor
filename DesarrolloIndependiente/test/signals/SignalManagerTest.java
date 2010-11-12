/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignalManagerTest {

    public SignalManagerTest() {
    }

    @Test
    public void testCrear() {
        SignalManager signalManager = SignalManager.getInstance();
        SignalManager aaa = SignalManager.getInstance();
        assertEquals(aaa, signalManager);
    }
}
