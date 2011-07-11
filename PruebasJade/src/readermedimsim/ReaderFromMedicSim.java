/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package readermedimsim;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ReaderFromMedicSim {

    private Socket Sock = null;
    private int Port = 3434;
    private String Address = "localhost";
    private DataInputStream read = null;
    private DataOutputStream   write = null;

    public ReaderFromMedicSim(int Port) {


        this.Port = Port;
        try {
            this.Sock = new Socket("localhost", Port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.read = null;
        this.write = null;
        try {

            read = new DataInputStream(Sock.getInputStream());
            // Crea el flujo de salida
            write = new DataOutputStream (Sock.getOutputStream());
        } catch (IOException ex) {
            try {
                read.close();
                write.close();
                Sock.close();
                Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex1) {
                Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
       
        }

    }    
    public boolean Send(int S) {
        try {
            write.writeChar(S);
        } catch (IOException ex) {
            Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }
        return true;
    }


      public int Recieve() {
        try {
            return  read.read();
        } catch (Exception ex) {
            Logger.getLogger(ReaderFromMedicSim.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo Leyendo");
        }
        return -1;
      }
      //Metodo Debug
      //@borrar
      public String RecieveString(){
          String a=new String();
          try{
              char caracter=0;
              while(caracter!=-1)
              {
           caracter=read.readChar();
           char[] caracteres=new char[1];
           caracteres[0]=caracter;
             a=new String(a.concat(new String(caracteres)));

              }


          }
          catch(Exception e){
              System.out.println(e.getLocalizedMessage());
          }
          return a;
      }
}
