/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vehicleclass;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
//@pendiente convertir clase en inmutable
public class ReadOperation {
   ArrayList<ReadOperationOneSignal> readOperationSignals;
   String algorithmIdentifier;

   public ReadOperation()
    {
       readOperationSignals=new ArrayList<ReadOperationOneSignal>();
   }
   public boolean addReadOperationOneSignal(ReadOperationOneSignal readOperationOneSignal)
    {
       return this.readOperationSignals.add(readOperationOneSignal);
   }
   public int getNumberOfReadOperationSignals()
    {
       return this.readOperationSignals.size();
   }
   public ReadOperationOneSignal getAndRemoveReadOperationOneSignal()
    {
       if (readOperationSignals.size()>0)
       return this.readOperationSignals.remove(0);
       else
           return null;
   }




}
