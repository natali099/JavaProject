package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.Command;


/**
 * The Class CLI.
 */
public class CLI {
	
	/** The view which this CLI belongs to. */
	private View v;
	
	/** The input. */
	private BufferedReader in;
	
	/** The output. */
	private PrintWriter out;
	
	/** The commands hash map. */
	private HashMap<String, Command> commands;
	
	/**
	 * Instantiates a new cli.
	 *
	 * @param v the view
	 * @param in the input
	 * @param out the output
	 * @param commands the commands hash map
	 */
	public CLI(View v, BufferedReader in, PrintWriter out, HashMap<String, Command> commands) {
		this.v = v;
		this.in = in;
		this.out = out;
		this.commands = commands;
	}

	/**
	 * Starts CLI in a thread.
	 * Reads commands from the input and runs doCommand method in the view until receiving "exit" command.
	 */
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
	
	/**
	 * Prints the given message to the output.
	 *
	 * @param message the message
	 */
	public void display(String message) {
		out.println(message);
		out.flush();
	}
}
