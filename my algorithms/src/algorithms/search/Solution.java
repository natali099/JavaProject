package algorithms.search;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * The Class Solution.
 *
 * @param <T> the generic type
 */
public class Solution<T> implements Serializable {
	
	/** The solution path. */
	private LinkedList<State<T>> path;
	
	/**
	 * Instantiates a new solution.
	 */
	public Solution() {
		path = new LinkedList<State<T>>();
	}
	
	/**
	 * Gets the solution path.
	 *
	 * @return the solution path
	 */
	public LinkedList<State<T>> getPath() {
		return path;
	}
	
	/**
	 * Sets the solution path.
	 *
	 * @param path the new solution path
	 */
	public void setPath(LinkedList<State<T>> path) {
		this.path = path;
	}
	
	/**
	 * Adds the state to end of path.
	 *
	 * @param s the state to be added to the end
	 */
	public void addToEndOfPath(State<T> s) {
		path.add(s);
	}
	
	/**
	 * Adds the state to beginning of path.
	 *
	 * @param s the state to be added to the beginning
	 */
	public void addToBeginningOfPath(State<T> s) {
		path.addFirst(s);
	}
	
	/**
	 * Converts the solution into a readable string
	 * 
	 * @return the solution as a string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder solution = new StringBuilder();
		for (State<T> s : path) {
			solution.append(s.getState());
			solution.append("->");
		}
		solution.delete(solution.length()-2, solution.length());
		
		return solution.toString();
	}
}
