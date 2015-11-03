package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class MazeBoard.
 */
public abstract class MazeBoard extends Canvas {
	
	/** The maze data. */
	int[][] mazeData;

	/**
	 * Instantiates a new maze board.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeBoard(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Gets the maze data.
	 *
	 * @return the maze data
	 */
	public int[][] getMazeData() {
		return mazeData;
	}

	/**
	 * Sets the maze data.
	 *
	 * @param mazeData the new maze data
	 */
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
	}

	/**
	 * Sets the character position.
	 *
	 * @param row the row
	 * @param column the column
	 */
	public abstract void setCharacterPosition(int row, int column);
	
	/**
	 * Sets the exit position.
	 *
	 * @param row the row
	 * @param column the column
	 */
	public abstract void setExitPosition(int row, int column);

	/**
	 * Move up.
	 */
	public abstract void moveUp();

	/**
	 * Move down.
	 */
	public abstract void moveDown();

	/**
	 * Move left.
	 */
	public abstract void moveLeft();
	
	/**
	 * Move right.
	 */
	public abstract void moveRight();

}
