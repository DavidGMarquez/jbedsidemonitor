package signals;

import auxiliarTools.AuxTestUtilities;
import org.junit.Test;
import static org.junit.Assert.*;

public class CircularBufferTest {

    public CircularBufferTest() {
    }

    @Test
    public void testConstructor() {
        CircularBuffer bufferWithAcapacityOff10000 = new CircularBuffer();
        assertEquals(10000, bufferWithAcapacityOff10000.getCapacity());
        CircularBuffer bufferWithAcapacityOff10 = new CircularBuffer(10);
        assertEquals(10, bufferWithAcapacityOff10.getCapacity());
    }

    @Test
    public void write3Numbers() {
        float[] dataToWrite = {1, 2, 3};
        CircularBuffer buffer = new CircularBuffer(5);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(3, result[1]);
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(0, buffer.getSize()), dataToWrite, dataToWrite.length));

        assertEquals(0, buffer.getIndexold());
    }

    @Test
    public void write5NumbersAndCheckIndexold() {
        float[] dataToWrite = {1, 2, 3, 4, 5};
        CircularBuffer buffer = new CircularBuffer(5);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(5, result[1]);

        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(0, buffer.getSize()),
                dataToWrite, dataToWrite.length));
        assertEquals(0, buffer.getIndexold());
    }

    @Test
    public void write6RandomNumbersAndCheckIndexold() {
        int sizetowrite = 6;
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArray(sizetowrite);
        CircularBuffer buffer = new CircularBuffer(6);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(6, result[1]);
        result = buffer.write(dataToWrite, 6);
        assertEquals(6, result[0]);
        assertEquals(6, result[1]);
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(6, buffer.getSize()), dataToWrite, sizetowrite));
        assertEquals(0, buffer.getIndexold());
    }

    @Test
    public void testWriteSeveralTimesAndCheckData() {
        float[] dataToWrite = {1, 2, 3, 4};
        CircularBuffer buffer = new CircularBuffer(5);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(4, result[1]);
        assertEquals(0, buffer.getIndexold());
        result = buffer.write(dataToWrite, 4);
        assertEquals(3, result[0]);
        assertEquals(5, result[1]);
        float[] datacompare = {4, 1, 2, 3, 4};
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(3,
                buffer.getSize()), datacompare, buffer.read(3, buffer.getSize()).length));
        assertEquals(3, buffer.getIndexold());
        float[] datacompare2 = {4, 1, 2, 3, 4};
        result = buffer.write(dataToWrite, 8);
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(7,
                buffer.getSize()), datacompare2, buffer.read(7, buffer.getSize()).length));
        assertEquals(7, result[0]);
        assertEquals(5, result[1]);
        assertEquals(2, buffer.getIndexold());
    }

    @Test
    public void writeManyNumbersAndCheckIndexoldAndLastData() {
        float[] dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(0, 999);
        CircularBuffer buffer = new CircularBuffer(1000);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(999, result[1]);
        assertEquals(0, buffer.getIndexold());
        assertEquals(998, buffer.getLastData(), 0.1);
        result = buffer.write(dataToWrite, 999);
        assertEquals(998, result[0]);
        assertEquals(1000, result[1]);
        assertEquals(998, buffer.getIndexold());
        assertEquals(998, buffer.getLastData(), 0.1);
        dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(-2, 5);
        result = buffer.write(dataToWrite, 1998);
        assertEquals(1003, result[0]);
        assertEquals(1000, result[1]);
        assertEquals(3, buffer.getIndexold());
        assertEquals(2, buffer.getLastData(), 0.1);
    }

    @Test
    public void TestIllegalReadException() {
        CircularBuffer buffer = new CircularBuffer(5);
        float[] datacompare = {2, 3, 4, 4, 1};
        try {
            buffer.read(-1, 1);
            fail("Deberia haber dado una excepcion al leer datos desde una posicion negativa");
        } catch (IllegalReadException e) {
        }
        buffer.write(datacompare, 0);
        float[] datacompare2 = {3, 4};
        float[] datacompare3 = {4, 4, 1};
        float[] datacompare4 = {1, 2, 3};
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(1, 2), datacompare2, 2));
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(2, 3), datacompare3, 3));
        try {
            assertEquals(true, AuxTestUtilities.compareArray(buffer.read(4, 3), datacompare4, 3));
            fail("Deberia haber dado una excepcion al leer datos del futuro");
        } catch (IllegalReadException e) {
        }
        try {
            buffer.read(-3, 1);
            fail("Deberia haber dado una excepcion al leer datos desde una posicion negativa");
        } catch (IllegalReadException e) {
        }
        try {
            buffer.read(-1, 3);
            fail("Deberia haber dado una excepcion al leer datos desde una posicion negativa");
        } catch (IllegalReadException e) {
        }
        buffer.write(datacompare, 5);
        try {
            buffer.read(3, 3);
            fail("Deberia haber dado una excepcion al leer datos que ya no estan disponibles");
        } catch (IllegalReadException e) {
        }
        try {
            buffer.read(4, 1);
            fail("Deberia haber dado una excepcion al leer datos que ya no estan disponibles");
        } catch (IllegalReadException e) {
        }
        try {
            buffer.read(1, 10);
            fail("Deberia haber dado una excepcion al leer datos que ya no estan disponibles");
        } catch (IllegalReadException e) {
        }
    }

    @Test
    public void readWhitRandomNumbers() {
        CircularBuffer buffer = new CircularBuffer(9);
        float[] array1 = AuxTestUtilities.generateArray(5);
        float[] array2 = AuxTestUtilities.generateArray(5);
        float[] array3 = AuxTestUtilities.generateArray(5);
        buffer.write(array1, 0);
        assertEquals(0, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(0, 5), 5));
        buffer.write(array2, 5);
        assertEquals(1, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 5), 5));
        buffer.write(array3, 10);
        assertEquals(6, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(10, 5), 5));
    }

    @Test
    public void complexReadTest() {
        CircularBuffer instance = new CircularBuffer(9);
        float[] array1 = {11, 12, 13, 14, 15, 16, 17};
        float[] array2 = {21, 22, 23, 24, 25};
        float[] array3 = {31, 32, 33};
        instance.write(array1, 0);
        assertEquals(0, instance.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array1, instance.read(0, 7), 7));
        instance.write(array1, 7);
        assertEquals(5, instance.getIndexold());
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(6, 7), 7));
        float[] result1 = {16, 17, 11, 12, 13, 14, 15, 16, 17};
        assertEquals(true, AuxTestUtilities.compareArray(instance.read(5, 7), result1, 7));
        instance.write(array3, 14);
        assertEquals(8, instance.getIndexold());
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(10, 7), 7));
        float[] result2 = {12, 13, 14, 15, 16, 17, 31, 32, 33};
        assertEquals(true, AuxTestUtilities.compareArray(result2, instance.read(8, 9), 9));
        instance.write(array2, 17);
        assertEquals(4, instance.getIndexold());
        float[] result3 = {17, 31, 32, 33, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result3, instance.read(13, 9), 9));
        instance.write(array1, 23);
        float[] result4 = {25, Float.NaN, 11, 12, 13, 14, 15, 16, 17};
        assertEquals(true, AuxTestUtilities.compareArray(result4, instance.read(21, 9), 9));
        instance.write(array2, 31);
        float[] result5 = {15, 16, 17, Float.NaN, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result5, instance.read(27, 9), 9));
        System.out.println("Capacidad" + instance.getCapacity() + " lastsample" + instance.getLastSampleWrite() + " indexOld" + instance.getIndexold() + " IndexNExt" + instance.getIndexNextWrite() + " SamplesWrites" + instance.getNumberOfSamplesWrite());
        //assertEquals(27, instance.getIndexold());
        instance.write(array2, 33);
        float[] result6 = {Float.NaN, 21, 22, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result6, instance.read(30, 8), 8));
