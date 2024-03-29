package vooga.stats.example02.model.rounds;

import java.awt.Graphics2D;
import java.io.IOException;

import vooga.stats.example02.util.commands.ImageReader;
import vooga.stats.example02.util.resources.ResourceManager;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.background.ImageBackground;

/**
 * This class represents rounds that are not in a game 
 * Such as welcome screen, a screen indicating next round
 * @author Yin
 *
 */
public abstract class AbstractTransitRound extends AbstractRound 
{
	private ResourceManager myResourceManager;
	private Background myBackground;
	private GameFont myFont;
	private String myKeyword;
	
	public AbstractTransitRound(String resourceFile, String keyword, GameFont font)
	{
		myResourceManager = ResourceManager.getManager(resourceFile);
		myKeyword = keyword;
		myFont = font;
		//
		//System.out.println(myResourceManager.getString(myKeyword));
		//System.out.println("Width: "+Integer.toString(ImageReader.readImage(myResourceManager.getString(myKeyword)).getWidth()));//
		//
		myBackground = new ImageBackground(ImageReader.readImage(myResourceManager.getString(myKeyword)));

		
		this.setBackground(myBackground);
	}
	
	/**
	 * A method that can catch keyboard activity
	 */
	@Override
	public void execute(long elapsedTime, boolean up, boolean down,
			boolean left, boolean right, boolean click)
	{		
		super.update(elapsedTime);
	}
	
	@Override
	public boolean isWin() 
	{
		return true;
	}

	@Override
	public boolean isLoss() 
	{
		return false;
	}
	
	public GameFont getFont()
	{
		return myFont;
	}
}
