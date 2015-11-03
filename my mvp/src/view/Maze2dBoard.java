package view;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * The Class Maze2dBoard.
 */
public class Maze2dBoard extends MazeBoard {
	
	/** The maze. */
	private Maze3d maze;
	
	/** The cross section. */
	private char crossSection;
	
	/** The current position. */
	private Position currentPosition;
	
	/** The end position. */
	private Position endPosition;
	
	/** The possible moves. */
	private String[] possibleMoves;
	
	/** The character x. */
	private int characterX;
	
	/** The character y. */
	private int characterY;
	
	/** The exit x. */
	private int exitX;
	
	/** The exit y. */
	private int exitY;
	
	/** The finished. */
	private boolean finished;
	
	/** The close message. */
	private boolean closeMessage;
	
	/** The timer. */
	private Timer timer;
	
	/** The task. */
	private TimerTask task;

	/**
	 * Instantiates a new maze2d board.
	 * Adds paint and key listeners.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public Maze2dBoard(Composite parent, int style) {
		super(parent, style);
		setBackground(new Color(null, 255, 255, 255));
		setFinished(false);
		setCloseMessage(false);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));

				int width=getSize().x;
				int height=getSize().y;

				if (mazeData != null) {
					int w=width/mazeData[0].length;
					int h=height/mazeData.length;

					for(int i=0;i<mazeData.length;i++) {
						for(int j=0;j<mazeData[i].length;j++) {
							int x=j*w;
							int y=i*h;
							if(mazeData[i][j]==1)
								e.gc.fillRectangle(x,y,w,h);
							if(i==characterX && j==characterY) {
								if (currentPosition.equals(endPosition))
								{
									Image img = new Image(getDisplay(), "resources/bugsbunny eating.png");
									e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, x, y, w, h);
									setFinished(true);
								}
								else {
									Image img = new Image(getDisplay(), "resources/Bugsrunning.png");
									e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, x, y, w, h);
								}
							}
							else if (i==exitX && j==exitY && ((crossSection=='x' && currentPosition.getX()==endPosition.getX()) || (crossSection=='y' && currentPosition.getY()==endPosition.getY()) || (crossSection=='z' && currentPosition.getZ()==endPosition.getZ()))) {
								Image img = new Image(getDisplay(), "resources/Bugs-Bunny-Carrot.png");
								e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, x, y, w, h);
							}
						}
					}
					if (finished) {
						if (!closeMessage) {
					        MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
					        messageBox.setMessage("Thanks a lot Doc!");
					        messageBox.setText("Congratulations!");
					        messageBox.open();
					        setCloseMessage(true);
						}
					}
				}
			}
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (!finished) {
					switch(e.keyCode) {
					case SWT.ARROW_UP:
						switch (crossSection) {
						case 'x'://down
							moveDown();
							break;
						case 'y'://left
							moveLeft();
							break;
						case 'z'://down
							moveDown();
							break;
						}
						break;
					case SWT.ARROW_DOWN:
						switch (crossSection) {
						case 'x'://up
							moveUp();
							break;
						case 'y'://right
							moveRight();
							break;
						case 'z'://up
							moveUp();
							break;
						}
				        break;
					case SWT.ARROW_LEFT:
						switch (crossSection) {
						case 'x'://back
							moveBack();
							break;
						case 'y'://back
							moveBack();
							break;
						case 'z'://left
							moveLeft();
							break;
						}
				        break;
					case SWT.ARROW_RIGHT:
						switch (crossSection) {
						case 'x'://forward
					        moveForward();
							break;
						case 'y'://forward
					        moveForward();
							break;
						case 'z'://right
							moveRight();
							break;
						}					
				        break;
					case SWT.PAGE_UP:
						switch (crossSection) {
						case 'x'://left
							moveLeft();
							break;
						case 'y'://up
							moveUp();
							break;
						case 'z'://forward
					        moveForward();
							break;
						}
				        break;
					case SWT.PAGE_DOWN:
						switch (crossSection) {
						case 'x'://right
							moveRight();
							break;
						case 'y'://down
							moveDown();
							break;
						case 'z'://back
							moveBack();
							break;
						}
				        break;
					}				
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
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
	 * Gets the cross section.
	 *
	 * @return the cross section
	 */
	public char getCrossSection() {
		return crossSection;
	}

	/**
	 * Sets the cross section.
	 *
	 * @param crossSection the new cross section
	 */
	public void setCrossSection(char crossSection) {
		this.crossSection = crossSection;
	}
	
	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 */
	public Position getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Sets the current position.
	 *
	 * @param currentPosition the new current position
	 */
	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Gets the end position.
	 *
	 * @return the end position
	 */
	public Position getEndPosition() {
		return endPosition;
	}

	/**
	 * Sets the end position.
	 *
	 * @param endPosition the new end position
	 */
	public void setEndPosition(Position endPosition) {
		this.endPosition = endPosition;
	}

	/**
	 * Gets the possible moves.
	 *
	 * @return the possible moves
	 */
	public String[] getPossibleMoves() {
		return possibleMoves;
	}
	

