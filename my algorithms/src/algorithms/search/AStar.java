package algorithms.search;

/**
 * The Class AStar.
 *
 * @param <T> the generic type
 */
public class AStar<T> extends BFS<T> {
	
	/** The heuristic. */
	private Heuristic<T> h;
	
	/**
	 * Instantiates a new A* searcher.
	 *
	 * @param h the heuristic
	 */
	public AStar(Heuristic<T> h) {
		this.h = h;
	}
	
	/**
	 * Sets the cost to state, using current state and the heuristic h.
	 *
	 * @param state the state to set its cost
	 * @param current the state to use its cost information
	 * @see algorithms.search.BFS#setCostToState(algorithms.search.State, algorithms.search.State)
	 */
	@Override
	protected void setCostToState(State<T> state, State<T> current) {
		state.setCost(current.getCost()+state.getCost()+h.distance(state, searchable.getGoalState()));
	}
}
