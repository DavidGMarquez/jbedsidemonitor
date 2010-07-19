/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public class EventTest {

    public EventTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAttributes method, of class Event.
     */
   @Test
    public void testCrear1() {
        Event e=new Event(new Date().getTime(), "Standard", new HashMap<Object, Object>());
        String prueba=e.getType();
        prueba=new String("Hola");
        assertEquals(e.getType(),"Standard");
        assertEquals(new Date().getTime()>=e.getMoment(), true);

        // TODO review the generated test code and remove the default call to fail.
    }
   @Test
       public void testCrear2() {
            HashMap<Object,Object> map=new HashMap<Object,Object>();
            map.put("Hello", new Integer(1));
            map.put("Hello2", new Integer(2));
            map.put("Hello", new Integer(1));
        Event e=new Event(new Date().getTime(), "Standard", map);
        assertEquals(e.getAttributes().size(), 2);
        Map<Object, Object> mapcopy=e.getAttributes();
        mapcopy.remove("Hello2");
        assertEquals(mapcopy.size(), 1);
assertEquals(e.getAttributes().size(), 2);
 
        // TODO review the generated test code and remove the default call to fail.
    }

}