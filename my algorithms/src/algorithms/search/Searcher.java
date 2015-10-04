package algorithms.search;

/**
 * The Interface Searcher.
 *
 * @param <T> the generic type
 */
public interface Searcher<T> {
    
    /**
     * Searches a Searchable<T>.
     *
     * @param s the searchable to be searched
     * @return the solution (or null if no solution was found)
     */
    // the search method
    public Solution<T> search(Searchable<T> s);
    
    /**
     * Gets the number of nodes evaluated.
     *
     * @return the number of nodes evaluated
     */
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();
}

