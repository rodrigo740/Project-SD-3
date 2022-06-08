package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type Bar.
 *
 *     It provides the functionality to access the Bar.
 */

public interface BarInterface extends Remote{

	public void alertWaiter(); //chef
	public void enter(); //student
	public void callTheWaiter();//student
	public void signalWaiter();//student
	public void shouldHaveArrivedEarlier();//student
	public void goHome();//student
	
	public char lookAround(); //waiter
	public void returnToTheBarAfterSalute();//waiter
	public void returnToTheBarAfterTakingTheOrder();//waiter
	public void returnToTheBarAfterPortionsDelivered();//waiter
	public void prepareBill();//waiter
	public void receivedPayment();//waiter
	public void returnToTheBar();//waiter
	public void sayGoodbye();//waiter
	
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
