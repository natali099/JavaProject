package boot;

import model.MyModel;
import view.MyView;
import controller.MyController;

public class Run {

	public static void main(String[] args) {
		MyController c = new MyController();
		MyView v = new MyView(c, c.getCommands());
		MyModel m = new MyModel(c);
		c.setModel(m);
		c.setView(v);
		v.start();

	}

}
