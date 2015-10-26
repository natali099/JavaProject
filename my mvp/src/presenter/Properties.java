package presenter;

import java.io.Serializable;

import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Heuristic;
import algorithms.search.Searcher;

public class Properties implements Serializable {
	
	private Maze3dGenerator generator;
	private Searcher<Position> searcher;
	private Heuristic<Position> heuristic;	
	
	public Properties() {
		
	}

	public Maze3dGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(Maze3dGenerator generator) {
		this.generator = generator;
	}

	public Searcher<Position> getSearcher() {
		return searcher;
	}

	public void setSearcher(Searcher<Position> searcher) {
		this.searcher = searcher;
	}

	public Heuristic<Position> getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(Heuristic<Position> heuristic) {
		this.heuristic = heuristic;
	}
}
