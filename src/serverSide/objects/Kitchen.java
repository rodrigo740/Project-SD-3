package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.Chef;
import clientSide.entities.ChefStates;
import clientSide.entities.Waiter;
import clientSide.entities.WaiterStates;
import genclass.GenericIO;
import interfaces.GeneralReposInterface;
import interfaces.KitchenInterface;
import serverSide.main.ServerKitchen;
import serverSide.main.SimulPar;

public class Kitchen implements KitchenInterface{

	/**
	 * Kitchen.
	 *
	 * It is responsible for the the synchronization of the chef and waiter during
	 * the order processing and is implemented as an implicit monitor.
	 * 
	 * There is three internal synchronization points: two blocking points for the
	 * chef, where he waits for an order to arrive and to wait for the waiter to
	 * collect a portion; And a single blocking point for the waiter, where he waits
	 * for a portion to be collected
	 */

	/**
	 * Reference to the General Information Repository.
	 */
	private final GeneralReposInterface reposStub;
	/**
	 * Reference to chef threads.
	 */

	private final Thread[] chef;
	/**
	 * Boolean flag that indicates if the order has arrived
	 */
	private boolean orderArrived;
	/**
	 * Boolean flag that indicates if the portion was collected
	 */
	private boolean portionCollected;
	/**
	 * Boolean flag that indicates if the portion is ready
	 */
	private boolean portionReady;
	/**
	 * number of the delivered courses
	 */
	private int deliveredCourses;
	/**
	 * number of the delivered portions
	 */
	private int deliveredPortions;
	/**
	 * Number of entity groups requesting the shutdown.
	 */

	private int nEntities;

	/**
	 * Kitchen instantiation
	 * 
	 * @param reposStub reference to the General Information Repository
	 */
	public Kitchen(GeneralReposInterface reposStub) {
		this.reposStub = reposStub;
		nEntities = 0;
		chef = new Thread[SimulPar.C];
		for (int i = 0; i < SimulPar.C; i++)
			chef[i] = null;

	}

	/**
	 * Set Portion Collected Flag.
	 *
	 * @param collected portion collected
	 */

	public synchronized void setPortionCollected(boolean collected) {
		portionCollected = collected;
	}

	/**
	 * Set Portion Ready Flag.
	 *
	 * @param ready collected portion ready
	 */

	public synchronized void setPortionReady(boolean ready) {
		portionReady = ready;
	}

	/**
	 * Set Order Arrived Flag.
	 *
	 * @param arrived order has arrived
	 */

	public synchronized void setOrderArrived(boolean arrived) {
		orderArrived = arrived;
	}

	/**
	 * Operation hand the note to the Chef.
	 *
	 * It is called by a waiter to deliver the order to the chef
	 * @param waiterID waiter identifier
	 * @return WaiterStates.PCODR
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized int handTheNoteToTheChef(int waiterID) throws RemoteException {
		// set state of waiter
		//((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PCODR);
		//int waiterID = ((Waiter) Thread.currentThread()).getWaiterID();
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.PCODR);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on handTheNoteToTheChef - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		// set orderArrived flag and wake chef
		setOrderArrived(true);
		notifyAll();
		return WaiterStates.PCODR;
	}

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
	@Override
	public synchronized int watchTheNews(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.WAFOR);

		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.WAFOR);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on watchTheNews - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		
		// Sleep while waiting for order to arrive
		while (!orderArrived) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		// order has arrived reseting flag
		setOrderArrived(false);
		return ChefStates.WAFOR;
	}

	/**
	 * Operation start preparations.
	 *
	 * It is called by a chef after receiving and order
	 * @param chefId chef identifier
	 * @return ChefStates.PRPCS
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int startPreparations(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.PRPCS);
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.PRPCS);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on startPreparations - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		return ChefStates.PRPCS;
	}

	/**
	 * Operation continue preparations.
	 *
	 * It is called by a chef after the chef delivered a portion
	 * @param chefId chef identifier
	 * @return ChefStates.PRPCS
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int continuePreparation(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.PRPCS);
				
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.PRPCS);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on continuePreparation - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		
		
		// reset delivered portions
		deliveredPortions = 0;
		return ChefStates.PRPCS;
	}

	/**
	 * Operation proceed to presentation.
	 *
	 * It is called by a chef after preparing a portion
	 * @param chefId chef identifier
	 * @return ChefStates.DSHPT
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int proceedToPresentation(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.DSHPT);
		
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.DSHPT);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on proceedToPresentation - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		// set portionReady flag
		setPortionReady(true);
		return ChefStates.DSHPT;
	}

	/**
	 * Operation all portions delivered.
	 *
	 * It is called by a chef to know if all portion have been delivered
	 *
	 * @return true, if all portion have been delivered -
     *            false, otherwise
	 */
	@Override
	public synchronized boolean allPortionsDelived() {
		return SimulPar.N == deliveredPortions;
	}

