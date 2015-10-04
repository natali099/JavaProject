package algorithms.demo;

import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;

public class Demo {

	public void run(int x, int y, int z) {
		try {
			SearchableMaze maze = new SearchableMaze(new MyMaze3dGenerator().generate(x, y, z));
			maze.getMaze().print();
			
			BFS<Position> bfs = new BFS<Position>();
			Solution<Position> bfsSolution = bfs.search(maze);
			System.out.println("BFS solution:");
			System.out.println(bfsSolution);
			
			AStar<Position> aStarManhattan = new AStar<Position>(new MazeManhattanDistance());
			Solution<Position> astarManhattanSolution = aStarManhattan.search(maze);
			System.out.println("AStar Manhattan solution:");
			System.out.println(astarManhattanSolution);
			
			AStar<Position> aStarAir = new AStar<Position>(new MazeAirDistance());
			Solution<Position> astarAirSolution = aStarAir.search(maze);
			System.out.println("AStar Air solution:");
			System.out.println(astarAirSolution);
			
			System.out.println("nodes evaluated by BFS: " + bfs.getNumberOfNodesEvaluated());
			System.out.println("nodes evaluated by AStar Manhattan: " + aStarManhattan.getNumberOfNodesEvaluated());
			System.out.println("nodes evaluated by AStar Air: " + aStarAir.getNumberOfNodesEvaluated());
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
}
