/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package signals;

/**
 *
 * @author USUARIO
 */
class TooMuchDataToWriteException extends RuntimeException{

    public TooMuchDataToWriteException(String message) {
        super(message);
    }

    TooMuchDataToWriteException(TooMuchDataToWriteException e, String identifier) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
