package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;

/**
 * The Class MyView.
 */
public class MyView implements View {
	
	/** The controller. */
	private Controller c;
	
	/** The CLI. */
	private CLI cli;

	/**
	 * Instantiates a new my view.
	 *
	 * @param c the controller
	 * @param commands the commands hash map for the CLI
	 */
	public MyView (Controller c, HashMap<String, Command> commands) {
		this.c = c;
		this.setCLI(commands);
	}

	/**
	 * Runs start method in the CLI.
	 * 
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		cli.start();
		
	}

	/**
	 * Sets the CLI.
	 *
	 * @param commands the commands
	 */
	public void setCLI(HashMap<String, Command> commands) {
		this.cli = new CLI(this, new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out), commands);
		
	}

	/**
	 * Displays a message to the CLI.
	 *
	 * @param message the message
	 * @see view.View#display(java.lang.String)
	 */
	@Override
	public void display(String message) {
		cli.display(message);
		
	}

	/**
	 * Runs doCommand method in the controller.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the received command
	 * @see view.View#doCommand(controller.Command, java.lang.String[])
	 */
	@Override
	public void doCommand(Command command, String[] args) {
		c.doCommand(command, args);
		
	}

}
