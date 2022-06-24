package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type Bar.
 *
 *     It provides the functionality to access the Bar.
 */

public interface BarInterface extends Remote{

	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portions is ready to be
	 * delivered
	 * @throws RemoteException 
	 */
	public void alertWaiter() throws RemoteException; //chef
	
	/**
	 * Operation enter
	 *
	 * It is called by a student to enter the restaurant
	 * @throws RemoteException 
	 */
	public void enter() throws RemoteException; //student
	
	/**
	 * Operation call the waiter
	 *
	 * It is called by a student to call the waiter to describe the order
	 * @throws RemoteException 
	 */
	public void callTheWaiter() throws RemoteException;//student
	
	/**
	 * Operation signal waiter
	 *
	 * It is called by a student to warn the waiter that it can start delivering the
	 * portions of the next course
	 * @throws RemoteException 
	 * 
	 */
	public void signalWaiter() throws RemoteException;//student
	
	/**
	 * Operation should have arrived earlier
	 *
	 * It is called by a student to warn the waiter that it is ready to pay the bill
	 * @throws RemoteException 
	 */
	public void shouldHaveArrivedEarlier() throws RemoteException;//student
	
	/**
	 * Operation go home
	 *
	 * It is called by a student to warn the waiter that its going home
	 * 
	 * @param studentID student identifier
	 * @throws RemoteException 
	 * 
	 */
	public int goHome(int studentID) throws RemoteException;//student
	
	
	/**
	 * Operation look around
	 *
	 * It is called by a waiter to look around
	 * 
	 * @param waiterID waiter identifier
	 * @return oper operation to be performed
	 * @throws RemoteException 
	 */
	public char lookAround(int waiterID) throws RemoteException; //waiter
	
	/**
	 * Operation return to the bar after salute
	 *
	 * It is called by a waiter to return to the bar after saluting the student
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	public int returnToTheBarAfterSalute(int waiterID) throws RemoteException;//waiter
	
	/**
	 * Operation return to the bar after taking the order
	 *
	 * It is called by a waiter to return to the bar after taking the order
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	public int returnToTheBarAfterTakingTheOrder(int waiterID) throws RemoteException;//waiter
	
	/**
	 * Operation return to the bar after portions delivered
	 *
	 * It is called by a waiter to the bar after all portions of a course have been
	 * delivered
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	public int returnToTheBarAfterPortionsDelivered(int waiterID) throws RemoteException;//waiter
	
	/**
	 * Operation prepare the bill
	 *
	 * It is called by a waiter to prepare the bill
	 * 
	 * @param waiterID waiter identifier
	 * @return WaiterStates.PRCBL
	 * @throws RemoteException 
	 * 
	 */
	public int prepareBill(int waiterID) throws RemoteException;//waiter
	
	/**
	 * Operation received payment
	 *
	 * It is called by a waiter after the payment has been received
	 * @throws RemoteException 
	 * 
	 */
	public void receivedPayment() throws RemoteException;//waiter
	
	/**
	 * Operation return to the bar
	 *
	 * It is called by a waiter to return to the bar
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	public int returnToTheBar(int waiterID) throws RemoteException;//waiter
	
	/**
	 * Operation say goodbye
	 *
	 * It is called by a waiter to say goodbye to the student
	 * 
	 * @throws RemoteException 
	 */
	public void sayGoodbye() throws RemoteException;//waiter
	
  /**
   *  Operation end of work.
   *
   *   New operation.
   *
   *      @param waiterId waiter id
   *      @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                              service fails
   */

    public void endOperation (int waiterId) throws RemoteException;

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
