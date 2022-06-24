package clientSide.entities;


import interfaces.*;

import java.rmi.*;

import genclass.GenericIO;

/**
 * Chef thread.
 *
 *		It simulates the barber life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on remote calls under Java RMI.
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
	 * @param barStub       remote reference to the bar
	 * @param kitStub       remote reference to the kitchen
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
			BarAlertWaiter();
			// Transition to 'DLVPT'
			deliverPortion();
			while (!allPortionsDelived()) {
				// Transition to 'DSHPT'
				haveNextPortionReady();
				alertWaiter();
				// Transition to 'DLVPT'
				deliverPortion();
			}
		} while (!orderBeenCompleted());
		// Transition to 'CLSSV'
		cleanUp();
	}

	
	/**
	 * Operation watch the news.
	 *
	 * It is called by a chef while waiting for and order to be delivered by the
	 * waiter.
	 *
	 */
	private void watchTheNews() {
		int ret=-1;
		 try
	      { 
			ret= kitStub.watchTheNews(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on watchTheNews: " + e.getMessage ());
	        System.exit (1);
	      }
		 chefState=ret;
	}
	
	/**
	 * Operation start preparations.
	 *
	 * It is called by a chef after receiving and order
	 *
	 */
	private void startPreparations() { 
		int ret = -1;
		try
	      { 
			 ret =kitStub.startPreparations(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on startPreparations: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	
	/**
	 * Operation continue preparations.
	 *
	 * It is called by a chef after the chef delivered a portion
	 *
	 */
	private void continuePreparation() { 
		int ret = -1;
		try
	      { 
			ret = kitStub.continuePreparation(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on continuePreparation: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	
	/**
	 * Operation proceed to presentation.
	 *
	 * It is called by a chef after preparing a portion
	 *
	 */
	private void proceedToPresentation() { 
		int ret=-1;
		try
	      { 
			 ret = kitStub.proceedToPresentation(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on proceedToPresentation: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	
	/**
	 * Operation deliver portion.
	 *
	 * It is called by a chef to deliver a portion to the waiter
	 *
	 */
	private void deliverPortion() {
		int ret=-1;
		try
	      { 
			 ret = kitStub.deliverPortion(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on deliverPortion: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	
	/**
	 * Operation have next portion ready.
	 *
	 * It is called by a chef in order to start dishing another portion
	 *
	 */
	private void haveNextPortionReady() { 
		int ret = -1;
		try
	      { 
			 ret = kitStub.haveNextPortionReady(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on haveNextPortionReady: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portion is ready to be
	 * collected
	 *
	 */
	private void alertWaiter() { 
		try
	      { 
			 kitStub.alertWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on alertWaiter to the kitchen: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation clean up.
	 *
	 * It is called by a chef to finish its service
	 *
	 */
	private void cleanUp() { 
		int ret=-1;
		try
	      { 
			ret= kitStub.cleanUp(chefID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on cleanUp: " + e.getMessage ());
	        System.exit (1);
	      }
		chefState=ret;
	}
	
	/**
	 * Operation order been completed.
	 *
	 * It is called by a chef in order to know if the order has been completed
	 *
	 * @return true if  order has been completed - 
	 * 		   false, otherwise  
	 */
	private boolean orderBeenCompleted() { 
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
	
	/**
	 * Operation all portions delivered.
	 *
	 * It is called by a chef to know if all portion have been delivered
	 *
	 * @return true if have all portions delivered - 
	 * 		   false, otherwise 
	 */
	private boolean allPortionsDelived() {
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

	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portions is ready to be
	 * delivered
	 * 
	 */
	private void BarAlertWaiter() {
		try
	      { 
			barStub.alertWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Chef " + chefID + " remote exception on bar alertWaiter to the bar: " + e.getMessage ());
	        System.exit (1);
	      }
	}
}
