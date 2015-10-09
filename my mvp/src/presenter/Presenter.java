package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

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
	* Instantiates a new presenter.
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
			ui.display(((String)arg).getBytes());
			//ui.display(m.getData());
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
				ui.display("invalid parameters, please enter a path".getBytes());			
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
				ui.display("invalid parameters, please enter a maze name and 3 dimensions".getBytes());			
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
				ui.display("invalid parameters, please enter a maze name".getBytes());
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
				ui.display("invalid parameters, please enter an axis, an index and a maze name".getBytes());		
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
				ui.display("invalid parameters, please enter a maze name and a file name".getBytes());			
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
				ui.display("invalid parameters, please enter a file name and a maze name".getBytes());
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
				ui.display("invalid parameters, please enter a maze name".getBytes());
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
				ui.display("invalid parameters, please enter a maze name".getBytes());
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
				ui.display("invalid parameters, please enter a maze name and an algorithm".getBytes());
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
				ui.display("invalid parameters, please enter a maze name".getBytes());
		}
	}

}
