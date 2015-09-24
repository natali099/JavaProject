package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;

public class MyView implements View {
	private Controller c;
	private CLI cli;

	public MyView (Controller c) {
		this.c = c;
	}

	@Override
	public void start() {
		cli.start();
		
	}

	@Override
	public void setCLI(HashMap<String, Command> commands) {
		this.cli = new CLI(this, new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out), commands);
		
	}

	@Override
	public void display(String message) {
		cli.display(message);
		
	}

	@Override
	public void doCommand(Command command, String[] args) {
		c.doCommand(command, args);
		
	}

}
