package boot;

import model.Model;
import model.MyModel;
import view.MyView;
import view.View;
import controller.Controller;
import controller.MyController;

public class Run {

	public static void main(String[] args) {
		Controller c = new MyController();
		View v = new MyView(c);
		Model m = new MyModel(c);
		c.setModel(m);
		c.setView(v);
		v.start();

	}

}
