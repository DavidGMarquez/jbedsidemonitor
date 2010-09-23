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
     * Test of getCopyOfAttributes method, of class Event.
     */
   @Test
    public void testCrear1() {
        Event e=new Event(new Date().getTime(), "Standard", null);
        String prueba=e.getType();
        prueba=new String("Hola");
        assertEquals(e.getType(),"Standard");
        assertEquals(new Date().getTime()>=e.getLocation(), true);

        // TODO review the generated test code and remove the default call to fail.
    }
   
   @Test
       public void testCrear2() {
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("Hello", new Integer(1).toString());
            map.put("Hello2", new Integer(2).toString());
            map.put("Hello", new Integer(1).toString());
        Event e=new Event(new Date().getTime(), "Standard", map);
        assertEquals(e.getCopyOfAttributes().size(), 2);
        Map<String, String> mapcopy=e.getCopyOfAttributes();
            mapcopy.put("Hello2", new Integer(20).toString());
            map.put("Hello2", new Integer(2000).toString());
        Integer i=new Integer(Integer.parseInt((String)mapcopy.get("Hello2")));
        System.out.println(i.intValue());
        mapcopy.remove("Hello2");
        assertEquals(mapcopy.get("Hello2"), null);

        

        assertEquals(mapcopy.size(), 1);
assertEquals(e.getCopyOfAttributes().size(), 2);
        Integer i2=new Integer(Integer.parseInt((String)e.getCopyOfAttributes().get("Hello2")));
        System.out.println(i2.intValue());
        assertEquals(i2.intValue(), 2);
 
        // TODO review the generated test code and remove the default call to fail.
    }

}