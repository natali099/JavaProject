package boot;

import presenter.Presenter;
import model.Maze3dModel;
import view.Maze3dView;

public class Run {

	public static void main(String[] args) {
		Maze3dView ui = new Maze3dView();
		Maze3dModel m = new Maze3dModel();
		Presenter p = new Presenter(ui, m);
		ui.addObserver(p);
		m.addObserver(p);

	}

}
