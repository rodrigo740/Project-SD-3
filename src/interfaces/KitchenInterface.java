package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type kitchen.
 *
 *     It provides the functionality to access the kitchen.
 */

public interface KitchenInterface extends Remote{

	public void watchTheNews() throws RemoteException; //chef
	public void startPreparations() throws RemoteException; //chef
	public void continuePreparation() throws RemoteException; //chef
	public void proceedToPresentation() throws RemoteException; //chef
	public void deliverPortion() throws RemoteException; //chef
	public void haveNextPortionReady() throws RemoteException; //chef
	public void alertWaiter() throws RemoteException; //chef
	public void cleanUp() throws RemoteException; //chef
	public boolean orderBeenCompleted() throws RemoteException; //chef
	public boolean allPortionsDelived() throws RemoteException; //chef
	
	public void handTheNoteToTheChef() throws RemoteException; //Waiter
	public void collectPortion() throws RemoteException; //Waiter
	
	  /**
	   *  Operation end of work.
	   *
	   *   New operation.
	   *
	   *      @param chefId chef id
	   *      @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                              service fails
	   */

	   public void endOperation (int chefId) throws RemoteException;

	  /**
	   *   Operation server shutdown.
	   *
	   *   New operation.
	   *
	   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                             service fails
	   */

	   public void shutdown () throws RemoteException;
}
