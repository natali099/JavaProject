package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import algorithms.demo.SearchableMaze;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.Solution;
import controller.Controller;

public class MyModel implements Model {
	Controller c;
	private HashMap<String, Maze3d> mazes;
	private HashMap<String, String> mazesFiles;
	private HashMap<String, Solution<Position>> mazesSolutions;
	
	public MyModel (Controller c) {
		this.c = c;
		this.mazes = new HashMap<String, Maze3d>();
		this.mazesFiles = new HashMap<String, String>();
		this.mazesSolutions = new HashMap<String, Solution<Position>>();
	}

	@Override
	public void dir(String path) {
		String[] files = new File(path).list();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<files.length; i++) {
			sb.append(files[i]);
			sb.append("\n");
		}
		c.display(sb.toString());
	}
	
	@Override
	public void generate3dMaze(String mazeName, int x, int y, int z) {
		if (mazes.containsKey(mazeName))
			c.display("maze \"" + mazeName + "\" already exists, please choose a different name");
		else {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);
						mazes.put(mazeName, maze);
						c.display("maze \"" + mazeName + "\" is ready");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	@Override
	public void displayMaze(String mazeName) {
		if (mazes.containsKey(mazeName))
			c.display(mazes.get(mazeName).toString());
		else
			c.display("maze \"" + mazeName + "\" does not exist");
		
	}

	@Override
	public void displayCrossSection(char axis, int index, String mazeName) {
		if (mazes.containsKey(mazeName)) {
			if (index > 0) {
				Maze3d maze = mazes.get(mazeName);
				if (axis == 'X' || axis == 'x') {
					if (index <= maze.getX())
						c.display(maze.printCrossSection(maze.getCrossSectionByX(index-1)));
					else
						c.display("index should not be larger than " + maze.getX());
				}
				else if (axis == 'Y' || axis == 'y') {
					if (index <= maze.getY())
						c.display(maze.printCrossSection(maze.getCrossSectionByY(index-1)));
					else
						c.display("index should not be larger than " + maze.getY());
				}
				else if (axis == 'Z' || axis == 'z') {
					if (index <= maze.getZ())
						c.display(maze.printCrossSection(maze.getCrossSectionByZ(index-1)));
					else
						c.display("index should not be larger than " + maze.getZ());
				}
				else
					c.display("axis invalid, please type X or Y or Z");
			}
			else
				c.display("index should not be smaller than 1");
		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");
	}

	@Override
	public void saveMaze(String mazeName, String fileName) {
		if (mazes.containsKey(mazeName)) {
			try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName + ".maz"));
				out.write(mazes.get(mazeName).toByteArray());
				out.flush();
				out.close();
				mazesFiles.put(fileName, mazeName);
				c.display("maze \"" + mazeName + "\" was saved successfully");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");
		
	}

	@Override
	public void loadMaze(String fileName, String mazeName) {
		if (mazes.containsKey(mazeName))
			c.display("maze \"" + mazeName + "\" already exists, please choose a different name");
		else if (mazesFiles.containsKey(fileName)) {
			try {
				InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName + ".maz"));
				byte[] mazeSizes = new byte[12];
				in.read(mazeSizes);
				in.close();
				ByteBuffer bb = ByteBuffer.wrap(mazeSizes);
				byte[] b = new byte[bb.getInt()*bb.getInt()*bb.getInt() + 36];
				
				in = new MyDecompressorInputStream(new FileInputStream(fileName + ".maz"));
				in.read(b);
				in.close();
				Maze3d maze = new Maze3d(b);
				mazes.put(mazeName, maze);
				c.display("maze \"" + mazeName + "\" was loaded successfully");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
			c.display("file name \"" + fileName + "\" does not exist");
		
	}

	@Override
	public void mazeSize(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			Maze3d maze = mazes.get(mazeName);
			int size = (maze.getX()*maze.getY()*maze.getZ()+6)*4;
			c.display(size + " bytes");			
		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");
		
	}

	@Override
	public void fileSize(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream("test.maz"));
				out.write(mazes.get(mazeName).toByteArray());
				out.flush();
				out.close();
				File file = new File("test.maz");
				c.display(file.length() + " bytes");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");
		
	}

	@Override
	public void solveMaze(String mazeName, String algorithm) {
		if (mazes.containsKey(mazeName)) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Maze3d maze = mazes.get(mazeName);
					Solution<Position> sol;
					
					if (algorithm.equals("bfs") || algorithm.equals("BFS")) {
						BFS<Position> bfs = new BFS<Position>();
						sol = bfs.search(new SearchableMaze(maze));
					}
					else if (algorithm.equals("a*") || algorithm.equals("A*")) {
						AStar<Position> astar = new AStar<>(new MazeAirDistance());
						sol = astar.search(new SearchableMaze(maze));
					}
					else {
						c.display("algorithm \"" + algorithm + "\" does not exist");
						return;
					}
					mazesSolutions.put(mazeName, sol);
					c.display("solution for \"" + mazeName + "\" is ready");
				}
			}).start();			
		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");
	}

	@Override
	public void displaySolution(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			if (mazesSolutions.containsKey(mazeName))
				c.display(mazesSolutions.get(mazeName).toString());
			else
				c.display("a solution for maze \"" + mazeName + "\" does not exist");
		}
		else
			c.display("maze \"" + mazeName + "\" does not exist");		
	}

}
