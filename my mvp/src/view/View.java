package view;

import java.util.HashMap;
import java.util.Observer;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Interface View.
 */
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
	 * Display error.
	 *
	 * @param error the error
	 */
	void displayError(String error);
	
	/**
	 * Display maze.
	 *
	 * @param maze the maze
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * Display solution.
	 *
	 * @param solution the solution
	 */
	void displaySolution(Solution<Position> solution);
	
	/**
	 * Display cross section.
	 *
	 * @param crossSection the cross section
	 */
	void displayCrossSection(int[][] crossSection);
	
	/**
	 * Notifies the observers with the arguments received.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the observers
	 */
	void doCommand(Command command, String[] args);

	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	void setCommands(HashMap<String, Command> commands);

	/**
	 * Adds the observer.
	 *
	 * @param o the observer
	 */
	void addObserver(Observer o);
}
