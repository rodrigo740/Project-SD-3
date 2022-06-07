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
	
	
}