	/**
	 * Operation deliver portion.
	 *
	 * It is called by a chef to deliver a portion to the waiter
	 * @param chefId chef identifier
	 * @return ChefStates.DLVPT
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int deliverPortion(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.DLVPT);
				
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.DLVPT);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on proceedToPresentation - setChefState: " + e.getMessage ());
			System.exit (1);
		}
			
		// set portionReady flag
		//setPortionReady(true);
		//notifyAll();
		// Sleep while waiting for the portion to be collected
		while (!portionCollected) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		// increment delivered portions
		deliveredPortions++;
		// reseting portionCollected flag
		setPortionCollected(false);
		return ChefStates.DLVPT;
	}

	/**
	 * Operation collect portion.
	 *
	 * It is called by a waiter to collect a portion
	 * @param waiterID waiter identifier
	 * @return WaiterStates.WTFPT
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int collectPortion(int waiterID) throws RemoteException {
		// set state of waiter
		//((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.WTFPT);
				
		//int waiterID = ((Waiter) Thread.currentThread()).getWaiterID();
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.WTFPT);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on collectPortion - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		
		
		// Sleep while waiting for a portion to be ready
		while (!portionReady) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		// reset portionReady flag
		setPortionReady(false);
		// Set portionCollected flag and wake the chef
		setPortionCollected(true);
		notifyAll();
		return WaiterStates.WTFPT;
	}

	/**
	 * Operation have next portion ready.
	 *
	 * It is called by a chef in order to start dishing another portion
	 * @param chefId chef identifier
	 * @return ChefStates.DSHPT
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int haveNextPortionReady(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.DSHPT);
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.DSHPT);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on haveNextPortionReady - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		return  ChefStates.DSHPT;
	}

	/**
	 * Operation order been completed.
	 *
	 * It is called by a chef in order to know if the order has been completed
	 *
	 * @return true, if the order has been completed -
     *            false, otherwise
     * @throws RemoteException
	 */
	@Override
	public synchronized boolean orderBeenCompleted() throws RemoteException{
		// increment delivered courses
		deliveredCourses++;
		return SimulPar.M == deliveredCourses;
	}

	/**
	 * Operation clean up.
	 *
	 * It is called by a chef to finish its service
	 * @param chefId chef identifier
	 * @return ChefStates.CLSSV
	 * @throws RemoteException 
	 *
	 */
	@Override
	public synchronized int cleanUp(int chefId) throws RemoteException {
		// set state of chef
		//((Chef) Thread.currentThread()).setChefState(ChefStates.CLSSV);
		//int chefId = ((Chef) Thread.currentThread()).getChefID();
		try
		{ 
			reposStub.setChefState(chefId, ChefStates.CLSSV);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Chef " + chefId + " remote exception on cleanUp - setChefState: " + e.getMessage ());
			System.exit (1);
		}
		return ChefStates.CLSSV;
	}

	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portion is ready to be
	 * collected
	 * @throws RemoteException
	 */
	@Override
	public synchronized void alertWaiter() throws RemoteException{
		// set portionReady flag and waking up the waiter
		setPortionReady(true);
		notifyAll();
	}

	/**
	 * Operation end of work.
	 *
	 * New operation.
	 * 
	 * @param chefID chef identification
	 * @throws RemoteException
	 */
	@Override
	public synchronized void endOperation(int chefID) throws RemoteException{
		while (nEntities == 0) { /* the chef waits for the termination of the customers */
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (chef[chefID] != null)
			chef[chefID].interrupt();
	}

	/**
	 * Operation server shutdown.
	 *
	 * New operation.
	 * @throws RemoteException
	 */
	@Override
	public synchronized void shutdown() throws RemoteException{
		nEntities += 1;
		if (nEntities >= SimulPar.EK)
			ServerKitchen.shutdown();
		notifyAll(); // the chef may now terminate
	}

}
