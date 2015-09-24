package controller;

import java.util.HashMap;

import model.Model;
import view.View;

/**
 * The Class MyController.
 */
public class MyController implements Controller {
	
	/** The model. */
	private Model m;
	
	/** The view. */
	private View v;
	
	/** The commands hash map. */
	private HashMap<String, Command> commands;
	
	/**
	 * Instantiates a new my controller and sets the commands.
	 */
	public MyController() {
		this.setCommands();
	}
	
	/**
	 * Sets the model.
	 *
	 * @param m the model
	 * @see controller.Controller#setModel(model.Model)
	 */
	public void setModel (Model m) {
		this.m = m;
	}
	
	/**
	 * Sets the view.
	 *
	 * @param v the view
	 * @see controller.Controller#setView(view.View)
	 */
	public void setView (View v) {
		this.v = v;
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
	
	public HashMap<String, Command> getCommands() {
		return this.commands;
	}

	/**
	 * Runs display method in the view.
	 *
	 * @param message the message to be displayed
	 * @see controller.Controller#display(java.lang.String)
	 */
	@Override
	public void display(String message) {
		v.display(message);		
	}
	
	/**
	 * Runs doCommand method in the received command.
	 *
	 * @param command the command to be done
	 * @param args the parameters to be passed to the command
	 * @see controller.Controller#doCommand(controller.Command, java.lang.String[])
	 */
	@Override
	public void doCommand(Command command, String[] args) {
		command.doCommand(args);		
	}
	
	/**
	 * The Class DirCommand.
	 */
	private class DirCommand implements Command {

		/**
		 * Runs doCommand method in the model with "dir" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("dir", args);			
		}		
	}
	
	/**
	 * The Class Generate3dMazeCommand.
	 */
	private class Generate3dMazeCommand implements Command {

		/**
		 * Runs doCommand method in the model with "generate" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("generate", args);
		}		
	}
	
	/**
	 * The Class DisplayCommand.
	 */
	private class DisplayCommand implements Command {

		/**
		 * Runs doCommand method in the model with "display" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("display", args);
		}
	}
	
	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	private class DisplayCrossSectionCommand implements Command {

		/**
		 * Runs doCommand method in the model with "cross" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("cross", args);
		}		
	}
	
	/**
	 * The Class SaveMazeCommand.
	 */
	private class SaveMazeCommand implements Command {

		/**
		 * Runs doCommand method in the model with "save" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("save", args);
		}		
	}
	
	/**
	 * The Class LoadMazeCommand.
	 */
	private class LoadMazeCommand implements Command {

		/**
		 * Runs doCommand method in the model with "load" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("load", args);
		}		
	}
	
	/**
	 * The Class MazeSizeCommand.
	 */
	private class MazeSizeCommand implements Command {

		/**
		 * Runs doCommand method in the model with "maze size" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("maze size", args);
		}		
	}
	
	/**
	 * The Class FileSizeCommand.
	 */
	private class FileSizeCommand implements Command {

		/**
		 * Runs doCommand method in the model with "file size" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("file size", args);
		}		
	}
	
	/**
	 * The Class SolveCommand.
	 */
	private class SolveCommand implements Command {

		/**
		 * Runs doCommand method in the model with "solve" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("solve", args);
		}		
	}
	
	/**
	 * The Class DisplaySolutionCommand.
	 */
	private class DisplaySolutionCommand implements Command {

		/**
		 * Runs doCommand method in the model with "solution" parameter
		 * 
		 * @param args the parameters to be passed to the model
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			m.doCommand("solution", args);
		}		
	}

}
