package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import genclass.GenericIO;

/**
 * Waiter thread.
 *
 *	It simulates the barber life cycle.
 *  Implementation of a client-server model of type 2 (server replication).
 *  Communication is based on remote calls under Java RMI.
 */

public class Waiter extends Thread {

	/**
	 * Waiter identification.
	 */

	private int waiterID;

	/**
	 * Waiter state.
	 */

	private int waiterState;

	/**
	 * Reference to the Bar.
	 */

	private final BarInterface barStub;

	/**
	 * Reference to the Kitchen.
	 */

	private final KitchenInterface kitStub;

	/**
	 * Reference to the Table.
	 */

	private final TableInterface tblStub;

	/**
	 * Instantiation of a Waiter thread.
	 * 
	 * @param name        thread name
	 * @param waiterID    id of the waiter
	 * @param waiterState state of the waiter
	 * @param barStub         remote reference to the bar
	 * @param kitStub         remote reference to the kitchen
	 * @param tblStub         remote reference to the table
	 */
	public Waiter(String name, int waiterID, int waiterState, BarInterface barStub, KitchenInterface kitStub, TableInterface tblStub) {
		super(name);
		this.waiterID = waiterID;
		this.waiterState = WaiterStates.APPST;
		this.barStub = barStub;
		this.kitStub = kitStub;
		this.tblStub = tblStub;
	}

	/**
	 * Get waiter id
	 * 
	 * @return waiterID
	 */
	public int getWaiterID() {
		return waiterID;
	}

	/**
	 * Set waiter id
	 * 
	 * @param waiterID
	 */

	public void setWaiterID(int waiterID) {
		this.waiterID = waiterID;
	}

	/**
	 * Get waiter state
	 * 
	 * @return waiterState
	 */

	public int getWaiterState() {
		return waiterState;
	}

	/**
	 * Set waiter state
	 * 
	 * @param waiterState
	 */

	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	/**
	 * Regulates the life cycle of the Waiter.
	 */

	@Override
	public void run() {
		char oper;
		boolean end = false;
		while (!end) {
			// Transition to 'APPST'
			oper = lookAround();
			switch (oper) {
			case 'c':
				// Transition to 'PRSMN'
				saluteTheClient();
				// Transition to 'APPST'
				returnToTheBarAfterSalute();
				break;
			case 'o':
				/*
				long v = (long) (1 + 40 * Math.random());
				try {
					Thread.sleep(v);
				} catch (InterruptedException e) {
				}*/
				// Transition to 'TKODR'
				getThePad();
				// Transition to 'PCODR'
				handTheNoteToTheChef();
				// Transition to 'APPST'
				returnToTheBarAfterTakingTheOrder();
				break;
			case 'p':
				while (!haveAllPortionsBeenServed()) {
					// Transition to 'WTFPT'
					collectPortion();
					deliverPortion();
					/*
					v = (long) (1 + 40 * Math.random());
					try {
						Thread.sleep(v);
					} catch (InterruptedException e) {
					}*/
				}
				// Transition to 'APPST'
				returnToTheBarAfterPortionsDelivered();
				break;
			case 'b':
				// Transition to 'PRCBL'
				prepareBill();
				// Transition to 'RECPM'
				TablepresentBill();
				receivedPayment();
				// Transition to 'APPST'
				returnToTheBar();
				break;
			case 'g':
				sayGoodbye();
				// Transition to 'APPST'
				returnToTheBar();
				break;
			case 'e':
				end = true;
				break;
			}
		}
	}
	
