/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.Date;
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
public class TimeSeriesTest {

    public TimeSeriesTest() {
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

    @Test
    public void TestCrear() {
        TimeSeries TS = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        assertEquals(TS.getAgent(), "Watson");
        assertEquals(TS.getIdentifier(), "Series1");
        assertEquals(new Date().getTime() >= TS.getOrigin(), true);
        assertEquals(100000, TS.getCapacity(), 0.0001);
        assertEquals(10, TS.getFrequency(), 0.0001);
        assertEquals(TS.getSamplescounter(), 0);
        assertEquals("mV", TS.getUnits());
    }

    @Test
    public void TestEscribirLeer() {
        TimeSeries TS = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 1, "mV");
        float[] datatowrite = null;
        datatowrite = this.generateArray(3000);
        TS.write(datatowrite);
        assertEquals(compareArray(TS.read(0, 3000), datatowrite, 3000), true);
        assertEquals(1 * 3600 * 6, TS.getCapacity(), 0.0001);
    }

    @Test
    public void TestEscribirLeerMucho() {
        TimeSeries TS = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
        float[] datatowrite = null;
        datatowrite = this.generateArray(75000);
        assertEquals(TS.getIndexNewsample(), -1);
        assertEquals(TS.getIndexOldsample(), -1);
        assertEquals(TS.getSamplescounter(), 0);
        System.out.println(datatowrite.length);
        System.out.println(TS.getCapacity());
        TS.write(datatowrite);
        assertEquals(TS.getIndexNewsample(), 74999);
        assertEquals(TS.getIndexOldsample(), 0);
        assertEquals(TS.getSamplescounter(), 75000);
        assertEquals(compareArray(TS.read(0, 75000), datatowrite, 75000), true);
        datatowrite = this.generateArray(75000);
        TS.write(datatowrite);
        assertEquals(TS.getIndexNewsample(), 49999);
        assertEquals(TS.getIndexOldsample(), 50000);
        assertEquals(TS.getSamplescounter(), 100000);
        assertEquals(compareArray(TS.read(75000, 75000), datatowrite, 75000), true);
    }
    @Test
    public void TestDesbordar(){
        TimeSeries TS = new TimeSeries("Series1", "Watson", new Date().getTime(), (float) 10, "mV");
         float[] datatowrite = null;
         try{
           datatowrite = this.generateArray(100001);
           TS.write(datatowrite);
           fail("Deberia haber fallado");
         }
        catch(TooMuchDataToWriteException e)
        {
            System.out.println(e.getMessage());
        }
         catch(Exception e)
         {
             System.out.println(e.getMessage());
         }
    }


    public void mostrarArray(float[] array) {
        System.out.println("-");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        System.out.println("-");
    }

    public boolean compareArray(float[] array1, float[] array2, int tam) {
        int acum = 0;
        for (int i = 0; i < tam; i++) {
            if (array1[i] != array2[i]) {
                acum++;
            }
        }
        if (acum == 0) {
            return true;
        } else {
            return false;
        }
    }

    public float[] generateArray(int tam) {
        float[] array = new float[tam];
        for (int i = 0; i < tam; i++) {
            array[i] = (float) Math.random() * 100;
        }

        return array;
    }
}
