package view;

import java.util.HashMap;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class Maze3dGUIView.
 */
public class Maze3dGUIView extends BasicWindow implements View {
	
	/** The maze board. */
	private Maze2dBoard mazeBoard;
	
	/** The message. */
	private Text message;
	
	/** The message box. */
	private MessageBox messageBox;
	
	/** The maze name. */
	private String mazeName;
	
	/** The commands. */
	private HashMap<String, Command> commands;

	/**
	 * Instantiates a new maze3d gui view.
	 */
	public Maze3dGUIView() {
	}
	
	/**
	 * Instantiates a new maze3d gui view.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public Maze3dGUIView(String title, int width, int height) {
		super(title, width, height);
		messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK | SWT.ERROR);
	}
	
	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;		
	}

	/**
	 * Starts the view.
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		run();

	}

	/**
	 * Displays data.
	 *
	 * @param data the data to be displayed, represented by a byte array
	 * @see view.View#display(byte[])
	 */
	@Override
	public void display(byte[] data) {
		message.setText(new String(data));
	}
	
	/**
	 * Display error.
	 *
	 * @param error the error
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String error) {		
		messageBox.setMessage(error);
		messageBox.setText("Error");
		messageBox.open();
	}

	/**
	 * Display maze.
	 *
	 * @param maze the maze
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		Position currentPosition = maze.getStartPosition();
		Position endPosition = maze.getGoalPosition();
		mazeBoard.setMaze(maze);
		mazeBoard.setCurrentPosition(currentPosition);
		mazeBoard.setEndPosition(endPosition);
		mazeBoard.setPossibleMoves(maze.getPossibleMoves(currentPosition));
		mazeBoard.setCrossSection('y');
		mazeBoard.setCharacterPosition(currentPosition.getX(), currentPosition.getZ());
		mazeBoard.setExitPosition(endPosition.getX(), endPosition.getZ());
		displayCrossSection(maze.getCrossSectionByY(currentPosition.getY()));		
	}

	/**
	 * Display solution.
	 *
	 * @param solution the solution
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		mazeBoard.solve(solution);
		
	}

	/**
	 * Display cross section.
	 *
	 * @param crossSection the cross section
	 * @see view.View#displayCrossSection(int[][])
	 */
	@Override
	public void displayCrossSection(int[][] crossSection) {
		mazeBoard.setMazeData(crossSection);
		mazeBoard.redraw();		
	}
	
	/**
	 * Notifies the observers with the arguments received.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the observers
	 * @see view.View#doCommand(presenter.Command, java.lang.String[])
	 */
	@Override
	public void doCommand(Command command, String[] args) {
		Object[] arg = new Object[2];
		arg[0] = command;
		arg[1] = args;
		setChanged();
		notifyObservers(arg);
	}

