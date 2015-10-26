package algorithms.search;

import java.util.PriorityQueue;

/**
 * The Class CommonSearcher.
 *
 * @param <T> the generic type
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

	 /** The open list. */
 	protected PriorityQueue<State<T>> openList;
	 
 	/** The number of evaluated nodes. */
 	private int evaluatedNodes;

	 /**
 	 * Gets the open list.
 	 *
 	 * @return the open list
 	 */
 	public PriorityQueue<State<T>> getOpenList() {
		return openList;
	}

	/**
	 * Sets the open list.
	 *
	 * @param openList the new open list
	 */
	public void setOpenList(PriorityQueue<State<T>> openList) {
		this.openList = openList;
	}

	/**
	 * Gets the evaluated nodes.
	 *
	 * @return the evaluated nodes
	 */
	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	/**
	 * Sets the evaluated nodes.
	 *
	 * @param evaluatedNodes the new evaluated nodes
	 */
	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}

	/**
 	 * Instantiates a new common searcher.
 	 */
 	public CommonSearcher() {
	  openList=new PriorityQueue<State<T>>();
	  evaluatedNodes=0;
 	}

	 /**
 	 * Pop open list.
 	 *
 	 * @return the state with highest priority
 	 */
 	protected State<T> popOpenList() {
	  evaluatedNodes++;
	  return openList.poll();
 	}

	 /**
 	 * Search.
 	 *
 	 * @param s the s
 	 * @return the solution
 	 * @see algorithms.search.Searcher#search(algorithms.search.Searchable)
 	 */
 	@Override
	 public abstract Solution<T> search(Searchable<T> s);

	 /**
 	 * Gets the number of nodes evaluated.
 	 *
 	 * @return the number of nodes evaluated
 	 * @see algorithms.search.Searcher#getNumberOfNodesEvaluated()
 	 */
 	@Override
	 public int getNumberOfNodesEvaluated() {
	  return evaluatedNodes;
 	}

	 /**
 	 * Adds a state to open list.
 	 *
 	 * @param s the state to be added to open list
 	 */
 	protected void addToOpenList(State<T> s) {
		 openList.add(s);
 	}
 	
 	/**
	  * Gets a state from open list.
	  *
	  * @param s the state
	  * @return the state from open list
	  */
	 protected State<T> getFromOpenList(State<T> s) {
 		State<T> stateInOpenList=null;
		for (State<T> st : openList) {
			if (st.equals(s)) {
				stateInOpenList = new State<T>(st.getState());
				stateInOpenList.setCost(st.getCost());
				break;
			}
		}
		return stateInOpenList;
	 }
	 
	 /**
 	 * Removes a state from open list and adds it back to update its priority.
 	 *
 	 * @param s the state to be updated
 	 */
 	protected void updateStateInOpenList(State<T> s) {
		 openList.remove(s);
		 addToOpenList(s);
	 }
	 
	/**
	 * Back trace.
	 *
	 * @param goal the goal state
	 * @param start the start state
	 * @return the solution
	 */
	protected Solution<T> backTrace(State<T> goal, State<T> start) {
		Solution<T> sol = new Solution<T>();
		State<T> current = goal;
		while (current != null) {
			sol.addToBeginningOfPath(current);
			current = current.getCameFrom();
		}
		
		return sol;
	}
}