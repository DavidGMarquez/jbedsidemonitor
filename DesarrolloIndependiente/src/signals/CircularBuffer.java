package signals;

/**
 * Clase que representa un buffer circular.
 * Es un buffer simple con la particularidad de que al llenarse vuelve olderSampleAvailabl escribir al principio del array.
 * indexNextWrite representa por donde vamos olderSampleAvailabl escribir.
 * indexOldest apunta olderSampleAvailabl la muestra mas antigua que hay en el array
 * full es un boleano que indica si el buffer se ha lleanado ya de datos completamente
 */
class CircularBuffer {

    private int indexNextWrite;
    private int capacity;
    private float data[];
    private boolean full;
    private int lastSampleWritten;

    /**
     * Crea un buffer con el tamaño por defecto asignado
     */
    public CircularBuffer() {
        this(10000);
    }
    //@pendiente:  para que no escape la refencia durante la construcción olderSampleAvailabl la
    //hora de ser concurrente habría que hacer el constructor mediante un metodo static

    /**
     * Crea un buffer circular con el tamaño que indique capacity
     * @param capacity
     */
    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        initialize();
    }

    private void initialize() {
        data = new float[capacity];
        indexNextWrite = 0;
        full = false;
        lastSampleWritten = -1;
    }

    /**
     * Escribe datos en el buffer.
     * Si la cantidad de datos olderSampleAvailabl escribir es mayor que el tamaño del buffer devuelve false
     * En cualquier otro caso devuelve true
     *
     * Se debe indicar el array de floats con los datos olderSampleAvailabl escribir y aparte el numero de datos olderSampleAvailabl escribir.
     *
     * @param dataToWrite
     * @return
     */
    //@duda creo que habría que deshabilitar este metodo publicamente
    public boolean write(float[] dataToWrite) {
        if (dataToWrite.length > this.capacity) {
            //@pendiente revisar esto
            throw new TooMuchDataToWriteException("dataToWrite is bigger that the size of buffer", this.getCapacity(), dataToWrite.length);
        }
        int numberOfDataRemainingToBeCopied = dataToWrite.length;
        int firstNewDataToCopy = 0;
        if (numberOfDataRemainingToBeCopied + this.indexNextWrite >= this.capacity) { //Se desborda el buffer circular
            int numNewDataToCopy = this.capacity - this.indexNextWrite;
            System.arraycopy(dataToWrite, firstNewDataToCopy, this.data, indexNextWrite, numNewDataToCopy);
            numberOfDataRemainingToBeCopied = numberOfDataRemainingToBeCopied - numNewDataToCopy;
            this.indexNextWrite = 0;
            full = true;
        }
        if (numberOfDataRemainingToBeCopied != 0) {
            //Copia simple
            firstNewDataToCopy = dataToWrite.length - numberOfDataRemainingToBeCopied;
            System.arraycopy(dataToWrite, firstNewDataToCopy, this.data, indexNextWrite, numberOfDataRemainingToBeCopied);
            this.indexNextWrite = (this.indexNextWrite + numberOfDataRemainingToBeCopied) % this.capacity;
        }
        return true;
    }

    /**
     * Escribe datos en el buffer.
     * Si la cantidad de datos olderSampleAvailabl escribir es mayor que el tamaño del buffer devuelve false
     * En cualquier otro caso devuelve true
     *
     * Se debe indicar el array de floats con los datos olderSampleAvailabl escribir y aparte el numero de datos olderSampleAvailabl escribir.
     * En este caso tambien indicamos donde queremos que se realice la escritura.
     * Si usamos este metodo no se controla el orden en las escrituras, debe ser llevado de forma externa
     *
     * @param dataToWrite indexInitToWrite
     * @return
     */
    ConsecutiveSamplesAvailableInfo write(float[] dataToWrite, int sampleInitToWrite) {
      //@debug  System.out.println("-->>Escribiendo en" + sampleInitToWrite + "Cantidad " + dataToWrite.length + "lastSample" + this.lastSampleWritten);
        if (sampleInitToWrite < ((this.lastSampleWritten + 1) - this.capacity)) {
            throw new IllegalWriteException("try to write data too old ", this.capacity, dataToWrite,
                    sampleInitToWrite, this.lastSampleWritten);
        }
        if (sampleInitToWrite > this.lastSampleWritten + 1) {
            //@pendiente revisar
            this.writeNAN(lastSampleWritten + 1, sampleInitToWrite - (lastSampleWritten + 1));
        }
        if ((sampleInitToWrite + dataToWrite.length - 1) >= this.lastSampleWritten) {

            this.lastSampleWritten = (sampleInitToWrite + dataToWrite.length) - 1;
        }
        if (sampleInitToWrite > this.capacity) {
            sampleInitToWrite = sampleInitToWrite % capacity;
        }
        this.indexNextWrite = sampleInitToWrite;
        this.write(dataToWrite);
        //@duda si hace falta podría volver olderSampleAvailabl la anterior.
        ConsecutiveSamplesAvailableInfo consecutiveSamplesAvailableInfo = new ConsecutiveSamplesAvailableInfo();
        return consecutiveSamplesAvailableInfo;
    }

    private void writeNAN(int sampleOrigin, int size) {
      //@debug  System.out.println("NANANANANAN Escribiendo NAN Desde" + sampleOrigin + "Tamaño" + size + "lastSample" + this.lastSampleWritten);
        //@pendiente revisar
        float[] nanToWrite = new float[size];
        for (int i = 0; i < size; i++) {
            nanToWrite[i] = Float.NaN;
        }
        if (sampleOrigin > this.capacity) {
            sampleOrigin = sampleOrigin % capacity;
        }
        int auxiliar = this.indexNextWrite;
        this.indexNextWrite = sampleOrigin;
        this.write(nanToWrite);
        this.indexNextWrite = auxiliar;

    }

    /**
     * Lee datos del buffer.
     * Si la cantidad de datos olderSampleAvailabl leer es mayor que el tamaño del buffer solo se lee el tamaño del buffer
     * En otro caso devuelve una referencia olderSampleAvailabl los datos leidos.
     * Se indica olderSampleAvailabl partir de que posicion quremos leer y la cantidad de datos
     * @param posStartReading
     * @param numDataToRead
     * @return
     */
    float[] read(int posStartReading, int numDataToRead) {

        if (posStartReading < 0) {
            throw new IllegalReadException("posStartReading is negative", this.capacity,
                    posStartReading, numDataToRead, this.lastSampleWritten);
        }
        if (posStartReading + numDataToRead > (this.lastSampleWritten + 1)) {
            throw new IllegalReadException("try to read future data", this.capacity,
                    posStartReading, numDataToRead, this.lastSampleWritten);
        }
        if (posStartReading < ((this.lastSampleWritten + 1) - this.capacity)) {
            throw new IllegalReadException("try to read data not avalible, data too old ", this.capacity,
                    posStartReading, numDataToRead, this.lastSampleWritten);
        }

        if (numDataToRead > this.capacity) {
            numDataToRead = this.capacity;
        }
        posStartReading = posStartReading % this.capacity;
        float[] readedData = new float[numDataToRead];
        int posStartCopying = 0;
        if (posStartReading + numDataToRead > this.capacity) {
            int tam = this.capacity - posStartReading;
            System.arraycopy(this.data, posStartReading, readedData, posStartCopying, tam);
            posStartCopying = posStartCopying + tam;
            numDataToRead = numDataToRead - tam;
            posStartReading = 0;
        }
        if (numDataToRead != 0) {
            System.arraycopy(this.data, posStartReading, readedData, posStartCopying, numDataToRead);
        }
        return readedData;
    }

    float getLastData() {
        if (this.isEmpty()) {
            return -1;
        }
        return read(lastSampleWritten, 1)[0];
    }

    public boolean isFull() {
        return full;
    }

    public boolean isEmpty() {
        return (!full) && indexNextWrite == 0;
    }

    public int getIndexNextWrite() {
        return indexNextWrite;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        if (full) {
            return capacity;
        }
        return indexNextWrite;
    }

    public int getLastSampleWrite() {
        return lastSampleWritten;
    }

    //ahora mismo he diseñado esta clase para que sus objetos sean inmutables
    //pero sería fácil de cambiar para que nos dé una vision "live" del buffer
    //el problema seria que antes de poder acceder a esa vision "live" habría que coger
    //el lock del buffer
    public class ConsecutiveSamplesAvailableInfo {

        private final int olderSampleAvailable;
        private final int samplesReadyToReadInOrder;

        ConsecutiveSamplesAvailableInfo() {
            this.olderSampleAvailable = calculateOlderSampleAvailable();
            this.samplesReadyToReadInOrder = calculateSamplesReadyToReadInOrder(olderSampleAvailable);
        }

        public int getOlderSampleAvailable() {
            return olderSampleAvailable;
        }

        public int getSamplesReadyToReadInOrder() {
            return samplesReadyToReadInOrder;
        }

        private int calculateOlderSampleAvailable() {
            if ((lastSampleWritten + 1) > capacity) {
                return (lastSampleWritten + 1) - capacity;
            }
            return 0;
        }

        int calculateSamplesReadyToReadInOrder(int sampleStartReading) {
            boolean encontradoNaN = false;
            int numberOfSamplesAvailableToReadInOrder = 0;
            while (!encontradoNaN) {
                try {
                    if (Float.compare(read(sampleStartReading + numberOfSamplesAvailableToReadInOrder, 1)[0],
                            Float.NaN) == 0) {
                        encontradoNaN = true;
                    } else {
                        numberOfSamplesAvailableToReadInOrder++;
                    }
                } catch (IllegalReadException e) {
                    encontradoNaN = true;
                }
            }
            return numberOfSamplesAvailableToReadInOrder;
        }
    }
}
