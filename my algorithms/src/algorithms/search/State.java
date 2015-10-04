package algorithms.search;

/**
 * The Class State.
 *
 * @param <T> the generic type
 */
public class State<T> implements Comparable<State<T>> {
    
    /** The state. */
    private T state;    // the state represented by a string
    
    /** The cost from the start state. */
    private double cost;     // cost to reach this state
    
    /** The came from state. */
    private State<T> cameFrom;  // the state we came from to this state

    /**
     * Instantiates a new state.
     *
     * @param state the state
     */
    public State(T state){   
        this.setState(state);
    }

    /**
     * Checks if two states are the same by comparing the Ts.
     *
     * @param obj the state to be compared
     * @return true, if obj and state are the same, else returns false.
     */
    public boolean equals(State<T> obj) {
    	return state.equals(obj.state);
    }

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public T getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(T state) {
		this.state = state;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Gets the came from state.
	 *
	 * @return the came from state
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}

	/**
	 * Sets the came from state.
	 *
	 * @param cameFrom the new came from state
	 */
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	/**
	 * Compares two state by their costs.
	 *
	 * @param o the state to be compared to
	 * @return the difference between this state's cost and o's cost
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(State<T> o) {
		return (int)(this.cost-o.getCost());
	}
    
    /**
     * Uses T's hash code.
     *
     * @return the hash code
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return state.hashCode();
    }
    
    /**
     * Uses T's conversion to string.
     *
     * @return T's string representation
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	return state.toString();
    }
}
