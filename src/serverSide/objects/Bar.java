package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.Student;
import clientSide.entities.StudentStates;
import clientSide.entities.Waiter;
import clientSide.entities.WaiterStates;
import genclass.GenericIO;
import interfaces.BarInterface;
import interfaces.GeneralReposInterface;
import serverSide.main.ServerBar;
import serverSide.main.SimulPar;

/**
 * Bar.
 *
 * It is responsible for the the synchronization of the waiter, student and chef
 * during the order processing and is implemented as an implicit monitor.
 * 
 * There is two internal synchronization points: two blocking points for the
 * waiter, where he waits for something to happen and waits for the student to
 * signal that he can bring the next course.
 */
public class Bar implements BarInterface {

	/**
	 * Reference to the General Information Repository.
	 */
	private final GeneralReposInterface reposStub;
	/**
	 * Number of entity groups requesting the shutdown.
	 */

	private int nEntities;

	/**
	 * Reference to student threads.
	 */

	private final Thread[] student;
	/**
	 * Reference to waiter threads.
	 */

	private final Thread[] waiter;
	/**
	 * Char that represents the next operation of the waiter
	 */
	private char oper;
	/**
	 * Boolean flag that indicates if the payment was received
	 */
	private boolean paymentReceived;
	/**
	 * Boolean flag that indicates if wants pay
	 */
	private boolean wantsToPay;
	/**
	 * Boolean flag that indicates if described the order
	 */
	private boolean describedOrder;
	/**
	 * Boolean flag that indicates if signal to waiter
	 */
	private boolean signalWaiter;
	/**
	 * Boolean flag that indicates if bill honored
	 */
	private boolean billHonored;
	/**
	 * Boolean flag that indicates if called the waiter
	 */
	private boolean hasCalledWaiter;

	/**
	 * Boolean flag that indicates if need action
	 */
	private boolean actionNeeded;

	/**
	 * Boolean flag that indicates if bring next course
	 */
	private boolean bringNextCourse;

	/**
	 * Boolean flag that indicates if ready to pay
	 */
	private boolean readyToPay;
	/**
	 * Boolean flag that indicates if student called the waiter
	 */
	private boolean studentCalled;

	/**
	 * Boolean flag that indicates if waiter was alerted
	 */
	private boolean waiterAlerted;
	/**
	 * number of the students leave the restaurant
	 */
	private int nLeft;
	/**
	 * number of the students entered in the restaurant
	 */
	private int nEntered;
	/**
	 * number of the students that waiter saluted
	 */
	private int nSaluted;
	/**
	 * number of the students that waiter said goodbye
	 */
	private int nSaidGoodbye;

	/**
	 * Bar instantiation
	 * 
	 * @param reposStub reference to the General Information Repository
	 */
	public Bar(GeneralReposInterface reposStub) {
		this.reposStub = reposStub;
		student = new Thread[SimulPar.S];
		waiter = new Thread[SimulPar.W];
		for (int i = 0; i < SimulPar.S; i++)
			student[i] = null;
		for (int i = 0; i < SimulPar.W; i++)
			waiter[i] = null;

	}

	/**
	 * Operation look around
	 *
	 * It is called by a waiter to look around
	 * 
	 * @param waiterID waiter identifier
	 * @return oper operation to be performed
	 * @throws RemoteException 
	 */
	@Override
	public synchronized char lookAround(int waiterID) throws RemoteException{
		// if everyone left end
		if (nLeft == SimulPar.S) {
			setOper('e');
			return oper;
		}
		// if payment received while on other task wake up
		if (paymentReceived) {
			notifyAll();
		}
		// if someone left while on other task, say goodbye
		if (nLeft != nSaidGoodbye) {
			setActionNeeded(true);
			setOper('g');
		} else {
			if (nLeft != 0) {
				setActionNeeded(false);
			}
		}
		// if someone entered while on other task, salute him
		if (nSaluted != nEntered) {
			setOper('c');
			return oper;
		} else {
			setActionNeeded(false);
		}
		// if ready to pay was set while on other task, prepare operation 'b'
		if (readyToPay) {
			setOper('b');
			return oper;
		}
		// if a student called while the waiter was on other task, prepare
		// operation 'o'
		if (studentCalled) {
			setOper('o');
			return oper;
		}
		// if the chef called while the waiter was on other task, prepare
		// operation 'p'
		if (waiterAlerted) {
			setOper('p');
			return oper;
		}
		// if a student said that the next course can be delivered while the waiter was
		// on other task, prepare
		// operation 'p'
		if (bringNextCourse) {
			setOper('p');
			setActionNeeded(true);
		}

		// Sleep while waiting for something to happen
		while (!actionNeeded) {
			waiter[waiterID] = Thread.currentThread();
			try {
				wait();
			} catch (InterruptedException e) {
				waiter[waiterID] = null;
				return 'e';
			}
		}
		if (oper == 'p') {
			// Sleep while waiting for the student to signal it needs the next course
			while (!bringNextCourse) {
				waiter[waiterID] = Thread.currentThread();
				try {
					wait();
				} catch (InterruptedException e) {
					waiter[waiterID] = null;
					return 'e';
				}
			}
			// reset bringNextCourse flag
			bringNextCourse = false;
		}
		// reseting actionNeeded flag
		setActionNeeded(false);
		return oper;

	}

