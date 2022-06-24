package clientSide.entities;


import genclass.GenericIO;
import java.rmi.*;
import interfaces.*;
import serverSide.main.SimulPar;
/**
 * Student thread.
 *
 *		It simulates the barber life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on remote calls under Java RMI.
 */
public class Student extends Thread {

	/**
	 * Student identification
	 */
	private int studentID;
	
	/**
	 * Student state
	 */
	private int studentState;

	/*
	 * Number of the seat at the table
	 */
	private int seat;

	/**
	 * Reference to the Bar
	 */
	private final BarInterface barStub;

	/**
	 * Reference to the Table
	 */
	private final TableInterface tblStub;

	/**
	 * Instantiation of a Student Thread
	 * 
	 * @param name      thread main
	 * @param studentID ID of the student
	 * @param barStub       remote reference to the bar
	 * @param tblStub       remote reference to the table
	 */
	public Student(String name, int studentID, BarInterface barStub, TableInterface tblStub) {
		super(name);
		this.studentID = studentID;
		this.studentState = StudentStates.GGTRT;
		this.barStub = barStub;
		this.tblStub = tblStub;
		this.seat = -1;
	}

	/**
	 * Get Student ID
	 * 
	 * @return studentID
	 */
	public int getStudentID() {
		return studentID;
	}

	/**
	 * Set Student ID
	 * 
	 * @param studentID
	 */
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	/**
	 * Set number of the seat at the table
	 * 
	 * @param seat
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}

	/**
	 * Get Student state
	 * 
	 * @return studentState
	 */
	public int getStudentState() {
		return studentState;
	}

	/**
	 * Get number of the seat at the table
	 * 
	 * @return seat
	 */
	public int getSeat() {
		return seat;
	}

	/**
	 * Set Student state
	 * 
	 * @param studentState
	 */
	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	/**
	 * Regulates the life cycle of the Student
	 */
	@Override
	public void run() {
		// Transition to 'GGTRT'
		walk();
		enter();
		// Transition to 'TKSTT'
		takeASeat();
		// Transition to 'SELCS'
		selectingCourse();
		if (!firstToEnter()) {
			informCompanions();
		} else {
			// Transition to 'OGODR'
			organizeOrder();
			callTheWaiter();
			describeOrder();
		}
		/*
		long v = (long) (1 + 40 * Math.random());
		try {
			Thread.sleep(v);
		} catch (InterruptedException e) {
		}*/
		//GenericIO.writelnString("Going chat");
		for (int i = 0; i < SimulPar.M; i++) {
			// Transition to 'CHTWC'
			chat();
			//GenericIO.writelnString("Going to eat");
			// Transition to 'EJYML'
			enjoyMeal();
			if (lastToEat()) {
				if (i != 2) {
					signalWaiter();
					/*
					v = (long) (1 + 40 * Math.random());
					try {
						Thread.sleep(v);
					} catch (InterruptedException e) {
					}*/
				}
				//tblStub.chatAgain();
			} else {
				// Transition to 'CHTWC'
				waitForEveryoneToFinish();
			}
		}
		if (lastToEnterRestaurant()) {
			shouldHaveArrivedEarlier();
			// Transition to 'PYTBL'
			honorTheBill();
		}
		// Transition to 'GGHOM'
		goHome();
	}
	
