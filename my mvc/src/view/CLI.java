package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class CLI {
	private View v;
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commands;
	
	public CLI(View v, BufferedReader in, PrintWriter out, HashMap<String, Command> commands) {
		this.v = v;
		this.in = in;
		this.out = out;
		this.commands = commands;
	}

	public void start() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String s;
				try {
					while (!(s = in.readLine()).endsWith("exit")) {
						String[] line = s.split(" ");
						String command = line[0];
						if (commands.containsKey(command)){
							String[] args = new String[line.length-1];
							for (int i=0; i<args.length; i++) {
								args[i] = line[i+1];
							}
							v.doCommand(commands.get(command), args);
						}
						else
							display("\"" + command + "\" command is illegal");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				display("CLI ended");
			}
		}).start();
	}
	
	public void display(String message) {
		out.println(message);
		out.flush();
	}
}