	/**
	 * Initializes the widgets.
	 * Adds listeners to widgets.
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(5,false));
		
	    Menu menuBar = new Menu(shell, SWT.BAR);
	    Menu fileMenu = new Menu(menuBar);
	    MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
	    fileItem.setText("File");
	    fileItem.setMenu(fileMenu);

	    MenuItem propertiesItem = new MenuItem(fileMenu, SWT.NONE);
	    propertiesItem.setText("Open properties");
	    MenuItem loadItem = new MenuItem(fileMenu, SWT.NONE);
	    loadItem.setText("Load maze");
	    MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
	    saveItem.setText("Save maze as...");
	    saveItem.setEnabled(false);
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
	    exitItem.setText("Exit");

	    shell.setMenuBar(menuBar);
			    
		Button generateButton=new Button(shell, SWT.PUSH);
		generateButton.setText("Generate");
		generateButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		generateButton.setSelection(false);
		
		Button crossX=new Button(shell, SWT.RADIO);
		crossX.setText("X");
		crossX.setEnabled(false);
		
		Button crossY=new Button(shell, SWT.RADIO);
		crossY.setText("Y");
		crossY.setEnabled(false);
		
		Button crossZ=new Button(shell, SWT.RADIO);
		crossZ.setText("Z");
		crossZ.setEnabled(false);
		
		message=new Text(shell, SWT.READ_ONLY);
		message.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		
		Button solveButton=new Button(shell, SWT.PUSH);
		solveButton.setText("Solve");
		solveButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		solveButton.setEnabled(false);
				
		mazeBoard=new Maze2dBoard(shell, SWT.BORDER);
		mazeBoard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 3));
		
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				doCommand(commands.get("exit"), null);				
			}
		});
		
		propertiesItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open properties");
				fd.setFilterPath("");
				fd.setFilterExtensions(new String[]{"*.xml", "*.*"});
				String filePath = fd.open();
				if (filePath != null) {
					doCommand(commands.get("setProperties"), new String[]{filePath});
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		loadItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Load maze");
				fd.setFilterPath("");
				fd.setFilterExtensions(new String[]{"*.maz", "*.*"});
				String filePath = fd.open();
				if (filePath != null) {
					MazeNameInputDialog dlg = new MazeNameInputDialog(shell);
					String[] input = dlg.open();
				    if (input != null) {
				    	if (input[0] != "" && input[0] != null) {
				    		mazeName = input[0];
				    		doCommand(commands.get("loadMaze"), new String[]{filePath, mazeName});
				    	}			    	
				    	else
				    		displayError("please give a name to the maze");
				    }
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		saveItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setText("Save maze");
				fd.setFilterPath("");
				String filePath = fd.open();
				doCommand(commands.get("saveMaze"), new String[]{mazeName, filePath});
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		exitItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
				
		generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeInputDialog dlg = new GenerateMazeInputDialog(shell);
			    String[] input = dlg.open();
			    Boolean b = false;
			    if (input != null) {
			    	for (String s : input) {
			    		if (s == "" || s == null) {
			    			b = true;
			    			break;
			    		}
			    	}
			    	if (!b) {
			    		doCommand(commands.get("generate3dMaze"), input);
			    		if (Integer.parseInt(input[1]) >= 3 && Integer.parseInt(input[2]) >= 3 && Integer.parseInt(input[3]) >= 3) {
				    		mazeName = input[0];
				    		crossX.setEnabled(true);
				    		crossZ.setEnabled(true);
				    		mazeBoard.setFinished(false);
				    		doCommand(commands.get("display"), new String[]{mazeName});				    		
				    		saveItem.setEnabled(true);
				    		solveButton.setEnabled(true);
				    		mazeBoard.setCloseMessage(false);
			    		}

			    	}			    	
			    	else
			    		displayError("please fill all fields");
			    }				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		crossX.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				crossX.setEnabled(false);
				crossY.setEnabled(true);
				crossZ.setEnabled(true);
				mazeBoard.setCrossSection('x');
				mazeBoard.setCharacterPosition(mazeBoard.getCurrentPosition().getY(), mazeBoard.getCurrentPosition().getZ());
				mazeBoard.setExitPosition(mazeBoard.getEndPosition().getY(), mazeBoard.getEndPosition().getZ());
				doCommand(commands.get("displayCrossSectionBy"), new String[]{"x", String.valueOf(mazeBoard.getCurrentPosition().getX()+1), mazeName});
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		crossY.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				crossY.setEnabled(false);
				crossX.setEnabled(true);
				crossZ.setEnabled(true);
				mazeBoard.setCrossSection('y');
				mazeBoard.setCharacterPosition(mazeBoard.getCurrentPosition().getX(), mazeBoard.getCurrentPosition().getZ());
				mazeBoard.setExitPosition(mazeBoard.getEndPosition().getX(), mazeBoard.getEndPosition().getZ());
				doCommand(commands.get("displayCrossSectionBy"), new String[]{"y", String.valueOf(mazeBoard.getCurrentPosition().getY()+1), mazeName});
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		crossZ.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				crossZ.setEnabled(false);
				crossX.setEnabled(true);
				crossY.setEnabled(true);
				mazeBoard.setCrossSection('z');
				mazeBoard.setCharacterPosition(mazeBoard.getCurrentPosition().getY(), mazeBoard.getCurrentPosition().getX());
				mazeBoard.setExitPosition(mazeBoard.getEndPosition().getY(), mazeBoard.getEndPosition().getX());
				doCommand(commands.get("displayCrossSectionBy"), new String[]{"z", String.valueOf(mazeBoard.getCurrentPosition().getZ()+1), mazeName});
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		solveButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				doCommand(commands.get("solve"), new String[]{mazeName, "a*"});
				doCommand(commands.get("displaySolution"), new String[]{mazeName});
				mazeBoard.setCloseMessage(false);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	}

	/**
	 * Adds the observer.
	 *
	 * @param o the observer
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		
	}
}
