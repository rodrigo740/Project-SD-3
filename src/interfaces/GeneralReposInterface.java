package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type Table.
 *
 *     It provides the functionality to access the Table.
 */

public interface GeneralReposInterface extends Remote{

	
	 /**
	   *   Operation initialization of simulation.
	   *
	   *   New operation.
	   *
	   *     @param logFileName name of the logging file
	   *     @param nIter number of iterations of the customer life cycle
	   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                             service fails
	   */

	   public void initSimul (String logFileName, int nIter) throws RemoteException;

	  /**
	   *   Operation server shutdown.
	   *
	   *   New operation.
	   *
	   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                             service fails
	   */

	   public void shutdown () throws RemoteException;
	   
	   public void setWaiterState(int waiterID, int waiterState);
	   
	   public void setStudentState(int studentID, int studentState);
	   
	   public void setChefState (int chefID, int chefState);
	   
	   public void setPortionsDelivered (int portionsDelivered);
	   
	   public void setStudentSeat(int studentID, int nStudents);
	   
	   public void setCoursesDelivered (int coursesDelivered);
	
	
}
