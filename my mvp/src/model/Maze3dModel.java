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
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.SearchableMaze;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.Solution;

/**
 * The Class Maze3dModel.
 */
public class Maze3dModel extends Observable implements Model {
	
	//private byte[] data;
	
	/** The mazes hash map. */
	private ConcurrentHashMap<String, Maze3d> mazes;
	
	/** The mazes-files hash map. */
	private HashMap<String, String> mazesFiles;
	
	/** The mazes-solutions hash map. */
	private ConcurrentHashMap<String, Solution<Position>> mazesSolutions;

	/**
	 * Instantiates a new maze3d model.
	 */
	public Maze3dModel() {
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.mazesFiles = new HashMap<String, String>();
		this.mazesSolutions = new ConcurrentHashMap<String, Solution<Position>>();
	}

//	@Override
//	public byte[] getData() {
//		return data;
//	}


	/**
	 * Displays all files and directories in the given path.
	 *
	 * @param path the path
	 * @see model.Model#dir(java.lang.String)
	 */
	@Override
	public void dir(String path) {
		String[] files = new File(path).list();
		if (files != null) {
			StringBuilder sb = new StringBuilder();
			for (String file : files) {
				sb.append(file + "\n");
			}
			setChanged();
			notifyObservers(sb.toString());
		}
		else {
			setChanged();
			notifyObservers("path \"" + path + "\" is invalid");
		}
	}
	

	/**
	 * Generates a 3d maze in a thread and adds it to mazes hash map under the given name.
	 * If a maze with the given name already exists, displays a message to the controller.
	 *
	 * @param mazeName the maze name
	 * @param x the number of rows
	 * @param y the number of floors
	 * @param z the number of columns
	 * @see model.Model#generate3dMaze(java.lang.String, int, int, int)
	 */
	@Override
	public void generate3dMaze(String mazeName, int x, int y, int z) {
		if (mazes.containsKey(mazeName)) {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" already exists, please choose a different name");
		}
		else {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);
						mazes.put(mazeName, maze);
						setChanged();
						notifyObservers("maze \"" + mazeName + "\" is ready");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}


	/**
	 * Displays the maze with the given name, if it exists.
	 *
	 * @param mazeName the maze name
	 * @see model.Model#displayMaze(java.lang.String)
	 */
	@Override
	public void displayMaze(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			setChanged();
			notifyObservers(mazes.get(mazeName).toString());
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Displays cross section by the given axis and index.
	 *
	 * @param axis the axis
	 * @param index the index
	 * @param mazeName the maze name
	 * @see model.Model#displayCrossSection(char, int, java.lang.String)
	 */
	@Override
	public void displayCrossSection(char axis, int index, String mazeName) {
		if (mazes.containsKey(mazeName)) {
			if (index > 0) {
				Maze3d maze = mazes.get(mazeName);
				
				switch (axis) {
				case 'X':
				case 'x':
					if (index <= maze.getX()) {
						setChanged();
						notifyObservers(maze.printCrossSection(maze.getCrossSectionByX(index-1)));
					}
					else {
						setChanged();
						notifyObservers("index should not be larger than " + maze.getX());
					}
					break;
				case 'Y':
				case 'y':
					if (index <= maze.getY()) {
						setChanged();
						notifyObservers(maze.printCrossSection(maze.getCrossSectionByY(index-1)));
					}
					else {
						setChanged();
						notifyObservers("index should not be larger than " + maze.getY());
					}
					break;
				case 'Z':
				case 'z':
					if (index <= maze.getZ()) {
						setChanged();
						notifyObservers(maze.printCrossSection(maze.getCrossSectionByZ(index-1)));
					}
					else {
						setChanged();
						notifyObservers("index should not be larger than " + maze.getZ());
					}
					break;
				default:
					setChanged();
					notifyObservers("axis invalid, please type X or Y or Z");
				}
			}
			else {
				setChanged();
				notifyObservers("index should not be smaller than 1");
			}
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Saves the maze to a file under the given file name and adds it to mazes-files hash map.
	 *
	 * @param mazeName the maze name
	 * @param fileName the file name
	 * @see model.Model#saveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveMaze(String mazeName, String fileName) {
		if (mazes.containsKey(mazeName)) {
			try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName + ".maz"));
				out.write(mazes.get(mazeName).toByteArray());
				out.flush();
				out.close();
				mazesFiles.put(mazeName, fileName);
				setChanged();
				notifyObservers("maze \"" + mazeName + "\" was saved successfully");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Loads the maze from the file fileName and adds it to mazes hash map under the given name.
	 *
	 * @param fileName the file name
	 * @param mazeName the maze name
	 * @see model.Model#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String fileName, String mazeName) {
		if (mazes.containsKey(mazeName)) {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" already exists, please choose a different name");
		}
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
				setChanged();
				notifyObservers("maze \"" + mazeName + "\" was loaded successfully");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		else {
			setChanged();
			notifyObservers("file name \"" + fileName + "\" does not exist");
		}
	}


	/**
	 * Displays the maze size in memory.
	 *
	 * @param mazeName the maze name
	 * @see model.Model#mazeSize(java.lang.String)
	 */
	@Override
	public void mazeSize(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			Maze3d maze = mazes.get(mazeName);
			int size = (maze.getX()*maze.getY()*maze.getZ()+6)*4;		
			setChanged();
			notifyObservers(size + " bytes");
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Displays the maze size in file.
	 * If a file for this maze name doesn't exist, creates a temporary file
	 *
	 * @param mazeName the maze name
	 * @see model.Model#fileSize(java.lang.String)
	 */
	@Override
	public void fileSize(String mazeName) { //check if should contain a file for this maze
		if (mazes.containsKey(mazeName)) {
			//if this maze is already saved in a file
			if (mazesFiles.containsKey(mazeName)) {
				File file = new File(mazesFiles.get(mazeName) + ".maz");
				setChanged();
				notifyObservers(file.length() + " bytes");
			}
			else {
				try {
					//create a test file to check its size
					OutputStream out = new MyCompressorOutputStream(new FileOutputStream("testfilename.maz"));
					out.write(mazes.get(mazeName).toByteArray());
					out.flush();
					out.close();
					File file = new File("testfilename.maz");
					setChanged();
					notifyObservers(file.length() + " bytes");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Solves the maze using the given algorithm.
	 *
	 * @param mazeName the maze name
	 * @param algorithm the algorithm
	 * @see model.Model#solveMaze(java.lang.String, java.lang.String)
	 */
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
						setChanged();
						notifyObservers("algorithm \"" + algorithm + "\" does not exist");
						return;					
					}
					mazesSolutions.put(mazeName, sol);
					setChanged();
					notifyObservers("solution for \"" + mazeName + "\" is ready");
				}
			}).start();			
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}


	/**
	 * Displays the solution for the maze.
	 *
	 * @param mazeName the maze name
	 * @see model.Model#displaySolution(java.lang.String)
	 */
	@Override
	public void displaySolution(String mazeName) {
		if (mazes.containsKey(mazeName)) {
			if (mazesSolutions.containsKey(mazeName)) {
				setChanged();
				notifyObservers(mazesSolutions.get(mazeName).toString());
			}
			else {
				setChanged();
				notifyObservers("a solution for maze \"" + mazeName + "\" does not exist");
			}
		}
		else {
			setChanged();
			notifyObservers("maze \"" + mazeName + "\" does not exist");
		}
	}
}
