package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import presenter.Properties;
import algorithms.demo.SearchableMaze;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.Solution;

/**
 * The Class Maze3dModel.
 */
public class Maze3dModel extends Observable implements Model {
	
	/** The data to be sent. */
	private byte[] data;
	
	/** The error message. */
	private String errorMessage;
	
	/** The maze. */
	private Maze3d maze;
	
	/** The solution. */
	private Solution<Position> solution;
	
	/** The cross section. */
	private int[][] crossSection;
	
	/** The lock on the data. */
	private ReentrantLock dataLock;
	
	/** Whether the data lock is locked. */
	private boolean dataLocked;
	
	/** The lock on the error message. */
	private ReentrantLock errorLock;
	
	/** Whether the error lock is locked. */
	private boolean errorLocked;
	
	/** The mazes hash map. */
	private ConcurrentHashMap<String, Maze3d> mazes;
	
	/** The mazes-files hash map. */
	private HashMap<String, String> mazesFiles;
	
	/** The mazes-solutions hash map. */
	private ConcurrentHashMap<Maze3d, Solution<Position>> mazesSolutions;
	
	/** The thread pool. */
	private ExecutorService threadpool;
	
	/** The properties. */
	private Properties properties;

	/**
	 * Instantiates a new maze3d model.
	 *
	 * @param properties the properties
	 */
	public Maze3dModel(Properties properties) {
		this.properties = properties;
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.mazesFiles = new HashMap<String, String>();
		this.threadpool = Executors.newCachedThreadPool();
		this.dataLock = new ReentrantLock();
		this.dataLocked = false;
		this.errorLock = new ReentrantLock();
		this.errorLocked = false;
		try {
			ObjectInputStream map = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.zip")));
			this.mazesSolutions = (ConcurrentHashMap<Maze3d, Solution<Position>>) map.readObject();
			map.close();
		} catch (IOException e) {
			this.mazesSolutions = new ConcurrentHashMap<Maze3d, Solution<Position>>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties the new properties
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * Sets the properties from an xml file.
	 *
	 * @param properties the file path
	 */
	public void setProperties(String properties) {
		try {
			XMLDecoder xml = new XMLDecoder(new FileInputStream(properties));
			this.properties = (Properties)xml.readObject();
			xml.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 * @see model.Model#getData()
	 */
	@Override
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 * @see model.Model#getErrorMessage()
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 * @see model.Model#getMaze()
	 */
	@Override
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 * @see model.Model#getSolution()
	 */
	@Override
	public Solution<Position> getSolution() {
		return solution;
	}

	/**
	 * Sets the solution.
	 *
	 * @param solution the new solution
	 */
	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
	}

	/**
	 * Gets the cross section.
	 *
	 * @return the cross section
	 * @see model.Model#getCrossSection()
	 */
	@Override
	public int[][] getCrossSection() {
		return crossSection;
	}

	/**
	 * Sets the cross section.
	 *
	 * @param crossSection the new cross section
	 */
	public void setCrossSection(int[][] crossSection) {
		this.crossSection = crossSection;
	}

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
			do {
				if (dataLock.tryLock()) {
					dataLocked = true;
					setData(sb.toString().getBytes());
					setChanged();
					notifyObservers();
					dataLocked = false;
				}
			} while (dataLocked);
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("path \"" + path + "\" is invalid");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" already exists, please choose a different name");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
		}
		else {
			try {
				Maze3d maze = threadpool.submit(new Callable<Maze3d>() {

					@Override
					public Maze3d call() throws Exception {
						Maze3d maze = properties.getGenerator().generate(x, y, z);
						return maze;													
					}					
				}).get();
				if (maze != null) {
					mazes.put(mazeName, maze);
					do {
						if (dataLock.tryLock()) {
							dataLocked = true;
							setData(("maze \"" + mazeName + "\" is ready").getBytes());
							setChanged();
							notifyObservers();
							dataLocked = false;
						}
					} while (dataLocked);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				do {
					if (errorLock.tryLock()) {
						errorLocked = true;
						setErrorMessage(e.getMessage());
						setChanged();
						notifyObservers("error");
						errorLocked = false;
					}
				} while (errorLocked);
			}
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
			setMaze(mazes.get(mazeName));
			setChanged();
			notifyObservers("maze");
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
						setCrossSection(maze.getCrossSectionByX(index-1));
						setChanged();
						notifyObservers("cross");
					}
					else {
						do {
							if (errorLock.tryLock()) {
								errorLocked = true;
								setErrorMessage("index should not be larger than " + maze.getX());
								setChanged();
								notifyObservers("error");
								errorLocked = false;
							}
						} while (errorLocked);
					}
					break;
				case 'Y':
				case 'y':
					if (index <= maze.getY()) {
						setCrossSection(maze.getCrossSectionByY(index-1));
						setChanged();
						notifyObservers("cross");
					}
					else {
						do {
							if (errorLock.tryLock()) {
								errorLocked = true;
								setErrorMessage("index should not be larger than " + maze.getY());
								setChanged();
								notifyObservers("error");
								errorLocked = false;
							}
						} while (errorLocked);
					}
					break;
				case 'Z':
				case 'z':
					if (index <= maze.getZ()) {
						setCrossSection(maze.getCrossSectionByZ(index-1));
						setChanged();
						notifyObservers("cross");
					}
					else {
						do {
							if (errorLock.tryLock()) {
								errorLocked = true;
								setErrorMessage("index should not be larger than " + maze.getZ());
								setChanged();
								notifyObservers("error");
								errorLocked = false;
							}
						} while (errorLocked);
					}
					break;
				default:
					do {
						if (errorLock.tryLock()) {
							errorLocked = true;
							setErrorMessage("axis invalid, please type X or Y or Z");
							setChanged();
							notifyObservers("error");
							errorLocked = false;
						}
					} while (errorLocked);
				}
			}
			else {
				do {
					if (errorLock.tryLock()) {
						errorLocked = true;
						setErrorMessage("index should not be smaller than 1");
						setChanged();
						notifyObservers("error");
						errorLocked = false;
					}
				} while (errorLocked);
			}
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
				do {
					if (dataLock.tryLock()) {
						dataLocked = true;
						setData(("maze \"" + mazeName + "\" was saved successfully").getBytes());
						setChanged();
						notifyObservers();
						dataLocked = false;
					}
				} while (dataLocked);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" already exists, please choose a different name");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
				do {
					if (dataLock.tryLock()) {
						dataLocked = true;
						setData(("maze \"" + mazeName + "\" was loaded successfully").getBytes());
						setChanged();
						notifyObservers();
						dataLocked = false;
					}
				} while (dataLocked);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("file name \"" + fileName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
			do {
				if (dataLock.tryLock()) {
					dataLocked = true;
					setData((size + " bytes").getBytes());
					setChanged();
					notifyObservers();
					dataLocked = false;
				}
			} while (dataLocked);
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
				do {
					if (dataLock.tryLock()) {
						dataLocked = true;
						setData((file.length() + " bytes").getBytes());
						setChanged();
						notifyObservers();
						dataLocked = false;
					}
				} while (dataLocked);
			}
			else {
				try {
					//create a test file to check its size
					OutputStream out = new MyCompressorOutputStream(new FileOutputStream("testfilename.maz"));
					out.write(mazes.get(mazeName).toByteArray());
					out.flush();
					out.close();
					File file = new File("testfilename.maz");
					do {
						if (dataLock.tryLock()) {
							dataLocked = true;
							setData((file.length() + " bytes").getBytes());
							setChanged();
							notifyObservers();
							dataLocked = false;
						}
					} while (dataLocked);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
		}
	}


	/**
	 * Solves the maze using the given algorithm.
	 * If a solution already exists, takes it from the hash map
	 *
	 * @param mazeName the maze name
	 * @param algorithm the algorithm
	 * @see model.Model#solveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void solveMaze(String mazeName, String algorithm) {
		if (mazes.containsKey(mazeName)) {
			Maze3d maze = mazes.get(mazeName);
			if (mazesSolutions.containsKey(maze)) {
				do {
					if (dataLock.tryLock()) {
						dataLocked = true;
						setData(("solution for \"" + mazeName + "\" is ready").getBytes());
						setChanged();
						notifyObservers();
						dataLocked = false;
					}
				} while (dataLocked);
			}
			else {
				try {
					Solution<Position> sol = threadpool.submit(new Callable<Solution<Position>>() {

						@Override
						public Solution<Position> call() throws Exception {
							switch (algorithm) {
							case "bfs":
							case "BFS":
								BFS<Position> bfs = new BFS<Position>();
								return bfs.search(new SearchableMaze(maze));
							case "a*":
							case "A*":
								AStar<Position> astar = new AStar<Position>(properties.getHeuristic());
								return astar.search(new SearchableMaze(maze));
							default:
								do {
									if (errorLock.tryLock()) {
										errorLocked = true;
										setErrorMessage("algorithm \"" + mazeName + "\" does not exist");
										setChanged();
										notifyObservers("error");
										errorLocked = false;
									}
								} while (errorLocked);
								return null;					
							}
						}
					}).get();
					if (sol != null) {
						mazesSolutions.put(maze, sol);
						do {
							if (dataLock.tryLock()) {
								dataLocked = true;
								data = ("solution for \"" + mazeName + "\" is ready").getBytes();
								setChanged();
								notifyObservers();
								dataLocked = false;
							}
						} while (dataLocked);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}						
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
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
			Maze3d maze = mazes.get(mazeName);
			if (mazesSolutions.containsKey(maze)) {
				setSolution(mazesSolutions.get(maze));
				setChanged();
				notifyObservers("solution");
			}
			else {
				do {
					if (errorLock.tryLock()) {
						errorLocked = true;
						setErrorMessage("a solution for maze \"" + mazeName + "\" does not exist");
						setChanged();
						notifyObservers("error");
						errorLocked = false;
					}
				} while (errorLocked);
			}
		}
		else {
			do {
				if (errorLock.tryLock()) {
					errorLocked = true;
					setErrorMessage("maze \"" + mazeName + "\" does not exist");
					setChanged();
					notifyObservers("error");
					errorLocked = false;
				}
			} while (errorLocked);
		}
	}

	/**
	 * Exits carefully, waiting for tasks in the thread pool to finish running and saves the solutions hash map to a file.
	 * 
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() {
		threadpool.shutdown();
		try {
			while(!(threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				ObjectOutputStream map = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.zip")));
				map.writeObject(mazesSolutions);
				map.flush();
				map.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