//        assertEquals(5, instance.getIndexold());

    }

    @Test
    public void testTooMuchDataToWriteException() {
        CircularBuffer instance = new CircularBuffer(7);
        float[] array1 = AuxTestUtilities.generateArray(7);
        instance.write(array1, 0);
        CircularBuffer instance2 = new CircularBuffer(6);
        try {
            instance2.write(array1, 0);
            fail("Debería haber saltado la excepcion");
        } catch (TooMuchDataToWriteException e) {
            System.out.println(e.getMessage());
        }
        CircularBuffer instance3 = new CircularBuffer(3);
        try {
            instance3.write(array1, 0);
            fail("Debería haber saltado la excepcion");
        } catch (TooMuchDataToWriteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIllegalReadException() {
        CircularBuffer instance = new CircularBuffer(20);
        float[] array1 = AuxTestUtilities.generateArray(15);
        instance.write(array1, 0);
        instance.write(array1, 15);

        try {
            instance.read(0, 0);
            fail("Debería haber saltado la excepcion");
        } catch (IllegalReadException e) {
            System.out.println(e.getMessage());
        }
        try {
            instance.read(0, 5);
            fail("Debería haber saltado la excepcion");
        } catch (IllegalReadException e) {
            System.out.println(e.getMessage());
        }
        try {
            instance.read(5, 15);
            fail("Debería haber saltado la excepcion");
        } catch (IllegalReadException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(AuxTestUtilities.compareArray(array1, instance.read(15, 15), 15));


    }

    @Test
    public void write3NumbersOrder() {
        float[] dataToWrite = {1, 2, 3};
        CircularBuffer buffer = new CircularBuffer(5);
        int[] result = buffer.write(dataToWrite, 0);
        assertEquals(0, result[0]);
        assertEquals(3, result[1]);
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(0, 3), dataToWrite, dataToWrite.length));
        buffer = new CircularBuffer(5);
        buffer.write(dataToWrite, 1);

        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(1, 3), dataToWrite, dataToWrite.length));

    }

    @Test
    public void readWhitRandomNumbersOrder() {
        CircularBuffer buffer = new CircularBuffer(9);
        float[] array1 = AuxTestUtilities.generateArray(5);
        float[] array2 = AuxTestUtilities.generateArray(5);
        float[] array3 = AuxTestUtilities.generateArray(5);
        buffer.write(array1, 0);
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(0, 5), 5));
        buffer.write(array2, 0);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(0, 5), 5));
        buffer.write(array2, 5);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 5), 5));
        buffer.write(array3, 10);
        assertEquals(6, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(10, 5), 5));

    }

    @Test
    public void writeAndReadWithNanSimple() {
        CircularBuffer buffer = new CircularBuffer(100);
        float[] array1 = AuxTestUtilities.generateArray(50);
        float[] array2 = AuxTestUtilities.generateArray(10);
        float[] array3 = AuxTestUtilities.generateArray(5);
        buffer.write(array1, 20);
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(20, 50), 50));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 20)));
        buffer.write(array2, 5);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 5)));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(15, 5)));
        buffer.write(array3, 0);
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(0, 5), 5));
        buffer.write(array3, 15);
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(15, 5), 5));
        buffer.write(array3, 70);
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(70, 5), 5));
        buffer.write(array2, 80);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(80, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(75, 5)));
        buffer.write(array3, 75);

        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(75, 5), 5));

        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(0, 5), 5));
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 10), 10));
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(15, 5), 5));
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(20, 50), 50));
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(70, 5), 5));
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(75, 5), 5));
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(80, 10), 10));
    }

    @Test
    public void methodsToReadInOrder() {
        CircularBuffer buffer = new CircularBuffer(100);
        float[] array1 = AuxTestUtilities.generateArray(50);
        float[] array2 = AuxTestUtilities.generateArray(10);
        float[] array3 = AuxTestUtilities.generateArray(5);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(-34), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(-1), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(90), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(130), 0);
        buffer.write(array1, 20);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(buffer.getSampleInitToReadInOrder()), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(20), 50);
        assertEquals(buffer.getSamplesReadyToReadInOrder(30), 40);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(90), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(130), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(20, 50), 50));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 20)));
        buffer.write(array2, 5);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(10), 5);
        assertEquals(buffer.getSamplesReadyToReadInOrder(5), 10);
        assertEquals(buffer.getSamplesReadyToReadInOrder(15), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(20), 50);
        assertEquals(buffer.getSamplesReadyToReadInOrder(30), 40);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(90), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(130), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 5)));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(15, 5)));
        buffer.write(array3, 0);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 15);
        assertEquals(buffer.getSamplesReadyToReadInOrder(5), 10);
        assertEquals(buffer.getSamplesReadyToReadInOrder(10), 5);
        assertEquals(buffer.getSamplesReadyToReadInOrder(5), 10);
        assertEquals(buffer.getSamplesReadyToReadInOrder(15), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(20), 50);
        assertEquals(buffer.getSamplesReadyToReadInOrder(30), 40);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(90), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(130), 0);
        buffer.write(array3, 15);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 70);
        assertEquals(buffer.getSamplesReadyToReadInOrder(45), 25);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 0);


        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(0, 5), 5));
        buffer.write(array3, 15);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 70);
        assertEquals(buffer.getSamplesReadyToReadInOrder(45), 25);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(15, 5), 5));
        buffer.write(array3, 70);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 75);
        assertEquals(buffer.getSamplesReadyToReadInOrder(45), 30);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 5);


        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(70, 5), 5));
        buffer.write(array2, 80);
        assertEquals(buffer.getSampleInitToReadInOrder(), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 75);
        assertEquals(buffer.getSamplesReadyToReadInOrder(45), 30);
        assertEquals(buffer.getSamplesReadyToReadInOrder(70), 5);
        assertEquals(buffer.getSamplesReadyToReadInOrder(75), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(80), 10);
        assertEquals(buffer.getSamplesReadyToReadInOrder(90), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(80, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(75, 5)));
        buffer.write(array1, 110);
        assertEquals(buffer.getSampleInitToReadInOrder(), 60);
        assertEquals(buffer.getSamplesReadyToReadInOrder(60), 15);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(180), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(110), 50);
        assertEquals(buffer.getSamplesReadyToReadInOrder(150), 10);
        assertEquals(buffer.getSamplesReadyToReadInOrder(75), 0);
        buffer.write(array1, 150);
        buffer.write(array1, 180);
        assertEquals(buffer.getSampleInitToReadInOrder(), 130);
        assertEquals(buffer.getSamplesReadyToReadInOrder(130), 100);
        assertEquals(buffer.getSamplesReadyToReadInOrder(0), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(80), 0);
        assertEquals(buffer.getSamplesReadyToReadInOrder(150), 80);
        assertEquals(buffer.getSamplesReadyToReadInOrder(200), 30);
        assertEquals(buffer.getSamplesReadyToReadInOrder(230), 0);

    }
}
