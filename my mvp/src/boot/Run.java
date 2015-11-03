package boot;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import model.Maze3dModel;
import presenter.Presenter;
import presenter.Properties;
import view.Maze3dGUIView;

public class Run {

	public static void main(String[] args) {
		
		try {
			XMLDecoder xml = new XMLDecoder(new FileInputStream("properties.xml"));
			Properties properties = (Properties)xml.readObject();
			xml.close();
			Maze3dGUIView ui = new Maze3dGUIView("My Maze", 500, 500);
			Maze3dModel m = new Maze3dModel(properties);
			Presenter p = new Presenter(ui, m);
			ui.setCommands(p.getCommands());
			ui.addObserver(p);
			m.addObserver(p);
			ui.start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

}
