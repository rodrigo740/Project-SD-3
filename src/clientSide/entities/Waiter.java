package clientSide.entities;

import java.rmi.*;

import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import genclass.GenericIO;
import interfaces.*;

/**
 * Waiter thread.
 *
 * Used to simulate the Waiter life cycle.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
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
	 * @param barStub         reference to the bar
	 * @param kitStub         reference to the kitchen
	 * @param tblStub         reference to the table
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
	
	private char lookAround() { //barStub
		char ret = ' ';
		try
	      { 
			 ret = barStub.lookAround();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on lookAround: " + e.getMessage ());
	        System.exit (1);
	      }
		return ret;
	}
	
	private void returnToTheBarAfterSalute() { //barStub
		try
	      { 
			 barStub.returnToTheBarAfterSalute();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterSalute: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void returnToTheBarAfterTakingTheOrder() { //barStub
		try
	      { 
			 barStub.returnToTheBarAfterTakingTheOrder();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterTakingTheOrder: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void returnToTheBarAfterPortionsDelivered() { //barStub
		try
	      { 
			 barStub.returnToTheBarAfterPortionsDelivered();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterPortionsDelivered: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void prepareBill() { //barStub
		try
	      { 
			 barStub.prepareBill();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on prepareBill: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void receivedPayment() { //barStub
		try
	      { 
			 barStub.receivedPayment();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on receivedPayment: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void returnToTheBar() { //barStub
		try
	      { 
			 barStub.returnToTheBar();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBar: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	
	private void sayGoodbye() { //barStub
		try
	      { 
			 barStub.sayGoodbye();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on sayGoodbye: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	
	private void saluteTheClient() { //tblStub
		try
	      { 
			 tblStub.saluteTheClient();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on saluteTheClient: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void getThePad() { //tblStub
		try
	      { 
			 tblStub.getThePad();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on getThePad: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void deliverPortion() { //tblStub
		try
	      { 
			 tblStub.deliverPortion();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on deliverPortion: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void TablepresentBill() { //tblStub
		try
	      { 
			 tblStub.presentBill();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on presentBill: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private boolean haveAllPortionsBeenServed() { //tblStub
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
	

	private void handTheNoteToTheChef() { //kitStub
		try
	      { 
			 kitStub.handTheNoteToTheChef();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on handTheNoteToTheChef: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void collectPortion() { //kitStub
		try
	      { 
			 kitStub.collectPortion();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Waiter " + waiterID + " remote exception on collectPortion: " + e.getMessage ());
	        System.exit (1);
	      }
	}
}

