package controller;

import model.Model;
import view.View;

/**
 * The Interface Controller.
 */
public interface Controller {
	
	/**
	 * Sets the model.
	 *
	 * @param m the model
	 */
	void setModel(Model m);
	
	/**
	 * Sets the view.
	 *
	 * @param v the view
	 */
	void setView(View v);
	
	/**
	 * Runs display method in the view.
	 *
	 * @param message the message to be displayed
	 */
	void display(String message);
	
	/**
	 * Runs doCommand method in the received command.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the received command
	 */
	void doCommand(Command command, String[] args);

}
