package controller;

/**
 * The Interface Command.
 */
public interface Command {
	
	/**
	 * Do command.
	 *
	 * @param args the parameters for the task
	 */
	void doCommand(String[] args);
}
