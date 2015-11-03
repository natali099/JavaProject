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
 * The Class GenerateMazeInputDialog.
 */
public class GenerateMazeInputDialog extends InputDialog {

	/**
	 * Instantiates a new generate maze input dialog.
	 *
	 * @param parent the parent
	 */
	public GenerateMazeInputDialog(Shell parent) {
		super(parent);
	    setText("Generate maze");
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
	    
	    Label rowsLabel = new Label(shell, SWT.NONE);
	    rowsLabel.setText("Rows:");
	    rowsLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Text rows = new Text(shell, SWT.BORDER);
	    rows.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));
	    
	    Label floorsLabel = new Label(shell, SWT.NONE);
	    floorsLabel.setText("Floors:");
	    floorsLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Text floors = new Text(shell, SWT.BORDER);
	    floors.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));
	    
	    Label columnsLabel = new Label(shell, SWT.NONE);
	    columnsLabel.setText("Columns:");
	    columnsLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Text columns = new Text(shell, SWT.BORDER);
	    columns.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));

	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    ok.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
	    ok.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setInput(new String[]{mazeName.getText(), rows.getText(), floors.getText(), columns.getText()});
	    		shell.close();
	    	}
	    });

	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    cancel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
	    cancel.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setInput(null);
	    		shell.close();
	    	}
	    });

	    shell.setDefaultButton(ok);
	  }
}
