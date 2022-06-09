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
	   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                             service fails
	   */

	   public void initSimul (String logFileName) throws RemoteException;

	  /**
	   *   Operation server shutdown.
	   *
	   *   New operation.
	   *
	   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
	   *                             service fails
	   */

	   public void shutdown () throws RemoteException;
	   
	   public void setWaiterState(int waiterID, int waiterState) throws RemoteException;
	   
	   public void setStudentState(int studentID, int studentState) throws RemoteException;
	   
	   public void setChefState (int chefID, int chefState) throws RemoteException;
	   
	   public void setPortionsDelivered (int portionsDelivered) throws RemoteException;
	   
	   public void setStudentSeat(int studentID, int nStudents) throws RemoteException;
	   
	   public void setCoursesDelivered (int coursesDelivered) throws RemoteException;
	
	
}
