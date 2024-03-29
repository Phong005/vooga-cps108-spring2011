package games.patterson_game.refactoredVooga.levelsRefactored.util;

import games.patterson_game.refactoredVooga.core.VoogaGame;
import games.patterson_game.refactoredVooga.levelsRefactored.AbstractLevel;
import games.patterson_game.refactoredVooga.levelsRefactored.IGoal;
import games.patterson_game.refactoredVooga.levelsRefactored.util.tags.*;
import games.patterson_game.refactoredVooga.resources.xmlparser.Parser;
import games.patterson_game.refactoredVooga.resources.xmlparser.ParserException;
import games.patterson_game.refactoredVooga.resources.xmlparser.XMLTag;
import vooga.sprites.improvedsprites.Sprite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.golden.gamedev.object.Background;


/**
 * XML Level Parser
 * @author Sterling Dorminey
 *
 */
public class LevelParser extends Parser {
	private AbstractLevel level;
	private Map<String, SpriteConstructor> spriteFactoryMap;
	private ConverterRack converterRack;
	
	private static final class LevelTag extends XMLTag {
		public static final String LEVEL = "level";

		@Override
		public String getTagName() {
			return LEVEL;
		}
		
	}
	public LevelParser(AbstractLevel level, VoogaGame game) {
		super();
		
		this.level = level;
		
		spriteFactoryMap = new HashMap<String, SpriteConstructor>();
		converterRack = new ConverterRack(game);
		
		super.addDefinitions(	new LevelTag(), 
								new BackgroundTag(this, game.getImageLoader()),
								new SpriteTag(this), 
								new CollisionManagerTag(this),
								new InstanceTag(this),
								new GoalTag(this),
								new ComponentTag(this),
								new MusicTag(this));
	}
	
	public AbstractLevel getLevel() {
		return level;
	}
	
	public void addToBackgroundQueue(Background background) {
		level.addToBackgroundQueue(background);
	}
	
	public void addToMusicQueue(String music) {
		level.addToMusicQueue(music);
	}
	
	public void setGoal(IGoal goal) {
		level.addGoal(goal);
	}
	
	public void addSpriteArchetype(String name, SpriteConstructor factory) {
		spriteFactoryMap.put(name, factory);
	}

	/**
	 * Construct a sprite given the name of its archetype and other assignments for it.
	 */
	public Sprite makeSprite(String name, List<String> assignments) {
		SpriteConstructor factory = spriteFactoryMap.get(name);
		return factory.construct(assignments);
	}
	
	public Sprite makeSprite(String name, Object ... assignments) {
		SpriteConstructor factory = spriteFactoryMap.get(name);
		return factory.construct(assignments);
	}
	
	/**
	 * Returns the sprite constructor associated with the given archetype.
	 */
	public SpriteConstructor getSpriteConstructor(String type) {
		SpriteConstructor constructor = spriteFactoryMap.get(type);
		if(constructor == null) throw ParserException.SYNTAX_ERROR;
		return constructor;
	}
	
	/**
	 * Returns the map of archetype names to sprite constructors.
	 */
	public Map<String, SpriteConstructor> getSpriteConstructorMap() {
		return spriteFactoryMap;
	}
	
	public ConverterRack getConverterRack() {
		return converterRack;
	}

}
