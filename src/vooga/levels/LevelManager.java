package vooga.levels;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import vooga.core.VoogaGame;
import vooga.levels.example.reflection.Reflection;
import vooga.player.Player;
import com.golden.gamedev.object.Sprite;

/**
 * A manger that facilitates movement between levels, stores information
 * regarding the overall state of the levels and maintains the user�s position/
 * progress in the game.
 * 
 * @author Andrew Patterson & Wesley Brown
 */
public class LevelManager {
	private static final String LEVEL_ORDER_FILE = "level_resources/LevelOrder";

	/** A map of level number to the associated XML file */
	private Map<Integer, String> myLevelOrderMap;

	/** The total number of levels */
	private int myNumOfLevels;

	/** The total number of levels completed */
	private int myNumOfLevelsCompleted;

	/** The players for this level */
	private Collection<Player> myPlayers;

	/** The current running game */
	private VoogaGame myGame;
	
	private AbstractLevel myActiveLevel;

	/**
	 * Maps level names/classes to level order
	 */
	public LevelManager(VoogaGame g, Collection<Player> players) {
		myLevelOrderMap = new HashMap<Integer, String>();
		Scanner in;

		try {
			in = new Scanner(new File(LEVEL_ORDER_FILE));
		} catch (FileNotFoundException e) {
			throw LevelException.NON_EXISTANT_LEVEL_ORDER;
		}

		int levelNumber = 0;
		while (in.hasNext()) {
			myLevelOrderMap.put(levelNumber, in.next());
			levelNumber++;
		}
		myNumOfLevels = levelNumber;
	}

	/**
	 * Attempts to load level with specified id. Checks to see if the level being
	 * loaded is of the same type as the current level. If so, it maintains the current
	 * instance and populates the instance with the new level content. 
	 * 
	 * @param id representing level to load
	 */
	public void loadLevel(int id) {		
	    if(!(myLevelOrderMap.containsKey(id)))
	        throw LevelException.NON_EXISTANT_LEVEL;
		String levelFileName = myLevelOrderMap.get(id);
	
		// First item is the class type of the level
		//second is the user defined name which is a comment
		String[] levelDef = levelFileName.split("\\_");
		String activeLevelClass = myActiveLevel.getClass().getName();
		activeLevelClass = activeLevelClass.substring(0,activeLevelClass.indexOf(".")); //Gets rid of .class
		if (activeLevelClass.equals(levelDef[0])) {
			myActiveLevel.loadLevel(levelFileName);
		} 
		else {
			try {
				myActiveLevel = ((AbstractLevel) Reflection.createInstance(levelDef[0], myPlayers, myGame));
				myActiveLevel.loadLevel(levelFileName);
			} catch (Exception e) {
				throw LevelException.LEVEL_LOADING_ERROR;
			}
		}
	}

	/**
	 * Loads the level that comes after the current level
	 */
	public void loadNextLevel() {
		loadLevel(myActiveLevel.getId() + 1);
	}

	/**
	 * Loads the level that came before the current level
	 */
	public void loadPreviousLevel() {
		loadLevel(myActiveLevel.getId() - 1);
	}

	/**
	 * Checks if the current level is complete
	 */
	public void checkLevelCompletion() {
		if(myActiveLevel.checkCompletion())
		    myNumOfLevelsCompleted++;
	}

	/**
	 * Retrieves the highest running level's id
	 */
	public int getCurrentLevel() {
		 return myActiveLevel.getId();
	}

	/**
	 * Returns number of levels completed
	 */
	public int getNumOfLevelsCompleted() {
		return myNumOfLevelsCompleted;
	}

	/**
	 * Returns the total number of levels
	 */
	public int getNumOfLevels() {
		return myNumOfLevels;
	}

	/**
	 * Adds a random sprite from the lowest running level pool
	 */
	public void addRandomSprite() {
		myActiveLevel.addRandomSprite();
	}

	/**
	 * Returns a Sprite from the lowest running level sprite pool
	 * 
	 * @return a random sprite
	 */
	public Sprite getRandomSprite() {
		return myActiveLevel.getRandomSprite();
	}

	/**
	 * Add a new sprite of the specified type to the playingfield. The sprite is
	 * taken from the lowest running level sprite pool.
	 * 
	 * @param type
	 *            of Sprite to add
	 */
	public void addNewSprite(String type) {
		myActiveLevel.addSprite(type);
	}


	/**
	 * Add a new sprite of the specified type to the playingfield. The sprite is
	 * taken from the lowest running level sprite pool.
	 * 
	 * @param type
	 *            of Sprite to return
	 * @return sprite of the specified type
	 */
	public Sprite getNewSprite(String type) {
		return myActiveLevel.getSprite(type);
	}

	/**
	 * Changes the playingfield background to the next background in a sequence
	 * of backgrounds. The background is taken from the lowest running level.
	 */
	public void useNextBackground() {
		myActiveLevel.addBackground();
	}

	/**
	 * Plays the next music file from a sequence of music files. The music is
	 * taken from the lowest running level.
	 */
	public void useNextMusic() {
		myActiveLevel.addMusic();
	}

	public void update(long elapsedTime) {
		 myActiveLevel.update(elapsedTime);
	}

	public void render(Graphics2D g) {
		 myActiveLevel.render(g);
	}
}