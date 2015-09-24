package view;

import controller.Command;

/**
 * The Interface View.
 */
public interface View {
	
	/**
	 * Starts the view.
	 */
	void start();
	
	/**
	 * Displays a message.
	 *
	 * @param message the message
	 */
	void display(String message);
	
	/**
	 * Runs doCommand method in the controller.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the received command
	 */
	void doCommand(Command command, String[] args);
	
}
