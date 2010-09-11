/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Representa una serie temporal.
 * identifier es una cadena de texto que representa a la serie temporal.
 * agent identifica al agente que produce los datos, si estos son parámetros fisiológicos que proceden del paciente tendrá el valor 'simulated'
 * buffer es el buffer circular que contiene los datos
 * frequency tiene la frecuencia de muestreo de la señal (Hz)
 * units es una cadena de texto que contiene las unidades del parámetro representado por la serie temporal
 *
 
 * @author USUARIO
 */
public class TimeSeries extends Series {
    //Parametros serie temporal

    private CircularBuffer buffer;
    private float frequency;
    private String units;
    private ReentrantReadWriteLock lock;

    /**
     * 
     * 
     * @param identifier
     * @param agent
     * @param frequency
     * @param units
     */
    public TimeSeries(String identifier, String agent, long origin, float frequency, String units) {
        super(identifier, agent, origin);
        this.lock=new ReentrantReadWriteLock();
        this.frequency = frequency;
        this.units = units.trim();
        if (3600 * 6 * this.frequency > 100000) {
            this.buffer = new CircularBuffer(100000);
        } else {
            this.buffer = new CircularBuffer((int)Math.ceil(3600 * 6*this.frequency));
        }
    }

    public int getSamplescounter() {
        return buffer.getSize();
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * Devuelve la muestra más nueva
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    //Aqui estos métodos no me quedan muy claros
    public int getIndexNewsample() {
        if(buffer.isEmpty()) return -1;
        return (buffer.getIndexNextWrite()-1)%this.getCapacity();
    }

    /**
     * Indice de la muestra mas antigua
     * @return -1 si el buffer esta vacio en otro caso el indice
     */
    public int getIndexOldsample() {
        if(buffer.isEmpty()) return -1;
        return buffer.getIndexold();
    }

    public String getUnits() {
        return units;
    }
    public int getCapacity(){
        return this.buffer.getCapacity();
    }

    public float[] read(int posSrc, int sizetoread) {
        return this.buffer.read(posSrc, sizetoread);
    }

    public boolean write(float[] datatowrite) {

        try {
            return this.buffer.write(datatowrite);
        } catch (TooMuchDataToWriteException e) {
            throw new TooMuchDataToWriteException(e,this.getIdentifier(),this.getCapacity(),datatowrite.length);
        }


    }
    //@duda hago esto o pongo los bloqueos implicitos?
    public void getReadLock()
    {
        //System.out.println(Thread.currentThread().getName()+"READ LOCK TRY");
        this.lock.readLock().lock();
        if(this.lock.isWriteLocked())
            System.out.println("@@@@ERROR READ WITH WRITE @@@@");
         //  System.out.println(Thread.currentThread().getName()+"READ LOCK ON");
    }
    public void releaseReadLock()
    {
        if(this.lock.isWriteLocked())
            System.out.println("@@@@ERROR READ WITH WRITE @@@@");
        this.lock.readLock().unlock();
       // System.out.println(Thread.currentThread().getName()+"READ UNLOCK ");
    }
    public void getWriteLock()
    {//System.out.println(Thread.currentThread().getName()+"WRITE LOCK TRY");
        this.lock.writeLock().lock();

        
       // System.out.println(Thread.currentThread().getName()+"WRITE LOCK ON");
    }
    public void releaseWriteLock()
    {
        this.lock.writeLock().unlock();
     //   System.out.println(Thread.currentThread().getName()+"WRITE UNLOCK ");
    }
}
