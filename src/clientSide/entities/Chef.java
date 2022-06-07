package clientSide.entities;

import java.rmi.*;
import interfaces.*;
import genclass.GenericIO;

/**
 * Chef thread.
 *
 * Used to simulate the Chef life cycle.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class Chef extends Thread {
	/**
	 * Chef identification
	 */
	private int chefID;
	/**
	 * Chef state
	 */
	private int chefState;
	/**
	 * Reference to the Bar
	 */
	private final BarInterface barStub;
	/**
	 * Reference to the Kitchen
	 */
	private final KitchenInterface kitStub;

	/**
	 * Instantiation of a Chef Thread
	 * 
	 * @param name      thread name
	 * @param chefID    ID of the chef
	 * @param chefState state of the chef
	 * @param barStub       reference of the BarStub
	 * @param kitStub       reference of the KitchenStub
	 */
	public Chef(String name, int chefID, int chefState, BarInterface barStub, KitchenInterface kitStub) {
		super(name);
		this.chefID = chefID;
		this.chefState = ChefStates.WAFOR;
		this.barStub = barStub;
		this.kitStub = kitStub;
	}

	/**
	 * Get Chef ID
	 * 
	 * @return chefID
	 */
	public int getChefID() {
		return chefID;
	}

	/**
	 * Set Chef ID
	 * 
	 * @param chefID
	 */
	public void setChefID(int chefID) {
		this.chefID = chefID;
	}

	/**
	 * Get Chef state
	 * 
	 * @return chefState
	 */
	public int getChefState() {
		return chefState;
	}

	/**
	 * Set Chef state
	 * 
	 * @param chefState
	 */
	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

	/**
	 * Regulates the life cycle of the Chef
	 */

	@Override
	public void run() {
		boolean firstCourse = true;
		// Transition to 'WAFOR'
		watchTheNews();
		// Transition to 'PRPCS'
		startPreparations();
		do {
			if (!firstCourse) {
				// Transition to 'PRPCS'
				continuePreparation();
			} else {
				firstCourse = false;
			}
			// Transition to 'DSHPT'
			proceedToPresentation();
			BARalertWaiter();
			/*
			long v = (long) (1 + 40 * Math.random());
			try {
				Thread.sleep(v);
			} catch (InterruptedException e) {
			}*/
			// Transition to 'DLVPT'
			deliverPortion();
			while (!allPortionsDelived()) {
				// Transition to 'DSHPT'
				haveNextPortionReady();
				alertWaiter();
				/*
				v = (long) (1 + 40 * Math.random());
				try {
					Thread.sleep(v);
				} catch (InterruptedException e) {
				}
				*/
				// Transition to 'DLVPT'
				deliverPortion();
			}
		} while (!orderBeenCompleted());
		// Transition to 'CLSSV'
		cleanUp();
	}

	private void watchTheNews() { //kitStub
		 try
	      { 
			 kitStub.watchTheNews();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on watchTheNews: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void startPreparations() { //kitStub
		try
	      { 
			 kitStub.startPreparations();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on startPreparations: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void continuePreparation() { //kitStub
		try
	      { 
			 kitStub.continuePreparation();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on continuePreparation: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void proceedToPresentation() { //kitStub
		try
	      { 
			 kitStub.proceedToPresentation();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on proceedToPresentation: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void deliverPortion() { //kitStub
		try
	      { 
			 kitStub.deliverPortion();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on deliverPortion: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void haveNextPortionReady() { //kitStub
		try
	      { 
			 kitStub.haveNextPortionReady();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on haveNextPortionReady: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void alertWaiter() { //kitStub
		try
	      { 
			 kitStub.alertWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on alertWaiter to the kitchen: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void cleanUp() { //kitStub
		try
	      { 
			 kitStub.cleanUp();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on cleanUp: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private boolean orderBeenCompleted() { //kitStub
		 boolean ret = false;   // return value

	      try
	      { ret = kitStub.orderBeenCompleted();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on orderBeenCompleted: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	private boolean allPortionsDelived() { //kitStub
		 boolean ret = false;   // return value

	      try
	      { ret = kitStub.allPortionsDelived();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on allPortionsDelived: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}

	private void BARalertWaiter() { //BarStub
		try
	      { 
			barStub.alertWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on alertWaiter to the bar: " + e.getMessage ());
	        System.exit (1);
	      }
	}
}
