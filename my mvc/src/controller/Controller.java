package controller;

import model.Model;
import view.View;

public interface Controller {
	void setModel(Model m);
	void setView(View v);
	void display(String message);
	void doCommand(Command command, String[] args);

}
