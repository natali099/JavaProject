package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Class BFS.
 *
 * @param <T> the generic type
 */
public class BFS<T> extends CommonSearcher<T> {

	/** The searchable to be searched. */
	protected Searchable<T> searchable;
	
	/**
	 * Gets the searchable.
	 *
	 * @return the searchable
	 */
	public Searchable<T> getSearchable() {
		return searchable;
	}

	/**
	 * Sets the searchable.
	 *
	 * @param searchable the new searchable
	 */
	public void setSearchable(Searchable<T> searchable) {
		this.searchable = searchable;
	}

	/**
	 * Searches using Best-First Search search algorithm.
	 *
	 * @param s the s
	 * @return the solution
	 * @see algorithms.search.CommonSearcher#search(algorithms.search.Searchable)
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		searchable = s;
		addToOpenList(searchable.getStartState());
		HashSet<T> closedSet=new HashSet<T>();
		
		while (openList.size()>0) {
			State<T> n = popOpenList(); //dequeue
		    closedSet.add(n.getState());

		    if (n.equals(searchable.getGoalState()))
		    	return backTrace(n, searchable.getStartState()); // private method, back traces through the parents
		    
		    ArrayList<State<T>> successors=searchable.getAllPossibleStates(n);
		    for (State<T> state : successors) {
		    	//state.setCost(n.getCost()+state.getCost()); //evaluate it
		    	setCostToState(state, n);
		    	state.setCameFrom(n); //record its parent
		    	if (!closedSet.contains(state.getState()) && !openList.contains(state)) {
		    		addToOpenList(state); //add it to OPEN
		    	} else if (openList.contains(state) && state.getCost() < getFromOpenList(state).getCost()) { //if this new path is better than previous one
		    		//adjust its priority in OPEN
		    		updateStateInOpenList(state);
		    	}
		    }
		}
		
		return null;
	}
	
	/**
	 * Sets the cost to state, using current state.
	 *
	 * @param state the state to set its cost
	 * @param current the state to use its cost information
	 */
	protected void setCostToState(State<T> state, State<T> current) {
		state.setCost(current.getCost()+state.getCost());
	}
}
