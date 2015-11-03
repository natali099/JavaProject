package presenter;

import view.View;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Heuristic;
import algorithms.search.Searcher;

/**
 * The Class Properties.
 */
public class Properties {
	
	/** The generator. */
	private Maze3dGenerator generator;
	
	/** The searcher. */
	private Searcher<Position> searcher;
	
	/** The heuristic. */
	private Heuristic<Position> heuristic;
	
	/** The view. */
	private View view;
	
	/**
	 * Instantiates a new properties.
	 */
	public Properties() {
		
	}

	/**
	 * Gets the generator.
	 *
	 * @return the generator
	 */
	public Maze3dGenerator getGenerator() {
		return generator;
	}

	/**
	 * Sets the generator.
	 *
	 * @param generator the new generator
	 */
	public void setGenerator(Maze3dGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Gets the searcher.
	 *
	 * @return the searcher
	 */
	public Searcher<Position> getSearcher() {
		return searcher;
	}

	/**
	 * Sets the searcher.
	 *
	 * @param searcher the new searcher
	 */
	public void setSearcher(Searcher<Position> searcher) {
		this.searcher = searcher;
	}

	/**
	 * Gets the heuristic.
	 *
	 * @return the heuristic
	 */
	public Heuristic<Position> getHeuristic() {
		return heuristic;
	}

	/**
	 * Sets the heuristic.
	 *
	 * @param heuristic the new heuristic
	 */
	public void setHeuristic(Heuristic<Position> heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(View view) {
		this.view = view;
	}
}
