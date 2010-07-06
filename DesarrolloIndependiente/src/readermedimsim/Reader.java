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
public class Reader {

    private Socket Sock = null;
    private int Port = 3434;
    private String Address = "localhost";
    private DataInputStream read = null;
    private DataOutputStream   write = null;

    public Reader(int Port) {


        this.Port = Port;
        try {
            this.Sock = new Socket("localhost", Port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex1) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
       
        }

    }    
    public boolean Send(int S) {
        try {
            write.writeChar(S);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }
        return true;
    }


      public int Recieve() {
        try {
            return  read.read();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);

        }
        return -1;
      }
}
