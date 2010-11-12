package signals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void simpleConstructor() {
        Event event = new Event(new Date().getTime(), "Standard", null);      
        assertEquals(event.getType(), "Standard");
        assertEquals(new Date().getTime() >= event.getLocation(), true);
    }

    @Test
    public void constructor() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Hello", new Integer(1).toString());
        map.put("Hello2", new Integer(2).toString());
        map.put("Hello", new Integer(1).toString());
        Event e = new Event(new Date().getTime(), "Standard", map);
        assertEquals(e.getCopyOfAttributes().size(), 2);
        Map<String, String> mapcopy = e.getCopyOfAttributes();
        mapcopy.put("Hello2", new Integer(20).toString());
        map.put("Hello2", new Integer(2000).toString());
        Integer i = new Integer(Integer.parseInt((String) mapcopy.get("Hello2")));
        assertEquals(i.intValue(), 20);
        mapcopy.remove("Hello2");
        assertEquals(mapcopy.get("Hello2"), null);
        assertEquals(mapcopy.size(), 1);
        assertEquals(e.getCopyOfAttributes().size(), 2);
        Integer i2 = new Integer(Integer.parseInt((String) e.getCopyOfAttributes().get("Hello2")));
        assertEquals(i2.intValue(), 2);
    }


    @Test
    public void testEquals() {
        Event eType1 = new Event(10, "Type1", null);
        Event eType1Bis = new Event(10, "Type1", null);
        Event eType11 = new Event(11, "Type1", null);
        Event eType2 = new Event(10, "Type2", null);

        assertEquals(true, eType1.equals(eType1));
        assertEquals(true, eType1Bis.equals(eType1));
        assertEquals(true, eType1.equals(eType1Bis));
        assertEquals(false, eType11.equals(eType1));
        assertEquals(false, eType1.equals(eType11));
        assertEquals(false, eType2.equals(eType1));
        assertEquals(false, eType1.equals(eType2));
        assertEquals(false, eType1.equals(new Object()));
    }


    @Test
    public void testCompare() {
        Event eType1 = new Event(10, "Type1", null);
        Event eType1Bis = new Event(10, "Type1", null);
        Event eType11 = new Event(11, "Type1", null);
        Event eType2 = new Event(10, "Type2", null);

        assertEquals(true, eType1.compareTo(eType1) == 0);
        assertEquals(true, eType1Bis.compareTo(eType1) == 0);
        assertEquals(true, eType1.compareTo(eType1Bis) == 0);
        assertEquals(true, eType11.compareTo(eType1) > 0);
        assertEquals(true, eType1.compareTo(eType11) < 0);
        assertEquals(true, eType2.compareTo(eType1) > 0);
        assertEquals(true, eType1.compareTo(eType2) < 0);
    }
}
