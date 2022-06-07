package interfaces;

import java.rmi.*;
/**
 *   Operational interface of a remote object of type Table.
 *
 *     It provides the functionality to access the Table.
 */

public interface TableInterface extends Remote{

	public void takeASeat(); //student
	public void selectingCourse(); //student
	public void organizeOrder(); //student
	public void informCompanions(); //student
	public void describeOrder(); //student
	public void chat(); //student
	public void enjoyMeal(); //student
	public void waitForEveryoneToFinish(); //student
	public void honorTheBill(); //student
	public boolean lastToEat(); //student
	public boolean firstToEnter(); //student
	public boolean lastToEnterRestaurant(); //student
	
	public void saluteTheClient(); //table
	public void getThePad(); //table
	public void deliverPortion(); //table  
	public void presentBill(); //table
	public boolean haveAllPortionsBeenServed(); //table
	
	
}
