package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type kitchen.
 *
 *     It provides the functionality to access the kitchen.
 */

public interface KitchenInterface extends Remote{

	/**
	 * Operation watch the news.
	 *
	 * It is called by a chef while waiting for and order to be delivered by the
	 * waiter.
	 * @param chefId chef identifier
	 * @return ChefStates.WAFOR
	 * @throws RemoteException 
	 *
	 */
	public int watchTheNews(int chefId) throws RemoteException; //chef
	/**
	 * Operation start preparations.
	 *
	 * It is called by a chef after receiving and order
	 * @param chefId chef identifier
	 * @return ChefStates.PRPCS
	 * @throws RemoteException 
	 *
	 */
	public int startPreparations(int chefId) throws RemoteException; //chef

	/**
	 * Operation continue preparations.
	 *
	 * It is called by a chef after the chef delivered a portion
	 * @param chefId chef identifier
	 * @return ChefStates.PRPCS
	 * @throws RemoteException 
	 *
	 */
	public int continuePreparation(int chefId) throws RemoteException; //chef
	/**
	 * Operation proceed to presentation.
	 *
	 * It is called by a chef after preparing a portion
	 * @param chefId chef identifier
	 * @return ChefStates.DSHPT
	 * @throws RemoteException 
	 *
	 */
	public int proceedToPresentation(int chefId) throws RemoteException; //chef
	/**
	 * Operation deliver portion.
	 *
	 * It is called by a chef to deliver a portion to the waiter
	 * @param chefId chef identifier
	 * @return ChefStates.DLVPT
	 * @throws RemoteException 
	 *
	 */
	public int deliverPortion(int chefId) throws RemoteException; //chef
	
	/**
	 * Operation have next portion ready.
	 *
	 * It is called by a chef in order to start dishing another portion
	 * @param chefId chef identifier
	 * @return ChefStates.DSHPT
	 * @throws RemoteException 
	 *
	 */
	public int haveNextPortionReady(int chefId) throws RemoteException; //chef
	
	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portion is ready to be
	 * collected
	 * @throws RemoteException
	 */
	public void alertWaiter() throws RemoteException; //chef
	
	/**
	 * Operation clean up.
	 *
	 * It is called by a chef to finish its service
	 * @param chefId chef identifier
	 * @return ChefStates.CLSSV
	 * @throws RemoteException 
	 *
	 */
	public int cleanUp(int chefId) throws RemoteException; //chef
	
	/**
	 * Operation order been completed.
	 *
	 * It is called by a chef in order to know if the order has been completed
	 *
	 * @return true, if the order has been completed -
     *            false, otherwise
     * @throws RemoteException
	 */
	public boolean orderBeenCompleted() throws RemoteException; //chef
	/**
	 * Operation all portions delivered.
	 *
	 * It is called by a chef to know if all portion have been delivered
	 *
	 * @return true, if all portion have been delivered -
     *            false, otherwise
	 */
	public boolean allPortionsDelived() throws RemoteException; //chef
	
	/**
	 * Operation hand the note to the Chef.
	 *
	 * It is called by a waiter to deliver the order to the chef
	 * @param waiterID waiter identifier
	 * @return WaiterStates.PCODR
	 * @throws RemoteException 
	 * 
	 */
	public int handTheNoteToTheChef(int waiterID) throws RemoteException; //Waiter

	/**
	 * Operation collect portion.
	 *
	 * It is called by a waiter to collect a portion
	 * @param waiterID waiter identifier
	 * @return WaiterStates.WTFPT
	 * @throws RemoteException 
	 *
	 */
	public int collectPortion(int waiterID) throws RemoteException; //Waiter
	
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
