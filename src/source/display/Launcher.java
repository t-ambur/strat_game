package source.display;

import java.awt.Dimension;
import java.awt.Toolkit;

import source.main.Game;

public class Launcher {
	
	public static void main(String[] args)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // grabs the size of the screen for whatever computer the program is run on
		Game game = new Game("Greece", (int) screenSize.getWidth() , (int) screenSize.getHeight()); // Create a game object with title and grabbed size
		game.start(); // start the game.
	}
}
