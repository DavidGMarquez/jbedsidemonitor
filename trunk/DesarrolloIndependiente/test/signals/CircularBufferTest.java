package signals;

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
        boolean result = buffer.write(dataToWrite);
        assertEquals(true, result);
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(0, buffer.getSize()), dataToWrite, dataToWrite.length));
        assertEquals(0, buffer.getIndexold());
    }

    @Test
    public void write5NumbersAndCheckIndexold() {
        float[] dataToWrite = {1, 2, 3, 4, 5};
        CircularBuffer buffer = new CircularBuffer(5);
        boolean result = buffer.write(dataToWrite);
        assertEquals(true, result);
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
        boolean result = buffer.write(dataToWrite);
        assertEquals(true, result);
        result = buffer.write(dataToWrite);
        assertEquals(true, result);
        assertEquals(true, AuxTestUtilities.compareArray(
                buffer.read(0, buffer.getSize()), dataToWrite, sizetowrite));
        assertEquals(0, buffer.getIndexold());
    }

    @Test
    public void testWriteSeveralTimesAndCheckData() {
        float[] dataToWrite = {1, 2, 3, 4};
        CircularBuffer buffer = new CircularBuffer(5);
        boolean result = buffer.write(dataToWrite);
        assertEquals(0, buffer.getIndexold());
        result = buffer.write(dataToWrite);
        assertEquals(true, result);
        float[] datacompare = {2, 3, 4, 4, 1};
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(0,
                buffer.getSize()), datacompare, buffer.read(0, buffer.getSize()).length));
        assertEquals(3, buffer.getIndexold());
        float[] datacompare2 = {3, 4, 4, 1, 2};
        result = buffer.write(dataToWrite);
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(0,
                buffer.getSize()), datacompare2, buffer.read(0, buffer.getSize()).length));
        assertEquals(true, result);
        assertEquals(2, buffer.getIndexold());
    }

    @Test
    public void writeManyNumbersAndCheckIndexoldAndLastData() {
        float[] dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(0, 999);
        CircularBuffer buffer = new CircularBuffer(1000);
        boolean result = buffer.write(dataToWrite);
        assertEquals(true, result);
        assertEquals(0, buffer.getIndexold());
        assertEquals(998, buffer.getLastData(), 0.1);
        result = buffer.write(dataToWrite);
        assertEquals(true, result);
        assertEquals(998, buffer.getIndexold());
        assertEquals(998, buffer.getLastData(), 0.1);
        dataToWrite = AuxTestUtilities.generateArrayWithConsecutiveIntegers(-2, 5);
        result = buffer.write(dataToWrite);
        assertEquals(true, result);
        assertEquals(3, buffer.getIndexold());
        assertEquals(2, buffer.getLastData(), 0.1);
    }

    @Test
    public void simpleRead() {
        CircularBuffer buffer = new CircularBuffer(5);
        float[] datacompare = {2, 3, 4, 4, 1};
        buffer.write(datacompare);
        float[] datacompare2 = {3, 4};
        float[] datacompare3 = {4, 4, 1};
        float[] datacompare4 = {1, 2, 3};
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(1, 2), datacompare2, 2));
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(2, 3), datacompare3, 3));
        assertEquals(true, AuxTestUtilities.compareArray(buffer.read(4, 3), datacompare4, 3));
    }

    @Test
    public void readWhitRandomNumbers() {
        CircularBuffer buffer = new CircularBuffer(9);
        float[] array1 = AuxTestUtilities.generateArray(5);
        float[] array2 = AuxTestUtilities.generateArray(5);
        float[] array3 = AuxTestUtilities.generateArray(5);
        buffer.write(array1);
        assertEquals(0, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array1, buffer.read(0, 5), 5));
        buffer.write(array2);
        assertEquals(1, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array2, buffer.read(5, 5), 5));
        buffer.write(array3);
        assertEquals(6, buffer.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array3, buffer.read(1, 5), 5));
    }

    @Test
    public void complexReadTest() {
        CircularBuffer instance = new CircularBuffer(9);
        float[] array1 = {11, 12, 13, 14, 15, 16, 17};
        float[] array2 = {21, 22, 23, 24, 25};
        float[] array3 = {31, 32, 33};
        instance.write(array1);
        assertEquals(0, instance.getIndexold());
        assertEquals(true, AuxTestUtilities.compareArray(array1, instance.read(0, 7), 7));
        instance.write(array1);
        assertEquals(5, instance.getIndexold());
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(0, 7), 7));
        float[] result1 = {13, 14, 15, 16, 17, 16, 17, 11, 12};
        assertEquals(true, AuxTestUtilities.compareArray(instance.read(0, 7), result1, 7));
        instance.write(array3);
        assertEquals(8, instance.getIndexold());
        assertEquals(false, AuxTestUtilities.compareArray(array1, instance.read(0, 7), 7));
        float[] result2 = {13, 14, 15, 16, 17, 31, 32, 33, 12};
        assertEquals(true, AuxTestUtilities.compareArray(result2, instance.read(0, 9), 9));
        instance.write(array2);
        assertEquals(4, instance.getIndexold());
        float[] result3 = {22, 23, 24, 25, 17, 31, 32, 33, 21};
        assertEquals(true, AuxTestUtilities.compareArray(result3, instance.read(0, 9), 9));
        instance.write(array1);
        float[] result4 = {16, 17, 24, 25, 11, 12, 13, 14, 15};
        assertEquals(true, AuxTestUtilities.compareArray(result4, instance.read(0, 9), 9));
        assertEquals(2, instance.getIndexold());
        instance.write(array2);
        float[] result5 = {16, 17, 21, 22, 23, 24, 25, 14, 15};
        assertEquals(true, AuxTestUtilities.compareArray(result5, instance.read(0, 9), 9));
        assertEquals(7, instance.getIndexold());
        instance.write(array1);
        float[] result6 = {13, 14, 15, 16, 17, 24, 25, 11, 12};
        assertEquals(true, AuxTestUtilities.compareArray(result6, instance.read(0, 9), 9));
        assertEquals(5, instance.getIndexold());

    }

    @Test
    public void testTooMuchDataToWriteException() {
        CircularBuffer instance = new CircularBuffer(7);
        float[] array1 = AuxTestUtilities.generateArray(7);
        instance.write(array1);
        CircularBuffer instance2 = new CircularBuffer(6);
        try {
            instance2.write(array1);
            fail("Debería haber saltado la excepcion");
        } catch (TooMuchDataToWriteException e) {
            System.out.println(e.getMessage());
        }
        CircularBuffer instance3 = new CircularBuffer(3);
        try {
            instance3.write(array1);
            fail("Debería haber saltado la excepcion");
        } catch (TooMuchDataToWriteException e) {
            System.out.println(e.getMessage());
        }
    }
}
