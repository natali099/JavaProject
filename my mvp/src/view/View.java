package view;

import controller.Command;

public interface View {

	/**
	 * Starts the view.
	 */
	void start();
	
	/**
	 * Displays data.
	 *
	 * @param data the data to be displayed, represented by a byte array
	 */
	void display(byte[] data);
	
	/**
	 * Runs doCommand method in the controller.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the received command
	 */
	void doCommand(Command command, String[] args);
}
