package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class BasicWindow.
 */
public abstract class BasicWindow extends Observable implements Runnable {

	/** The display. */
	Display display;
	
	/** The shell. */
	Shell shell;
	
	/**
	 * Instantiates a new basic window.
	 */
	public BasicWindow() {
	}
	
	/**
	 * Instantiates a new basic window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public BasicWindow(String title, int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}
	
	/**
	 * Initializes the widgets.
	 */
	abstract void initWidgets();
	
	
	/**
	 * Initializes the widgets and runs the main loop
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initWidgets();
		
		shell.open();
		
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}

}
