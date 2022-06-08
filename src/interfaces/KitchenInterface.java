package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type kitchen.
 *
 *     It provides the functionality to access the kitchen.
 */

public interface KitchenInterface extends Remote{

	public void watchTheNews(); //chef
	public void startPreparations(); //chef
	public void continuePreparation(); //chef
	public void proceedToPresentation(); //chef
	public void deliverPortion(); //chef
	public void haveNextPortionReady(); //chef
	public void alertWaiter(); //chef
	public void cleanUp(); //chef
	public boolean orderBeenCompleted(); //chef
	public boolean allPortionsDelived(); //chef
	
	public void handTheNoteToTheChef(); //Waiter
	public void collectPortion(); //Waiter
	
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
