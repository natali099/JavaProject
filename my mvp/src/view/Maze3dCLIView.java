package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class Maze3dCLIView.
 */
public class Maze3dCLIView extends Observable implements View {
	
	/** The CLI. */
	private CLI cli;

	/**
	 * Gets the cli.
	 *
	 * @return the cli
	 */
	public CLI getCli() {
		return cli;
	}

	/**
	 * Sets the cli.
	 *
	 * @param cli the new cli
	 */
	public void setCli(CLI cli) {
		this.cli = cli;
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
	 * Sets the CLI by given commands hashmap.
	 *
	 * @param commands the commands
	 */
	public void setCLI(HashMap<String, Command> commands) {
		this.cli = new CLI(this, new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out), commands);		
	}

	/**
	 * Displays data to the CLI.
	 *
	 * @param data the data to be sent to the CLI
	 * @see view.View#display(java.lang.String)
	 */
	@Override
	public void display(byte[] data) {
		cli.display(new String(data));		
	}
	
	/**
	 * Display error.
	 *
	 * @param error the error
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String error) {
		cli.display(error);

	}

	/**
	 * Display maze.
	 *
	 * @param maze the maze
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		cli.display(maze.toString());
		
	}

	/**
	 * Display solution.
	 *
	 * @param solution the solution
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		cli.display(solution.toString());
		
	}

	/**
	 * Display cross section.
	 *
	 * @param crossSection the cross section
	 */
	@Override
	public void displayCrossSection(int[][] crossSection) {
		cli.display(new Maze3d().printCrossSection(crossSection));
		
	}

	/**
	 * Notifies the observers with the arguments received.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the observers
	 * @see view.View#doCommand(presenter.Command, java.lang.String[])
	 */
	@Override
	public void doCommand(Command command, String[] args) {
		Object[] arg = new Object[2];
		arg[0] = command;
		arg[1] = args;
		setChanged();
		notifyObservers(arg);		
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		setCLI(commands);
		
	}

	/**
	 * Adds the observer.
	 *
	 * @param o the observer
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		
	}
}
