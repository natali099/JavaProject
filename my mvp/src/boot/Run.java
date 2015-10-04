package boot;

import presenter.Presenter;
import model.Maze3dModel;
import view.Maze3dCLIView;

public class Run {

	public static void main(String[] args) {
		Maze3dCLIView ui = new Maze3dCLIView();
		Maze3dModel m = new Maze3dModel();
		Presenter p = new Presenter(ui, m);
		ui.setCLI(p.getCommands());
		ui.addObserver(p);
		m.addObserver(p);
		ui.start();

	}

}