	/**
	 * Operation look around
	 *
	 * It is called by a waiter to look around
	 * 
	 * @return ret operation to be performed
	 */
	private char lookAround() {
		char ret = ' ';
		try
	      { 
			 ret = barStub.lookAround(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on lookAround: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
		return ret;
	}
	
	/**
	 * Operation return to the bar after salute
	 *
	 * It is called by a waiter to return to the bar after saluting the student
	 * 
	 */
	private void returnToTheBarAfterSalute() {
		int ret = -1;
		try
	      { 
			 ret = barStub.returnToTheBarAfterSalute(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterSalute: " + e.getMessage ());
	        System.exit (1);
	      }
		 waiterState = ret;
	}
	
	/**
	 * Operation return to the bar after taking the order
	 *
	 * It is called by a waiter to return to the bar after taking the order
	 * 
	 */
	private void returnToTheBarAfterTakingTheOrder() {
		int ret = -1;
		try
	      { 
			 ret = barStub.returnToTheBarAfterTakingTheOrder(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterTakingTheOrder: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation return to the bar after portions delivered
	 *
	 * It is called by a waiter to the bar after all portions of a course have been
	 * delivered
	 * 
	 */
	private void returnToTheBarAfterPortionsDelivered() {
		int ret = -1;
		try
	      { 
			 ret = barStub.returnToTheBarAfterPortionsDelivered(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterPortionsDelivered: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation prepare the bill
	 *
	 * It is called by a waiter to prepare the bill
	 * 
	 */
	private void prepareBill() {
		int ret = -1;
		try
	      { 
			 ret = barStub.prepareBill(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on prepareBill: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation received payment
	 *
	 * It is called by a waiter after the payment has been received
	 * 
	 */
	private void receivedPayment() {
		try
	      { 
			 barStub.receivedPayment();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on receivedPayment: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation return to the bar
	 *
	 * It is called by a waiter to return to the bar
	 * 
	 */
	private void returnToTheBar() {
		int ret = -1;
		try
	      { 
			ret = barStub.returnToTheBar(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBar: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation say goodbye
	 *
	 * It is called by a waiter to say goodbye to the student
	 * 
	 */
	private void sayGoodbye() {
		try
	      { 
			 barStub.sayGoodbye();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on sayGoodbye: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation salute the client.
	 *
	 * It is called by a waiter to salute the client
	 * 
	 */
	private void saluteTheClient() {
		int ret = -1;
		try
	      { 
			 tblStub.saluteTheClient(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on saluteTheClient: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	
	/**
	 * Operation get the pad
	 *
	 * It is called by a waiter to get the pad
	 * 
	 */
	private void getThePad() {
		int ret = -1;
		try
	      { 
			 ret = tblStub.getThePad(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on getThePad: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation deliver portion.
	 *
	 * It is called by a waiter to deliver a portion to a student
	 * 
	 */
	private void deliverPortion() {
		try
	      { 
			 tblStub.deliverPortion();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on deliverPortion: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation present the bill.
	 *
	 * It is called by a waiter to present the bill to the student
	 * 
	 */
	private void TablepresentBill() {
		int ret = -1;
		try
	      { 
			 ret = tblStub.presentBill(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on presentBill: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	
	/**
	 * Operation have all portions been served
	 *
	 * It is called by a waiter to know if all the portions have been served
	 * 
	 * @return  true if have all portions been served - 
	 * 		   false, otherwise 
	 */
	private boolean haveAllPortionsBeenServed() {
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.haveAllPortionsBeenServed();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on haveAllPortionsBeenServed: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
	/**
	 * Operation hand the note to the Chef.
	 *
	 * It is called by a waiter to deliver the order to the chef
	 * 
	 */
	private void handTheNoteToTheChef() {
		int ret = -1;
		try
	      { 
			 ret = kitStub.handTheNoteToTheChef(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on handTheNoteToTheChef: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
	
	/**
	 * Operation collect portion.
	 *
	 * It is called by a waiter to collect a portion
	 *
	 */
	private void collectPortion() {
		int ret = -1;
		try
	      { 
			 ret = kitStub.collectPortion(waiterID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on collectPortion: " + e.getMessage ());
	        System.exit (1);
	      }
		waiterState = ret;
	}
}

