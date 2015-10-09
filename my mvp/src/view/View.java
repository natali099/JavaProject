package view;

import presenter.Command;

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
	 * Notifies the observers with the arguments received.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the observers
	 */
	void doCommand(Command command, String[] args);
}
