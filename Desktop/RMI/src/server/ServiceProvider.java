
package server;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author hristo
 */
public interface ServiceProvider extends Remote{
    boolean login(String username, char[] password) throws RemoteException;
    String encrypt(String creditCard) throws RemoteException, Exception;
    String decrypt(String creditCard) throws RemoteException, Exception;
}