	/**
	 * Operation walk
	 *
	 * It is called by a student to wander before entering the restaurant
	 * 
	 * Internal operation.
	 */
	public void walk() {
		long v = (long) (1 + 40 * Math.random());
		try {
			Thread.sleep(v);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Operation enter
	 *
	 * It is called by a student to enter the restaurant
	 * 
	 */
	private void enter() {
		try
	      { 
			 barStub.enter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on enter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation call the waiter
	 *
	 * It is called by a student to call the waiter to describe the order
	 * 
	 */
	private void callTheWaiter() {
		try
	      { 
			 barStub.callTheWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on callTheWaiter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	
	/**
	 * Operation signal waiter
	 *
	 * It is called by a student to warn the waiter that it can start delivering the
	 * portions of the next course
	 * 
	 */
	private void signalWaiter() {
		try
	      { 
			 barStub.signalWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on signalWaiter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation should have arrived earlier
	 *
	 * It is called by a student to warn the waiter that it is ready to pay the bill
	 * 
	 */
	private void shouldHaveArrivedEarlier() {
		try
	      { 
			 barStub.shouldHaveArrivedEarlier();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on shouldHaveArrivedEarlier: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation go home
	 *
	 * It is called by a student to warn the waiter that its going home
	 * 
	 */
	private void goHome() {
		int ret = -1;
		try
	      { 
			 ret = barStub.goHome(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on goHome: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation take a seat
	 *
	 * It is called by a student when it wants to take a seat at the table
	 * 
	 */
	private void takeASeat() {
		ReturnInt ret = null;
		try
	      { 
			ret = tblStub.takeASeat(studentID);
			
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on takeASeat: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret.getIntStateVal();
		seat = ret.getIntVal();
	}
	
	/**
	 * Operation selecting the course
	 *
	 * It is called by a student to know if all the portions have been served
	 * 
	 */
	private void selectingCourse() {
		int ret = -1;
		try
	      { 
			ret = tblStub.selectingCourse(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on selectingCourse: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation organize order
	 *
	 * It is called by a student to start organizing the order
	 * 
	 */
	private void organizeOrder() { 
		int ret = -1;
		try
	      { 
			ret = tblStub.organizeOrder(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on organizeOrder: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation inform companions
	 *
	 * It is called by a student to inform the companion about its order
	 */
	private void informCompanions() { 
		try
	      { 
			tblStub.informCompanions();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on informCompanions: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation describe order
	 *
	 * It is called by a student to describe the order to the waiter
	 * 
	 */
	private void describeOrder() {
		try
	      { 
			tblStub.describeOrder();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on describeOrder: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	/**
	 * Operation chat
	 *
	 * It is called by a student to start chatting with the companions
	 * 
	 */
	private void chat() {
		int ret = -1;
		try
	      { 
			ret = tblStub.chat(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on chat: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation enjoy the meal
	 *
	 * It is called by a student to start eating the portion
	 * 
	 */
	private void enjoyMeal() {
		int ret = -1;
		try
	      { 
			ret = tblStub.enjoyMeal(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on enjoyMeal: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation wait for everyone to finish
	 *
	 * It is called by a student to wait of everyone to finish eating the current
	 * course
	 * 
	 */
	private void waitForEveryoneToFinish() {
		int ret = -1;
		try
	      { 
			ret = tblStub.waitForEveryoneToFinish(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on waitForEveryoneToFinish: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation honor the bill
	 *
	 * It is called by a student to honor the bill
	 * 
	 */
	private void honorTheBill() { 
		int ret = -1;
		try
	      { 
			ret = tblStub.honorTheBill(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on honorTheBill: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	/**
	 * Operation last to eat
	 *
	 * It is called by a student to know if it was the last to eat the portion
	 * 
	 * @return true if the student was the last to eat - 
	 * 		   false, otherwise 
	 */
	private boolean lastToEat() { 
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.lastToEat(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on lastToEat: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
	/**
	 * Operation first to enter
	 *
	 * It is called by a student to know if it was the first to enter in the
	 * restaurant
	 * 
	 * @return  true if the student was the first to enter - 
	 * 		   false, otherwise 
	 */
	private boolean firstToEnter() {
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.firstToEnter(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on firstToEnter: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
	/**
	 * Operation last to enter restaurant
	 *
	 * It is called by a student to know if it was the last to enter in the
	 * restaurant
	 * 
	 * @return true if the student was the last to enter in the restaurant - 
	 * 		   false, otherwise 
	 */
	private boolean lastToEnterRestaurant() {
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.lastToEnterRestaurant(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on lastToEnterRestaurant: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
}
