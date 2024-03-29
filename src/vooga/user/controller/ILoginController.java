package vooga.user.controller;

import vooga.user.view.gui.middleFrame.TextSection;
import vooga.user.voogauser.VoogaUser;

/**
 * Interface for the Controller in the MVC pattern for the login structure.
 * 
 * @author Conrad.
 */
public interface ILoginController
{

/**
 * This method displays an error on the screen when the user enters incorrect input as a password
 */
	public void displayError(String s);
	
/**
 * Process information is called by the view to evaluate the input entered in the GUI panel.
 * Method also returns an optional boolean of whether or not the file can be processed
 */
	public boolean processInformation(String[] text);

/**
 * This method verifys that a log-in password and username exist in a given password bank or database
 */
	public boolean approveLogin(String user, String password);

/**
 * Update with input is called by the model to update the prompt questions in the view. Uber helpful
 */
	public void updateWithInput();
	
/**
 * Updates the GUI with a LoginTemplate that contains a mere text section type. Uses information from the database
 */
	public void updateWithInformationPanel();
	
/**
 * Returns the original VoogaUser. This method was created in collaboration with the arcade. Still to be determined if necessary
 */
	public VoogaUser getVooga();
	
/**
 * This method saves the VoogaUser information and exits the Login of the system
*/
	void logIn();
	
/**
 * This method logs the VoogaUser out od the system. To preserve their privacy and enable others to log on.
 */
	void logOut();
}
