/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

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
public class CircularBufferTest {

    public CircularBufferTest() {
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
     * Test de Creacion de 2 Buffers con los constructores
     */
    @Test
    public void testCrear() {
        CircularBuffer Test = new CircularBuffer();
        CircularBuffer Test10 = new CircularBuffer(10);

    }

    /**
     * Test de escritura de 3 numeros en un array más grande
     */
    @Test
    public void testWrite() {
        float[] datatowrite = new float[3];
        datatowrite[0] = 1;
        datatowrite[1] = 2;
        datatowrite[2] = 3;
        CircularBuffer instance = new CircularBuffer(5);
        boolean result = instance.write(datatowrite);
        assertEquals(true, result);
        assertEquals(true, compareArray(instance.getData(), datatowrite,
                datatowrite.length));
        assertEquals(0, instance.getIndexold());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test de escritura de 5 numeros en un buffer de 5
     */
    @Test
    public void testWrite2() {
        float[] datatowrite = new float[5];
        datatowrite[0] = 1;
        datatowrite[1] = 2;
        datatowrite[2] = 3;
        datatowrite[3] = 4;
        datatowrite[4] = 5;
        CircularBuffer instance = new CircularBuffer(5);
        boolean result = instance.write(datatowrite);
        assertEquals(true, result);
        assertEquals(true, compareArray(instance.getData(), datatowrite, datatowrite.length));
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(0, instance.getIndexold());
    }

    /**
     * Test de escritura de 6 numeros en un array más pequeño
     * El test falla ya que no tiene sentido escribir mas del tamaño del array
     */
    @Test
    public void testWrite3() {
        int sizetowrite = 6;
        float[] datatowrite = this.generateArray(sizetowrite);
        CircularBuffer instance = new CircularBuffer(5);
        boolean result = instance.write(datatowrite);
        assertEquals(false, result);
        assertEquals(0, instance.getIndexold());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test de escritura de 6 numeros en un array de 6 dos veces
     */
    @Test
    public void testWrite4() {
        int sizetowrite = 6;
        float[] datatowrite = null;
        datatowrite = this.generateArray(sizetowrite);
        CircularBuffer instance = new CircularBuffer(6);
        boolean result = instance.write(datatowrite);
        assertEquals(true, result);
        result = instance.write(datatowrite);
        assertEquals(true, result);
        assertEquals(true, compareArray(instance.getData(), datatowrite, sizetowrite));
        assertEquals(0, instance.getIndexold());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test de escritura de varias escrituras para que se desborde el array
     */
    @Test
    public void testWrite5() {
        int sizetowrite = 4;
        float[] datatowrite = {1, 2, 3, 4};
        CircularBuffer instance = new CircularBuffer(5);
        boolean result = instance.write(datatowrite);
        assertEquals(0, instance.getIndexold());
        result = instance.write(datatowrite);

        assertEquals(true, result);

        float[] datacompare = {2, 3, 4, 4, 1};
        assertEquals(true, compareArray(instance.getData(), datacompare, instance.getData().length));
        assertEquals(3, instance.getIndexold());
        float[] datacompare2 = {3, 4, 4, 1, 2};
        result = instance.write(datatowrite);
        assertEquals(true, compareArray(instance.getData(), datacompare2, instance.getData().length));

        assertEquals(true, result);
        assertEquals(2, instance.getIndexold());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test de lectura simple con un array asignado manualmente
     */
    @Test
    public void testRead1() {
        CircularBuffer instance = new CircularBuffer(5);
        float[] datacompare = {2, 3, 4, 4, 1};
        instance.setData(datacompare);
        float[] datacompare2 = {3, 4};
        float[] datacompare3 = {4, 4, 1};
        float[] datacompare4 = {1, 2, 3};
        assertEquals(true, compareArray(instance.read(1, 2), datacompare2, 2));

        assertEquals(true, compareArray(instance.read(2, 3), datacompare3, 3));
        assertEquals(true, compareArray(instance.read(4, 3), datacompare4, 3));

    }

    /**
     * Test de lectura y escritura completo
     */
    @Test
    public void testReadWrite1() {
        CircularBuffer instance = new CircularBuffer(9);
        float[] array1 = generateArray(5);
        float[] array2 = generateArray(5);
        float[] array3 = generateArray(5);
        instance.write(array1);
        assertEquals(0, instance.getIndexold());

        assertEquals(true, compareArray(array1, instance.read(0, 5), 5));
        instance.write(array2);
        assertEquals(1, instance.getIndexold());
        assertEquals(true, compareArray(array2, instance.read(5, 5), 5));
        instance.write(array3);
        assertEquals(6, instance.getIndexold());
        assertEquals(true, compareArray(array3, instance.read(1, 5), 5));
    }

    /**
     * Test de lectura y escritura completo 2
     */
    @Test
    public void testReadWrite2() {
        CircularBuffer instance = new CircularBuffer(9);
        float[] array1 = {11, 12, 13, 14, 15, 16, 17};
        float[] array2 = {21, 22, 23, 24, 25};
        float[] array3 = {31, 32, 33};
        instance.write(array1);
        assertEquals(0, instance.getIndexold());

        assertEquals(true, compareArray(array1, instance.read(0, 7), 7));
        instance.write(array1);
        assertEquals(5, instance.getIndexold());

        assertEquals(false, compareArray(array1, instance.read(0, 7), 7));
        float[] result1 = {13, 14, 15, 16, 17, 16, 17, 11, 12};
        assertEquals(true, compareArray(instance.read(0, 7), result1, 7));
        instance.write(array3);
        assertEquals(8, instance.getIndexold());

        assertEquals(false, compareArray(array1, instance.read(0, 7), 7));
        float[] result2 = {13, 14, 15, 16, 17, 31, 32, 33, 12};
        assertEquals(true, compareArray(result2, instance.read(0, 9), 9));
        instance.write(array2);
        assertEquals(4, instance.getIndexold());

        float[] result3 = {22, 23,  24, 25, 17,31, 32, 33, 21};
        assertEquals(true, compareArray(result3, instance.read(0, 9), 9));
        instance.write(array1);
        float[] result4 = {16, 17,  24, 25, 11,12, 13, 14, 15};
        assertEquals(true, compareArray(result4, instance.read(0, 9), 9));
        assertEquals(2, instance.getIndexold());
        instance.write(array2);
        float[] result5 = {16, 17,  21, 22, 23, 24, 25, 14, 15};
        assertEquals(true, compareArray(result5, instance.read(0, 9), 9));
        assertEquals(7, instance.getIndexold());

        instance.write(array1);
        float[] result6 = {13, 14, 15, 16, 17, 24, 25, 11, 12};
        assertEquals(true, compareArray(result6, instance.read(0, 9), 9));
        assertEquals(5, instance.getIndexold());

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
