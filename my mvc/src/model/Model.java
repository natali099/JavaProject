package model;

public interface Model {
	void dir(String path);
	void generate3dMaze(String mazeName, int x, int y, int z);
	void displayMaze(String mazeName);
	void displayCrossSection(char axis, int index, String mazeName);
	void saveMaze(String mazeName, String fileName);
	void loadMaze(String fileName, String mazeName);
	void mazeSize(String mazeName);
	void fileSize(String mazeName);
	void solveMaze(String mazeName, String algorithm);
	void displaySolution(String mazeName);
}
