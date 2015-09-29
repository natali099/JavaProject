package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {
	private Model m;
	private View ui;
	
	public Presenter(View ui, Model m) {
		this.m = m;
		this.ui = ui;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == ui) {
			
		}
		else if (o == m) {
			
		}

		
	}
}
