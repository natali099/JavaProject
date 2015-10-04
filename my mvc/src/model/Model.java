package model;

/**
 * The Interface Model.
 */
public interface Model {
	
	/**
	 * Runs the method represented by the received command.
	 *
	 * @param command the command, represented by a string
	 * @param args the parameters to be passed to the received method
	 */
	void doCommand(String command, String[] args);
	
}
