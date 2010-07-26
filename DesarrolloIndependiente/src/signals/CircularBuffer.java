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
    boolean write(float[] dataToWrite) {
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
     * Lee datos del buffer.
     * Si la cantidad de datos a leer es mayor que el tamaño del buffer solo se lee el tamaño del buffer
     * En otro caso devuelve una referencia a los datos leidos.
     * Se indica a partir de que posicion quremos leer y la cantidad de datos
     * @param posStartReading
     * @param numDataToRead
     * @return
     */
    float[] read(int posStartReading, int numDataToRead) {
        if (numDataToRead > this.capacity) {
            numDataToRead = this.capacity;
        }
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
}
