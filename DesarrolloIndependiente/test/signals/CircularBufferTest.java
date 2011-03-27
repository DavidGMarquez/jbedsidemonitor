package signals;

import signals.CircularBuffer.ConsecutiveSamplesAvailableInfo;
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
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(3, result.getSamplesReadyToReadInOrder());
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(0, buffer.getSize()), dataToWrite, dataToWrite.length));
    }

    @Test
    public void write5NumbersAndCheckIndexold() {
        float[] dataToWrite = {1, 2, 3, 4, 5};
        CircularBuffer buffer = new CircularBuffer(5);
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(5, result.getSamplesReadyToReadInOrder());

        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(0, buffer.getSize()),
                dataToWrite, dataToWrite.length));
    }

    @Test
    public void write6RandomNumbersAndCheckIndexold() {
        int sizetowrite = 6;
        float[] dataToWrite = null;
        dataToWrite = AuxTestUtilities.generateArray(sizetowrite);
        CircularBuffer buffer = new CircularBuffer(6);
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(6, result.getSamplesReadyToReadInOrder());
        result = buffer.write(dataToWrite, 6);
        assertEquals(6, result.getOlderSampleAvailable());
        assertEquals(6, result.getSamplesReadyToReadInOrder());
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(6, buffer.getSize()), dataToWrite, sizetowrite));
    }

    @Test
    public void testWriteSeveralTimesAndCheckData() {
        float[] dataToWrite = {1, 2, 3, 4};
        CircularBuffer buffer = new CircularBuffer(5);
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(4, result.getSamplesReadyToReadInOrder());
        result = buffer.write(dataToWrite, 4);
        assertEquals(3, result.getOlderSampleAvailable());
        assertEquals(5, result.getSamplesReadyToReadInOrder());
        float[] datacompare = {4, 1, 2, 3, 4};
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(3,
                buffer.getSize()), datacompare, buffer.read(3, buffer.getSize()).length));
        float[] datacompare2 = {4, 1, 2, 3, 4};
        result = buffer.write(dataToWrite, 8);
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(7,
                buffer.getSize()), datacompare2, buffer.read(7, buffer.getSize()).length));
        assertEquals(7, result.getOlderSampleAvailable());
        assertEquals(5, result.getSamplesReadyToReadInOrder());
    }

    @Test
    public void writeManyNumbersAndCheckIndexoldAndLastData() {
        float[] dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(0, 999);
        CircularBuffer buffer = new CircularBuffer(1000);
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(999, result.getSamplesReadyToReadInOrder());
        assertEquals(998, buffer.getLastData(), 0.1);
        result = buffer.write(dataToWrite, 999);
        assertEquals(998, result.getOlderSampleAvailable());
        assertEquals(1000, result.getSamplesReadyToReadInOrder());
        assertEquals(998, buffer.getLastData(), 0.1);
        dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(-2, 5);
        result = buffer.write(dataToWrite, 1998);
        assertEquals(1003, result.getOlderSampleAvailable());
        assertEquals(1000, result.getSamplesReadyToReadInOrder());
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
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(0, 5), 5));
        buffer.write(array2, 5);
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 5), 5));
        buffer.write(array3, 10);
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(10, 5), 5));
    }

    @Test
    public void complexReadTest() {
        CircularBuffer instance = new CircularBuffer(9);
        float[] array1 = {11, 12, 13, 14, 15, 16, 17};
        float[] array2 = {21, 22, 23, 24, 25};
        float[] array3 = {31, 32, 33};
        instance.write(array1, 0);
        assertEquals(true, AuxTestUtilities.compareArray(array1, instance.read(0, 7), 7));
        instance.write(array1, 7);
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(6, 7), 7));
        float[] result1 = {16, 17, 11, 12, 13, 14, 15, 16, 17};
        assertEquals(true, AuxTestUtilities.compareArray(instance.read(5, 7), result1, 7));
        instance.write(array3, 14);
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(10, 7), 7));
        float[] result2 = {12, 13, 14, 15, 16, 17, 31, 32, 33};
        assertEquals(true, AuxTestUtilities.compareArray(result2, instance.read(8, 9), 9));
        instance.write(array2, 17);
        float[] result3 = {17, 31, 32, 33, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result3, instance.read(13, 9), 9));
        instance.write(array1, 23);
        float[] result4 = {25, Float.NaN, 11, 12, 13, 14, 15, 16, 17};
        assertEquals(true, AuxTestUtilities.compareArray(result4, instance.read(21, 9), 9));
        instance.write(array2, 31);
        float[] result5 = {15, 16, 17, Float.NaN, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result5, instance.read(27, 9), 9));
        instance.write(array2, 33);
        float[] result6 = {Float.NaN, 21, 22, 21, 22, 23, 24, 25};
        assertEquals(true, AuxTestUtilities.compareArray(result6, instance.read(30, 8), 8));

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
        ConsecutiveSamplesAvailableInfo result = buffer.write(dataToWrite, 0);
        assertEquals(0, result.getOlderSampleAvailable());
        assertEquals(3, result.getSamplesReadyToReadInOrder());
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
        float[] array50 = AuxTestUtilities.generateArray(50);
        float[] array10 = AuxTestUtilities.generateArray(10);
        float[] array5 = AuxTestUtilities.generateArray(5);

        ConsecutiveSamplesAvailableInfo result = buffer.write(array50, 20);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(result.getOlderSampleAvailable()), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(20), 50);
        assertEquals(result.calculateSamplesReadyToReadInOrder(30), 40);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(90), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(130), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array50, buffer.read(20, 50), 50));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 20)));

        result = buffer.write(array10, 5);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(10), 5);
        assertEquals(result.calculateSamplesReadyToReadInOrder(5), 10);
        assertEquals(result.calculateSamplesReadyToReadInOrder(15), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(20), 50);
        assertEquals(result.calculateSamplesReadyToReadInOrder(30), 40);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(90), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(130), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array10, buffer.read(5, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(0, 5)));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(15, 5)));

        result = buffer.write(array5, 0);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 15);
        assertEquals(result.calculateSamplesReadyToReadInOrder(5), 10);
        assertEquals(result.calculateSamplesReadyToReadInOrder(10), 5);
        assertEquals(result.calculateSamplesReadyToReadInOrder(5), 10);
        assertEquals(result.calculateSamplesReadyToReadInOrder(15), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(20), 50);
        assertEquals(result.calculateSamplesReadyToReadInOrder(30), 40);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(90), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(130), 0);

        result = buffer.write(array5, 15);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 70);
        assertEquals(result.calculateSamplesReadyToReadInOrder(45), 25);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array5, buffer.read(0, 5), 5));

        result = buffer.write(array5, 15);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 70);
        assertEquals(result.calculateSamplesReadyToReadInOrder(45), 25);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array5, buffer.read(15, 5), 5));

        result = buffer.write(array5, 70);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 75);
        assertEquals(result.calculateSamplesReadyToReadInOrder(45), 30);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 5);
        assertEquals(true, AuxTestUtilities.compareArray(array5, buffer.read(70, 5), 5));

        result = buffer.write(array10, 80);
        assertEquals(result.getOlderSampleAvailable(), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 75);
        assertEquals(result.calculateSamplesReadyToReadInOrder(45), 30);
        assertEquals(result.calculateSamplesReadyToReadInOrder(70), 5);
        assertEquals(result.calculateSamplesReadyToReadInOrder(75), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(80), 10);
        assertEquals(result.calculateSamplesReadyToReadInOrder(90), 0);
        assertEquals(true, AuxTestUtilities.compareArray(array10, buffer.read(80, 10), 10));
        assertTrue(AuxTestUtilities.containsNAN(buffer.read(75, 5)));

        result = buffer.write(array50, 110);
        assertEquals(result.getOlderSampleAvailable(), 60);
        assertEquals(result.calculateSamplesReadyToReadInOrder(60), 15);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(180), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(110), 50);
        assertEquals(result.calculateSamplesReadyToReadInOrder(150), 10);
        assertEquals(result.calculateSamplesReadyToReadInOrder(75), 0);

        result = buffer.write(array50, 150);
        result = buffer.write(array50, 180);
        assertEquals(result.getOlderSampleAvailable(), 130);
        assertEquals(result.calculateSamplesReadyToReadInOrder(130), 100);
        assertEquals(result.calculateSamplesReadyToReadInOrder(0), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(80), 0);
        assertEquals(result.calculateSamplesReadyToReadInOrder(150), 80);
        assertEquals(result.calculateSamplesReadyToReadInOrder(200), 30);
        assertEquals(result.calculateSamplesReadyToReadInOrder(230), 0);

    }
}
