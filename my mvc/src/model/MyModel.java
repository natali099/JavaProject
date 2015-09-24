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
import java.util.concurrent.ConcurrentHashMap;

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
	private Controller c;
	private ConcurrentHashMap<String, Maze3d> mazes;
	private ConcurrentHashMap<String, String> mazesFiles;
	private ConcurrentHashMap<String, Solution<Position>> mazesSolutions;
	
	public MyModel (Controller c) {
		this.c = c;
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.mazesFiles = new ConcurrentHashMap<String, String>();
		this.mazesSolutions = new ConcurrentHashMap<String, Solution<Position>>();
	}

	@Override
	public void dir(String path) {
		String[] files = new File(path).list();
		if (files != null) {
			StringBuilder sb = new StringBuilder();
			for (String file : files) {
				sb.append(file + "\n");
			}
			c.display(sb.toString());
		}
		else
			c.display("path \"" + path + "\" is invalid");
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
				
				switch (axis) {
				case 'X':
				case 'x':
					if (index <= maze.getX())
						c.display(maze.printCrossSection(maze.getCrossSectionByX(index-1)));
					else
						c.display("index should not be larger than " + maze.getX());
					break;
				case 'Y':
				case 'y':
					if (index <= maze.getY())
						c.display(maze.printCrossSection(maze.getCrossSectionByY(index-1)));
					else
						c.display("index should not be larger than " + maze.getY());
					break;
				case 'Z':
				case 'z':
					if (index <= maze.getZ())
						c.display(maze.printCrossSection(maze.getCrossSectionByZ(index-1)));
					else
						c.display("index should not be larger than " + maze.getZ());
					break;
				default:
					c.display("axis invalid, please type X or Y or Z");
				}
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
				mazesFiles.put(mazeName, fileName);
				c.display("maze \"" + mazeName + "\" was saved successfully");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
		else if (mazesFiles.containsValue(fileName)) {
			try {
				InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName + ".maz"));
				//read the size of the maze and create an array with appropriate size
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
				e.printStackTrace();
			} catch (IOException e) {
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
	public void fileSize(String mazeName) { //check if should contain a file for this maze
		if (mazes.containsKey(mazeName)) {
			//if this maze is already saved in a file
			if (mazesFiles.containsKey(mazeName)) {
				File file = new File(mazesFiles.get(mazeName) + ".maz");
				c.display(file.length() + " bytes");
			}
			else {
				try {
					//create a test file to check its size
					OutputStream out = new MyCompressorOutputStream(new FileOutputStream("testfilename.maz"));
					out.write(mazes.get(mazeName).toByteArray());
					out.flush();
					out.close();
					File file = new File("testfilename.maz");
					c.display(file.length() + " bytes");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
					
					switch (algorithm) {
					case "bfs":
					case "BFS":
						BFS<Position> bfs = new BFS<Position>();
						sol = bfs.search(new SearchableMaze(maze));
						break;
					case "a*":
					case "A*":
						AStar<Position> astar = new AStar<Position>(new MazeAirDistance());
						sol = astar.search(new SearchableMaze(maze));
						break;
					default:
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