	/**
	 * Set actionNeeded flag
	 *
	 * @param action actionNeeded
	 */
	public synchronized void setActionNeeded(boolean action) {
		actionNeeded = action;
	}

	/**
	 * Set oper flag
	 *
	 * @param op oper
	 */
	public synchronized void setOper(char op) {
		oper = op;
	}

	/**
	 * Getter has called waiter
	 *
	 * Method that returns hasCalledWaiter flag
	 * 
	 * @return hasCalledWaiter 
	 */
	public synchronized boolean getHasCalledWaiter() {
		return hasCalledWaiter;
	}

	/**
	 * Set hasCalledWaiter flag
	 *
	 * @param hasCalledWaiter has called waiter
	 */
	public synchronized void setHasCalledWaiter(boolean hasCalledWaiter) {
		this.hasCalledWaiter = hasCalledWaiter;
	}

	/**
	 * Getter wants to pay
	 *
	 * Method that returns wantsToPay flag
	 * 
	 * @return wantsToPay
	 */
	public synchronized boolean getWantsToPay() {
		return wantsToPay;
	}

	/**
	 * Set wantsToPay flag
	 *
	 * @param wantsToPay wants to pay
	 */
	public synchronized void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}

	/**
	 * Getter described order
	 *
	 * Method that returns describedOrder flag
	 * 
	 * @return describedOrder
	 */
	public synchronized boolean getDescribedOrder() {
		return describedOrder;
	}

	/**
	 * Set describedOrder flag
	 *
	 * @param describedOrder described order
	 */
	public synchronized void setDescribedOrder(boolean describedOrder) {
		this.describedOrder = describedOrder;
	}

	/**
	 * Getter signal waiter
	 *
	 * Method that returns signalWaiter flag
	 * 
	 * @return signalWaiter
	 */
	public synchronized boolean getSignalWaiter() {
		return signalWaiter;
	}

	/**
	 * Set signalWaiter flag
	 *
	 * @param signalWaiter signal waiter
	 */
	public synchronized void setSignalWaiter(boolean signalWaiter) {
		this.signalWaiter = signalWaiter;
	}

	/**
	 * Getter bill honored
	 *
	 * Method that returns billHonored flag
	 * 
	 * @return billHonored
	 */
	public synchronized boolean getBillHonored() {
		return billHonored;
	}

	/**
	 * Set billHonored flag
	 *
	 * @param billHonored bill honored
	 */
	public synchronized void setBillHonored(boolean billHonored) {
		this.billHonored = billHonored;
	}

	/**
	 * Operation say goodbye
	 *
	 * It is called by a waiter to say goodbye to the student
	 * 
	 * @throws RemoteException 
	 */
	@Override
	public synchronized void sayGoodbye() throws RemoteException {
		nSaidGoodbye++;
	}

	/**
	 * Operation return to the bar
	 *
	 * It is called by a waiter to return to the bar
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized int returnToTheBar(int waiterID) throws RemoteException {
		
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.APPST);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBar - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		return WaiterStates.APPST;
	}

	/**
	 * Operation return to the bar after salute
	 *
	 * It is called by a waiter to return to the bar after saluting the student
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized int returnToTheBarAfterSalute(int waiterID) throws RemoteException {
		nSaluted++;
		// set state of waiter
		//((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		// waiter id
		//int waiterID = ((Waiter) Thread.currentThread()).getWaiterID();
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.APPST);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterSalute - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		return  WaiterStates.APPST;
	}

	/**
	 * Operation return to the bar after taking the order
	 *
	 * It is called by a waiter to return to the bar after taking the order
	 * @param waiterID waiter identifier
	 * @return WaiterStates.APPST
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized int returnToTheBarAfterTakingTheOrder(int waiterID) throws RemoteException {
		// reset student called flag
		studentCalled = false;
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.APPST);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterTakingTheOrder - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		
		return WaiterStates.APPST;
	}

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
	@Override
	public synchronized int returnToTheBarAfterPortionsDelivered(int waiterID) throws RemoteException {
		waiterAlerted = false;
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.APPST);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on returnToTheBarAfterPortionsDelivered - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		return WaiterStates.APPST;
	}

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
	@Override
	public synchronized int prepareBill(int waiterID) throws RemoteException {
		try
		{ 
			reposStub.setWaiterState(waiterID, WaiterStates.PRCBL);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Waiter " + waiterID + " remote exception on prepareBill - setWaiterState: " + e.getMessage ());
			System.exit (1);
		}
		return WaiterStates.PRCBL;
	}

	/**
	 * Operation «get the pad
	 *
	 * It is called by a waiter to get the pad
	 * 
	 */
	public synchronized void getThePad() {
		// wake up the student
		notifyAll();
	}

	/**
	 * Operation call the waiter
	 *
	 * It is called by a student to call the waiter to describe the order
	 * @throws RemoteException
	 */
	@Override
	public synchronized void callTheWaiter() throws RemoteException {
		// set bringNextCourse and studentCalled flag
		bringNextCourse = true;
		studentCalled = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('o');
		notifyAll();
	}

	/**
	 * Operation should have arrived earlier
	 *
	 * It is called by a student to warn the waiter that it is ready to pay the bill
	 *  @throws RemoteException
	 */
	@Override
	public synchronized void shouldHaveArrivedEarlier() throws RemoteException {
		// set ready to pay flag
		readyToPay = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('b');
		notifyAll();
	}

	/**
	 * Operation enter
	 *
	 * It is called by a student to enter the restaurant
	 *  @throws RemoteException
	 */
	@Override
	public synchronized void enter() throws RemoteException {
		// increment number of students that entered the restaurant
		nEntered++;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('c');
		notifyAll();
	}

	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portions is ready to be
	 * delivered
	 *  @throws RemoteException
	 */
	@Override
	public synchronized void alertWaiter() throws RemoteException {
		// set waiter alerted flag
		waiterAlerted = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();
	}

	/**
	 * Operation signal waiter
	 *
	 * It is called by a student to warn the waiter that it can start delivering the
	 * portions of the next course
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized void signalWaiter() throws RemoteException {
		// set bringNextCourse flag
		bringNextCourse = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();
	}

	/**
	 * Operation go home
	 *
	 * It is called by a student to warn the waiter that its going home
	 * @param studentID student identifier
	 * @return StudentStates.GGHOM
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized int goHome(int studentID) throws RemoteException {
		//int studentID;
		// set state of student
		//studentID = ((Student) Thread.currentThread()).getStudentID();
		//((Student) Thread.currentThread()).setStudentState(StudentStates.GGHOM);
		try
		{ 
			reposStub.setStudentState(studentID, StudentStates.GGHOM);
		}
		catch (RemoteException e)
		{ 
			GenericIO.writelnString ("Student " + studentID + " remote exception on goHome - setStudentState: " + e.getMessage ());
			System.exit (1);
		}
		// increment number of students that have left the restaurant
		nLeft++;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('g');
		notifyAll();
		return StudentStates.GGHOM;
	}

	/**
	 * Operation received payment
	 *
	 * It is called by a waiter after the payment has been received
	 * @throws RemoteException 
	 * 
	 */
	@Override
	public synchronized void receivedPayment() throws RemoteException {
		// set paymentReceived flag and reseting readyToPay flag
		paymentReceived = true;
		readyToPay = false;
	}

	/**
	 * Operation end of work.
	 *
	 * New operation.
	 * @param waiterID waiter identifier
	 * @throws RemoteException 
	 */
	@Override
	public synchronized void endOperation(int waiterID) throws RemoteException {
		while (nEntities < 2) { /* the waiter waits for the termination of the students */
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (waiter[waiterID] != null)
			waiter[waiterID].interrupt();
	}

	/**
	 * Operation server shutdown.
	 *
	 * New operation.
	 * @throws RemoteException 
	 */
	@Override
	public synchronized void shutdown() throws RemoteException {
		nEntities += 1;
		if (nEntities >= SimulPar.EB)
			ServerBar.shutdown ();
		notifyAll(); // the waiter may now terminate
	}

}
