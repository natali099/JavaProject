package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.Heuristic;
import algorithms.search.MazeAirDistance;
import presenter.Properties;

public class Test {

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setGenerator(new MyMaze3dGenerator());
		p.setHeuristic(new MazeAirDistance());
		Heuristic<Position> h = p.getHeuristic();
		p.setSearcher(new AStar<Position>(h));

		
		try {
			XMLEncoder xml = new XMLEncoder(new FileOutputStream("properties.xml"));
			xml.writeObject(p);
			xml.flush();
			xml.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
