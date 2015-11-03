package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * The Class MazeNameInputDialog.
 */
public class MazeNameInputDialog extends InputDialog {

	/**
	 * Instantiates a new maze name input dialog.
	 *
	 * @param parent the parent
	 */
	public MazeNameInputDialog(Shell parent) {
		super(parent);
	    setText("Maze name");
	}

	/**
	 * Creates the contents.
	 *
	 * @param shell the shell
	 * @see view.InputDialog#createContents(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	void createContents(Shell shell) {
	    shell.setLayout(new GridLayout(2, true));

	    Label nameLabel = new Label(shell, SWT.NONE);
	    nameLabel.setText("Name:");
	    nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Text mazeName = new Text(shell, SWT.BORDER);
	    mazeName.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setInput(new String[]{mazeName.getText()});
	    		shell.close();
	    	}
	    });

	    shell.setDefaultButton(ok);
	  }
}
