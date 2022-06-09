package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type Bar.
 *
 *     It provides the functionality to access the Bar.
 */

public interface BarInterface extends Remote{

	public void alertWaiter() throws RemoteException; //chef
	public void enter() throws RemoteException; //student
	public void callTheWaiter() throws RemoteException;//student
	public void signalWaiter() throws RemoteException;//student
	public void shouldHaveArrivedEarlier() throws RemoteException;//student
	public void goHome() throws RemoteException;//student
	
	public char lookAround() throws RemoteException; //waiter
	public void returnToTheBarAfterSalute() throws RemoteException;//waiter
	public void returnToTheBarAfterTakingTheOrder() throws RemoteException;//waiter
	public void returnToTheBarAfterPortionsDelivered() throws RemoteException;//waiter
	public void prepareBill() throws RemoteException;//waiter
	public void receivedPayment() throws RemoteException;//waiter
	public void returnToTheBar() throws RemoteException;//waiter
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
