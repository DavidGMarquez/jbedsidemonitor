/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import datasource.DriverReaderJSignal;
import zclasesParaBORRAR.ThreadManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import signals.SignalManager;
import signals.SignalManagerTest;
import zclasesParaBORRAR.ThreadWriteOperations;
import static org.junit.Assert.*;

/**
 *
 * @author USUARIO
 */
public class TestIntegrationWithGeneral {

    public TestIntegrationWithGeneral() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    @Test
    public void TestDriverReaderJSignal(){

             ThreadManager generalManager=ThreadManager.getInstance();
             generalManager.start();
                WriteToDisk WTD1=new WriteToDisk("OutputTestDriverReaderJSignal1.txt", SignalManager.getInstance());
         Thread threadWriteToDisk1=new Thread(WTD1,"threadWriteToDisk1");
         threadWriteToDisk1.start();
   /*                      WriteToDisk WTD2=new WriteToDisk("OutputTestDriverReaderJSignal2.txt", SignalManager.getInstance());
         Thread threadWriteToDisk2=new Thread(WTD2,"threadWriteToDisk2");
         threadWriteToDisk2.start();
                         WriteToDisk WTD3=new WriteToDisk("OutputTestDriverReaderJSignal3.txt", SignalManager.getInstance());
         Thread threadWriteToDisk3=new Thread(WTD3,"threadWriteToDisk3");
         threadWriteToDisk3.start();
                         WriteToDisk WTD4=new WriteToDisk("OutputTestDriverReaderJSignal4.txt", SignalManager.getInstance());
         Thread threadWriteToDisk4=new Thread(WTD4,"threadWriteToDisk1");
         threadWriteToDisk4.start();*/
        try {
            System.out.println("INCIO JOINS");
         threadWriteToDisk1.join(10000);
   /*      threadWriteToDisk2.join(1000);
         threadWriteToDisk3.join(1000);
         threadWriteToDisk4.join(1000);*/
        } catch (InterruptedException ex) {
            Logger.getLogger(TestIntegration.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}