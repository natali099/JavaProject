package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class InputDialog.
 */
public abstract class InputDialog extends Dialog {

	/** The input. */
	private String[] input;

	/**
	 * Instantiates a new input dialog.
	 *
	 * @param parent the parent
	 */
	public InputDialog(Shell parent) {
	    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	/**
	 * Instantiates a new input dialog.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public InputDialog(Shell parent, int style) {
	    super(parent, style);
	}
	
	/**
	 * Gets the input.
	 *
	 * @return the input
	 */
	public String[] getInput() {
	    return input;
	}
	
	/**
	 * Sets the input.
	 *
	 * @param input the new input
	 */
	public void setInput(String[] input) {
	    this.input = input;
	}
	
	/**
	 * Opens the dialog.
	 *
	 * @return the input
	 */
	public String[] open() {
	    Shell shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	    	if (!display.readAndDispatch()) {
	    		display.sleep();
	    	}
	    }
	    return input;
	}
	
	/**
	 * Creates the contents.
	 *
	 * @param shell the shell
	 */
	abstract void createContents(final Shell shell);		  
}