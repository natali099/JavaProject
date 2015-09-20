package view;

import java.util.HashMap;

import controller.Command;

public interface View {
	void start();
	void setCLI(HashMap<String, Command> commands);
	void display(String message);
	
}
