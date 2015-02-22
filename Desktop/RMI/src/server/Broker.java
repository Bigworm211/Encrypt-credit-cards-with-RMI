package server;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
* Dealing with all the details related to tuning the 
* server and the publication of the interface.
* @param reg
* @param rmiMessage
* @return
*/

public class Broker {
    
    public static Registry reg;
    public static ServiceProvider rmiMessage;
    
    public static void main(String args[]){
           try{
            rmiMessage = new ClientServiceUtillity();
            //Change the port
            reg = LocateRegistry.createRegistry(1100);
            reg.rebind("ClientServiceUtillity", rmiMessage);
            
            java.awt.EventQueue.invokeLater(() -> {
                 new UIServer().setVisible(true);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}