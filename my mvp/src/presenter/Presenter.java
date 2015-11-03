package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Maze3dModel;
import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class Presenter.
 */
public class Presenter implements Observer {
	/** The model. */
	private Model m;
	
	/** The view. */
	private View ui;
	
	/** The commands hash map. */
	private HashMap<String, Command> commands;
	
	/**
	* Instantiates a new presenter with given view and model.
	* 
	* @param ui the view
	* @param m the model
	*/
	public Presenter(View ui, Model m) {
		this.m = m;
		this.ui = ui;
		this.setCommands();
	}
	
	/**
	 * If the observable is the view, runs the command's doCommand method.
	 * If the observable is the model, runs display method in the view.
	 * 
	 * @param o the Observable that invoked this method
	 * @param arg the arguments sent to the presenter, used by the view or model
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o == ui) {
			Object[] args = (Object[])arg;
			((Command)args[0]).doCommand((String[])args[1]);
		}
		else if (o == m) {
			if (arg == null)
				ui.display(m.getData());
			else if (((String)arg).equals("error"))
				ui.displayError(m.getErrorMessage());
			else if (((String)arg).equals("maze"))
				ui.displayMaze(m.getMaze());
			else if (((String)arg).equals("solution"))
				ui.displaySolution(m.getSolution());
			else if (((String)arg).equals("cross"))
				ui.displayCrossSection(m.getCrossSection());
			
		}
		
	}
	
	/**
	 * Sets the commands hash map.
	 */
	private void setCommands() {
		commands = new HashMap<String, Command>();
		commands.put("dir", new DirCommand());
		commands.put("generate3dMaze", new Generate3dMazeCommand());
		commands.put("display", new DisplayCommand());
		commands.put("displayCrossSectionBy", new DisplayCrossSectionCommand());
		commands.put("saveMaze", new SaveMazeCommand());
		commands.put("loadMaze", new LoadMazeCommand());
		commands.put("mazeSize", new MazeSizeCommand());
		commands.put("fileSize", new FileSizeCommand());
		commands.put("solve", new SolveCommand());
		commands.put("displaySolution", new DisplaySolutionCommand());
		commands.put("setProperties", new SetProperties());
		commands.put("exit", new ExitCommand());
	}
	
	/**
	 * Gets the commands hash map.
	 *
	 * @return the commands hash map
	 */
	public HashMap<String, Command> getCommands() {
		return this.commands;
	}
	
	/**
	 * The Class DirCommand.
	 */
	private class DirCommand implements Command {

		/**
		 * Runs dir method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 1)
				m.dir(args[0]);
			else
				ui.displayError("invalid parameters, please enter a path");			
		}
	}
	
	/**
	 * The Class Generate3dMazeCommand.
	 */
	private class Generate3dMazeCommand implements Command {

		/**
		 * Runs generate3dMaze method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 4)
				m.generate3dMaze(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			else
				ui.displayError("invalid parameters, please enter a maze name and 3 dimensions");			
		}
	}
	
	/**
	 * The Class DisplayCommand.
	 */
	private class DisplayCommand implements Command {

		/**
		 * Runs display method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
		@Override
 		public void doCommand(String[] args) {
			if (args.length == 1)
				m.displayMaze(args[0]);
			else
				ui.displayError("invalid parameters, please enter a maze name");
		}
	}
	
	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	private class DisplayCrossSectionCommand implements Command {

		/**
		 * Runs displayCrossSection method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 3)
				m.displayCrossSection(args[0].charAt(0), Integer.parseInt(args[1]), args[2]);
			else
				ui.displayError("invalid parameters, please enter an axis, an index and a maze name");		
		}
	}
	
	/**
	 * The Class SaveMazeCommand.
	 */
	private class SaveMazeCommand implements Command {

		/**
		 * Runs saveMaze method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 2)
				m.saveMaze(args[0], args[1]);
			else
				ui.displayError("invalid parameters, please enter a maze name and a file name");			
		}
	}
	
	/**
	 * The Class LoadMazeCommand.
	 */
	private class LoadMazeCommand implements Command {

		/**
		 * Runs loadMaze method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 2)
				m.loadMaze(args[0], args[1]);
			else
				ui.displayError("invalid parameters, please enter a file name and a maze name");
		}
	}
	
	/**
	 * The Class MazeSizeCommand.
	 */
	private class MazeSizeCommand implements Command {

		/**
		 * Runs mazeSize method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 1)
				m.mazeSize(args[0]);
			else
				ui.displayError("invalid parameters, please enter a maze name");
		}
	}
	
	/**
	 * The Class FileSizeCommand.
	 */
	private class FileSizeCommand implements Command {

		/**
		 * Runs fileSize method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 1)
				m.fileSize(args[0]);
			else
				ui.displayError("invalid parameters, please enter a maze name");
		}
	}
	
	/**
	 * The Class SolveCommand.
	 */
	private class SolveCommand implements Command {

		/**
		 * Runs solveMaze method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 2)
				m.solveMaze(args[0], args[1]);
			else
				ui.displayError("invalid parameters, please enter a maze name and an algorithm");
		}
	}
	
	/**
	 * The Class DisplaySolutionCommand.
	 */
	private class DisplaySolutionCommand implements Command {

		/**
		 * Runs displaySolution method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
 		@Override
 		public void doCommand(String[] args) {
			if (args.length == 1)
				m.displaySolution(args[0]);
			else
				ui.displayError("invalid parameters, please enter a maze name");
		}
	}
	
	/**
	 * The Class SetProperties.
	 */
	public class SetProperties implements Command {

		/**
		 * Runs setProperties method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			((Maze3dModel)m).setProperties(args[0]);

		}

	}
	
	/**
	 * The Class ExitCommand.
	 */
	private class ExitCommand implements Command {

		/**
		 * Runs exit method in the model.
		 *
		 * @param args the parameters to be passed to the model
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.exit();
		}
		
	}

}
