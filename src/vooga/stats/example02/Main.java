package vooga.stats.example02;

import java.awt.Dimension;
import java.awt.Graphics2D;

import vooga.stats.example02.util.resources.ResourceManager;
import vooga.stats.example02.view.components.ErrorCatcher;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.background.ImageBackground;

/**
 * Main method
 * @author Yin
 *
 */
public class Main 
{
	
	//private static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
	private static String GAME_RESOURCE = "game";	
	private static String WINDOW_SIZE = "window_size";
	
	public class Tester extends Game {
		private PlayField myTester;
		
		@Override
		public void initResources() {
			myTester = new PlayField();
			myTester.setBackground(new ImageBackground(getImage("resources/opening.jpg")));
		}
		
		@Override
		public void update(long elapsedTime) {
			myTester.update(elapsedTime);
		}
		
		@Override
		public void render(Graphics2D g) {
			myTester.render(g);
		}

	}
	
	public static void main(String[] args) throws Exception
	{
		
		ResourceManager resources = ResourceManager
        .getManager(GAME_RESOURCE);
		
		String[] windowSize = resources.getStringArray(WINDOW_SIZE,",");
		
		//TrickyGame myGame = new TrickyGame();
		
		Main testMain = new Main();
		Tester myGame = testMain.new Tester();
		
		GameLoader myGameLoader = new GameLoader();
		
		// Run program
		// If exception is caught, user is notified via GUI
		try
		{
			myGameLoader.setup(myGame, 
			   new Dimension(Integer.parseInt(windowSize[0]),Integer.parseInt(windowSize[1])), 
			   false);
			   myGameLoader.start();
		}
		catch (Exception e)
		{
			new ErrorCatcher(e.getMessage());
		}
		
	}
	
}