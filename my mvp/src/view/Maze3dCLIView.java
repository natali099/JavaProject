package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import controller.Command;

public class Maze3dCLIView extends Observable implements View {
	
	/** The CLI. */
	private CLI cli;

	public Maze3dCLIView(/*HashMap<String, Command> commands*/) {
		//this.setCLI(commands);
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
	public void display(byte[] data) {
		cli.display(new String(data));
		
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
		Object[] arg = new Object[2];
		arg[0] = command;
		arg[1] = args;
		setChanged();
		notifyObservers(arg);
		//c.doCommand(command, args);
		
	}
}
