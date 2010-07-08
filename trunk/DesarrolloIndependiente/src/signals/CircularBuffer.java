/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 * Clase que representa un buffer circular.
 * Es un buffer simple con la particularidad de que al llenarse vuelve a escribir al principio del array.
 * indexnext representa por donde vamos a escribir.
 * indexold apunta a la muestra mas antigua que hay en el array
 * full es un boleano que indica si el buffer se ha lleanado ya de datos completamente
 * @author USUARIO
 */
class CircularBuffer {

    private int indexnext;
    private int indexold;
    private int size;
    private float data[];
    private boolean full;

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public int getIndexold() {
        return indexold;
    }

    public void setIndexold(int indexold) {
        this.indexold = indexold;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    

    public int getIndexnext() {
        return indexnext;
    }

    public void setIndexnext(int indexnext) {
        this.indexnext = indexnext;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    /**
     * Crea un buffer con el tamaño por defecto asignado
     */
    public CircularBuffer() {
        size = 100000;
        data = new float[size];
        indexnext = 0;
        indexold = 0;
        full=false;
    }
    /**
     * Crea un buffer circular con el tamaño que indique size
     * @param size
     */
    public CircularBuffer(int size) {
        this.size = size;
        data = new float[size];
        indexnext = 0;
        indexold = 0;
        full=false;
    }
    /**
     * Escribe datos en el buffer.
     * Si la cantidad de datos a escribir es mayor que el tamaño del buffer devuelve false
     * En cualquier otro caso devuelve true
     *
     * Se debe indicar el array de floats con los datos a escribir y aparte el numero de datos a escribir.
     *
     * @param datatowrite
     * @param sizetowrite
     * @return
     */
    boolean write(float[] datatowrite, int sizetowrite) {
        if (sizetowrite > this.size) {
            return false;
        }
        int tocopy = sizetowrite;
        int inidest = this.indexnext;
        int iniori = 0;
        int tam = tocopy;
        while (tocopy != 0) {

            if (tocopy + this.indexnext > this.size) {
                iniori = sizetowrite - tocopy;
                inidest = this.indexnext;
                tam = this.size - this.indexnext;
                //Se desborda el buffer circular
                System.arraycopy(datatowrite, iniori, this.data, inidest, tam);
                tocopy = tocopy - tam;
                this.indexnext = 0;
                full=true;
                this.indexold=0;
            } else {
                //Copia simple
                iniori = sizetowrite - tocopy;
                tam = tocopy;
                inidest = this.indexnext;
                System.arraycopy(datatowrite, iniori, this.data, inidest, tam);
                tocopy = tocopy - tam;
                this.indexnext =(this.indexnext + tam)%this.size;
                if(full==true)
                {
                    this.indexold=this.indexnext;
                }
                else
                {
                   this.indexold=0;
                }

            }
        }
        return true;
    }
/**
 * Lee datos del buffer.
 * Si la cantidad de datos a leer es mayor que el tamaño del buffer devuelve una referencia a null.
 * En otro caso devuelve una referencia a los datos leidos.
 * Se indica a partir de que posicion quremos leer y la cantidad de datos
 * @param posSrc
 * @param sizetoread
 * @return
 */
    float[] read( int posSrc,int sizetoread) {

        if (sizetoread > this.size) {
            return null;
        }
        float[] dataread = new float[sizetoread];
        int toread = sizetoread;
        int iniori = posSrc;
        int inidest = 0;
        int tam = sizetoread - toread;
        while (toread != 0) {
            if (iniori + toread > this.size) {
                tam = this.size - iniori;
                System.arraycopy(this.data, iniori, dataread, inidest, tam);
                inidest = inidest + tam;
                toread = toread - tam;
                iniori = 0;

            } else {
                tam = toread;
                System.arraycopy(this.data, iniori, dataread, inidest, tam);
                toread = toread - tam;
            }
        }


        return dataread;
    }
}
