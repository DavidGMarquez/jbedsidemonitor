/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 * Clase que representa un buffer circular.
 * Es un buffer simple con la particularidad de que al llenarse vuelve a escribir al principio del array.
 * indexNextWrite representa por donde vamos a escribir.
 * indexOldest apunta a la muestra mas antigua que hay en el array
 * full es un boleano que indica si el buffer se ha lleanado ya de datos completamente
 * @author USUARIO
 */
class CircularBuffer {

    private int indexNextWrite;
    private int indexOldest;
    private int capacity;
    private float data[];
    private boolean full;
    private int lastSampleWrite;
    private int numberOfSamplesWrite;


    /**
     * Crea un buffer con el tamaño por defecto asignado
     */
    public CircularBuffer() {
        this(10000);
    }
    //@pendiente:  para que no escape la refencia durante la construcción a la
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
        indexOldest = 0;
        full = false;
        lastSampleWrite = -1;
        numberOfSamplesWrite = 0;
        //@duda y si escribo dos veces la misma muestra se deben contar dos veces?
        //@duda no se si necesito ambos contadores pero por si acaso los he puesto

    }

    /**
     * Escribe datos en el buffer.
     * Si la cantidad de datos a escribir es mayor que el tamaño del buffer devuelve false
     * En cualquier otro caso devuelve true
     *
     * Se debe indicar el array de floats con los datos a escribir y aparte el numero de datos a escribir.
     *
     * @param dataToWrite
     * @return
     */
    //@duda creo que habría que deshabilitar este metodo publicamente
    public boolean write(float[] dataToWrite) {
        if (dataToWrite.length > this.capacity) {
            //@pendiente revisar esto
            throw new TooMuchDataToWriteException("dataToWrite is bigger that the size of buffer");
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
            if (full) {
                this.indexOldest = this.indexNextWrite;
            }
        }
        return true;
    }

    /**
     * Escribe datos en el buffer.
     * Si la cantidad de datos a escribir es mayor que el tamaño del buffer devuelve false
     * En cualquier otro caso devuelve true
     *
     * Se debe indicar el array de floats con los datos a escribir y aparte el numero de datos a escribir.
     * En este caso tambien indicamos donde queremos que se realice la escritura.
     * Si usamos este metodo no se controla el orden en las escrituras, debe ser llevado de forma externa
     *
     * @param dataToWrite indexInitToWrite
     * @return
     */
    boolean write(float[] dataToWrite, int sampleInitToWrite) {
        System.out.println("-->>Escribiendo en" + sampleInitToWrite + "Cantidad " + dataToWrite.length + "lastSample" + this.lastSampleWrite);
        if (sampleInitToWrite > this.lastSampleWrite + 1) {
            //@pendiente revisar
            this.writeNAN(lastSampleWrite + 1, sampleInitToWrite -( lastSampleWrite + 1));
        }
        if ((sampleInitToWrite + dataToWrite.length - 1) >= this.lastSampleWrite) {

            this.lastSampleWrite = (sampleInitToWrite + dataToWrite.length) - 1;
        }
        numberOfSamplesWrite = +dataToWrite.length;
        if (sampleInitToWrite > this.capacity) {
            sampleInitToWrite = sampleInitToWrite % capacity;
        }
        this.indexNextWrite = sampleInitToWrite;
        this.write(dataToWrite);
        //@duda si hace falta podría volver a la anterior.
        return true;
    }

    private void writeNAN(int sampleOrigin, int size) {
        System.out.println("NANANANANAN Escribiendo NAN Desde" + sampleOrigin + "Tamaño" + size + "lastSample" + this.lastSampleWrite);
        //@pendiente revisar
        float[] nanToWrite = new float[size];
        for (int i = 0; i < size; i++) {
            nanToWrite[i] = Float.NaN;
        }
        if (sampleOrigin > this.capacity) {
            sampleOrigin = sampleOrigin % capacity;
        }
        int auxiliar=this.indexNextWrite;
        this.indexNextWrite = sampleOrigin;
        this.write(nanToWrite);
        this.indexNextWrite=auxiliar;

    }

    /**
     * Lee datos del buffer.
     * Si la cantidad de datos a leer es mayor que el tamaño del buffer solo se lee el tamaño del buffer
     * En otro caso devuelve una referencia a los datos leidos.
     * Se indica a partir de que posicion quremos leer y la cantidad de datos
     * @param posStartReading
     * @param numDataToRead
     * @return
     */
    float[] read(int posStartReading, int numDataToRead) {
        
        if(posStartReading<0)
        {
            throw new IllegalReadException("posStartReading is negative", this.capacity, posStartReading, numDataToRead, this.lastSampleWrite, this.numberOfSamplesWrite);
        }
        if(posStartReading+numDataToRead>(this.lastSampleWrite+1))
        {
            throw new IllegalReadException("try to read future data", this.capacity, posStartReading, numDataToRead, this.lastSampleWrite, this.numberOfSamplesWrite);
        }
        if(posStartReading<(this.lastSampleWrite-this.capacity))
        {
            throw new IllegalReadException("try to read data not avalible, data too old ", this.capacity, posStartReading, numDataToRead, this.lastSampleWrite, this.numberOfSamplesWrite);
        }

        if (numDataToRead > this.capacity) {
            numDataToRead = this.capacity;
        }
        posStartReading=posStartReading%this.capacity;
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
        return read(indexNextWrite - 1, 1)[0];
    }

    public int getIndexold() {
        return indexOldest;
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
        return lastSampleWrite;
    }

    public int getNumberOfSamplesWrite() {
        return numberOfSamplesWrite;
    }

    public int getSamplesReadyToRead() {
        if (this.lastSampleWrite < 0) {
            return 0;
        }
        if (this.lastSampleWrite > this.capacity) {
            return this.capacity;
        }
        return this.lastSampleWrite;

    }
}
