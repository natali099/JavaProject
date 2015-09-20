package controller;

import java.util.HashMap;

import model.Model;
import view.View;

public class MyController implements Controller {
	Model m;
	View v;
	private HashMap<String, Command> commands;
	
	public MyController() {
		this.setCommands();
	}
	
	public void setModel (Model m) {
		this.m = m;
	}
	
	public void setView (View v) {
		this.v = v;
		this.v.setCLI(commands);
	}
	
	public void setCommands() {
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

	@Override
	public void display(String message) {
		v.display(message);
		
	}
	
	private class DirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 1)
				m.dir(args[0]);
			else
				v.display("invalid parameters, please enter a path");
			
		}
		
	}
	
	private class Generate3dMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 4)
				m.generate3dMaze(args[0], Integer.valueOf(args[1]).intValue(), Integer.valueOf(args[2]).intValue(), Integer.valueOf(args[3]).intValue());
			else
				v.display("invalid parameters, please enter a maze name and 3 dimensions");
			
		}
		
	}
	
	private class DisplayCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 1)
				m.displayMaze(args[0]);
			else
				v.display("invalid parameters, please enter a maze name");
			
		}
		
	}
	
	private class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 3)
				m.displayCrossSection(args[0].charAt(0), Integer.valueOf(args[1]).intValue(), args[2]);
			else
				v.display("invalid parameters, please enter an axis, an index and a maze name");
			
		}
		
	}
	
	private class SaveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 2)
				m.saveMaze(args[0], args[1]);
			else
				v.display("invalid parameters, please enter a maze name and a file name");
			
		}
		
	}
	
	private class LoadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 2)
				m.loadMaze(args[0], args[1]);
			else
				v.display("invalid parameters, please enter a file name and a maze name");
			
		}
		
	}
	
	private class MazeSizeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 1)
				m.mazeSize(args[0]);
			else
				v.display("invalid parameters, please enter a maze name");
		}
		
	}
	
	private class FileSizeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 1)
				m.fileSize(args[0]);
			else
				v.display("invalid parameters, please enter a maze name");
			
		}
		
	}
	
	private class SolveCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 2)
				m.solveMaze(args[0], args[1]);
			else
				v.display("invalid parameters, please enter a maze name and an algorithm");
		}
		
	}
	
	private class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args.length == 1)
				m.displaySolution(args[0]);
			else
				v.display("invalid parameters, please enter a maze name");
		}
		
	}


}
