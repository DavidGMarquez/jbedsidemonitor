/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vehicleclass;

/**
 *
 * @author USUARIO
 * @pendiente hacer Inmutable
 */
public class ReadOperationOneSignal {
    String identifier;
    int indexToRead;
    int quantityToRead;

    public ReadOperationOneSignal(String identifier, int indexToRead, int quantityToRead) {
        this.identifier = identifier;
        this.indexToRead = indexToRead;
        this.quantityToRead = quantityToRead;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getIndexToRead() {
        return indexToRead;
    }

    public int getQuantityToRead() {
        return quantityToRead;
    }



}