	/**
	 * Sets the possible moves.
	 *
	 * @param possibleMoves the new possible moves
	 */
	public void setPossibleMoves(String[] possibleMoves) {
		this.possibleMoves = possibleMoves;
	}
	

	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Sets the finished.
	 *
	 * @param finished the new finished
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
		if (task!=null && timer!=null) {
			task.cancel();
			timer.cancel();			
		}
	}

	/**
	 * Checks if is close message.
	 *
	 * @return true, if is close message
	 */
	public boolean isCloseMessage() {
		return closeMessage;
	}

	/**
	 * Sets the close message.
	 *
	 * @param closeMessage the new close message
	 */
	public void setCloseMessage(boolean closeMessage) {
		this.closeMessage = closeMessage;
	}

	/**
	 * Sets the character position.
	 *
	 * @param row the row
	 * @param column the column
	 * @see view.MazeBoard#setCharacterPosition(int, int)
	 */
	@Override
	public void setCharacterPosition(int row, int column) {
		characterX = row;
		characterY = column;
	}

	/**
	 * Sets the exit position.
	 *
	 * @param row the row
	 * @param column the column
	 * @see view.MazeBoard#setExitPosition(int, int)
	 */
	@Override
	public void setExitPosition(int row, int column) {
		exitX = row;
		exitY = column;		
	}

	/**
	 * Move character.
	 *
	 * @param x the row
	 * @param y the column
	 */
	private void moveCharacter(int x, int y){
		setCharacterPosition(x, y);
		redraw();
	}
	
	/**
	 * Move character to position.
	 *
	 * @param p the position
	 */
	private void moveCharacter(Position p){
		setCurrentPosition(p);
		switch (crossSection) {
		case 'x':
			setMazeData(maze.getCrossSectionByX(currentPosition.getX()));
			moveCharacter(currentPosition.getY(), currentPosition.getZ());
			break;
		case 'y':
			setMazeData(maze.getCrossSectionByY(currentPosition.getY()));
			moveCharacter(currentPosition.getX(), currentPosition.getZ());
			break;
		case 'z':
			setMazeData(maze.getCrossSectionByZ(currentPosition.getZ()));
			moveCharacter(currentPosition.getY(), currentPosition.getX());
			break;
		}
	}
	
	/**
	 * Move up.
	 * @see view.MazeBoard#moveUp()
	 */
	@Override
	public void moveUp() {
		for (String s : possibleMoves) {
			if (s.equals("up")) {
				currentPosition.setY(currentPosition.getY()+1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://down
					moveCharacter(characterX+1, characterY);
					break;
				case 'y'://same
					setMazeData(maze.getCrossSectionByY(currentPosition.getY()));
					redraw();
					break;
				case 'z'://down
					moveCharacter(characterX+1, characterY);
					break;
				}
				break;
			}
		}
	}

	/**
	 * Move down.
	 * @see view.MazeBoard#moveDown()
	 */
	@Override
	public void moveDown() {
		for (String s : possibleMoves) {
			if (s.equals("down")) {
				currentPosition.setY(currentPosition.getY()-1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://up
					moveCharacter(characterX-1, characterY);
					break;
				case 'y'://same
					setMazeData(maze.getCrossSectionByY(currentPosition.getY()));
					redraw();
					break;
				case 'z'://up
					moveCharacter(characterX-1, characterY);
					break;
				}
				break;
			}
		}
	}

	/**
	 * Move left.
	 * @see view.MazeBoard#moveLeft()
	 */
	@Override
	public void moveLeft() {
		for (String s : possibleMoves) {
			if (s.equals("left")) {
				currentPosition.setX(currentPosition.getX()-1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://same
					setMazeData(maze.getCrossSectionByX(currentPosition.getX()));
					redraw();
					break;
				case 'y'://up
					moveCharacter(characterX-1, characterY);
					break;
				case 'z'://left
					moveCharacter(characterX, characterY-1);
					break;
				}
				break;
			}
		}
	}

	/**
	 * Move right.
	 * @see view.MazeBoard#moveRight()
	 */
	@Override
	public void moveRight() {
		for (String s : possibleMoves) {
			if (s.equals("right")) {
				currentPosition.setX(currentPosition.getX()+1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://same
					setMazeData(maze.getCrossSectionByX(currentPosition.getX()));
					redraw();
					break;
				case 'y'://down
					moveCharacter(characterX+1, characterY);
					break;
				case 'z'://right
					moveCharacter(characterX, characterY+1);
					break;
				}
				break;
			}
		}
	}

	/**
	 * Move forward.
	 */
	public void moveForward() {
		for (String s : possibleMoves) {
			if (s.equals("forward")) {
				currentPosition.setZ(currentPosition.getZ()+1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://right
					moveCharacter(characterX, characterY+1);
					break;
				case 'y'://right
					moveCharacter(characterX, characterY+1);
					break;
				case 'z'://same
					setMazeData(maze.getCrossSectionByZ(currentPosition.getZ()));
					redraw();
					break;
				}
				break;
			}
		}		
	}

	/**
	 * Move back.
	 */
	public void moveBack() {
		for (String s : possibleMoves) {
			if (s.equals("back")) {
				currentPosition.setZ(currentPosition.getZ()-1);
				setPossibleMoves(maze.getPossibleMoves(currentPosition));
				switch (crossSection) {
				case 'x'://left
					moveCharacter(characterX, characterY-1);
					break;
				case 'y'://left
					moveCharacter(characterX, characterY-1);
					break;
				case 'z'://same
					setMazeData(maze.getCrossSectionByZ(currentPosition.getZ()));
					redraw();
					break;
				}
				break;
			}
		}		
	}
	
	/**
	 * Solve and display the animated solution.
	 *
	 * @param sol the solution
	 */
	public void solve(Solution<Position> sol) {
		setFinished(false);
		final LinkedList<State<Position>> path = new LinkedList<State<Position>>(sol.getPath());
		timer=new Timer();
		task=new TimerTask() {
			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						Position p = path.poll().getState();
						if (p!=null)
							moveCharacter(p);
						else
							setFinished(true);
					}
				});
			}
		};				
		timer.scheduleAtFixedRate(task, 0, 1000);		
	}
	
}
