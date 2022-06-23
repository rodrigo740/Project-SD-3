package clientSide.entities;


import genclass.GenericIO;
import java.rmi.*;
import interfaces.*;
import serverSide.main.SimulPar;
/**
 * Student thread.
 *
 * Used to simulate the Student life cycle.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
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
	 * @param barStub       reference to the BarStub
	 * @param tblStub       reference to the TableStub
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
	
	private void enter() { //barStub
		try
	      { 
			 barStub.enter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on enter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void callTheWaiter() { //barStub
		try
	      { 
			 barStub.callTheWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on callTheWaiter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void signalWaiter() { //barStub
		try
	      { 
			 barStub.signalWaiter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on signalWaiter: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void shouldHaveArrivedEarlier() { //barStub
		try
	      { 
			 barStub.shouldHaveArrivedEarlier();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on shouldHaveArrivedEarlier: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void goHome() { //barStub
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
	
	private void takeASeat() { //tblStub
		int ret = -1;
		try
	      { 
			ret = tblStub.takeASeat(studentID);
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on takeASeat: " + e.getMessage ());
	        System.exit (1);
	      }
		studentState = ret;
	}
	
	private void selectingCourse() { //tblStub
		try
	      { 
			tblStub.selectingCourse();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on selectingCourse: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	
	private void organizeOrder() { //tblStub
		try
	      { 
			tblStub.organizeOrder();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on organizeOrder: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void informCompanions() { //tblStub
		try
	      { 
			tblStub.informCompanions();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on informCompanions: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void describeOrder() { //tblStub
		try
	      { 
			tblStub.describeOrder();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on describeOrder: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void chat() { //tblStub
		try
	      { 
			tblStub.chat();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on chat: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void enjoyMeal() { //tblStub
		try
	      { 
			tblStub.enjoyMeal();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on enjoyMeal: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private void waitForEveryoneToFinish() { //tblStub
		try
	      { 
			tblStub.waitForEveryoneToFinish();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on waitForEveryoneToFinish: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	private void honorTheBill() { //tblStub
		try
	      { 
			tblStub.honorTheBill();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on honorTheBill: " + e.getMessage ());
	        System.exit (1);
	      }
	}
	
	private boolean lastToEat() { //tblStub
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.lastToEat();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on lastToEat: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
	private boolean firstToEnter() { //tblStub
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.firstToEnter();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on firstToEnter: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
	private boolean lastToEnterRestaurant() { //tblStub
		 boolean ret = false;   // return value

	      try
	      { ret = tblStub.lastToEnterRestaurant();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Student " + studentID + " remote exception on lastToEnterRestaurant: " + e.getMessage ());
	        System.exit (1);
	      }
	      return ret;
	}
	
}
