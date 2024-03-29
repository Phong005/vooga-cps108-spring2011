package games.patterson_game.refactoredVooga.levelsRefactored.util.tags;

import vooga.collisions.collisionManager.CollisionManager;
import games.patterson_game.refactoredVooga.levelsRefactored.util.LevelParser;
import games.patterson_game.refactoredVooga.reflection.Reflection;
import games.patterson_game.refactoredVooga.resources.xmlparser.Parser;
import games.patterson_game.refactoredVooga.resources.xmlparser.XMLTag;
import org.w3c.dom.Element;


public class CollisionManagerTag extends XMLTag {
	private static final String TAG_NAME = "collision_manager";
	private static final String CLASS = "class";
	private static final String G1_ATTR = "g1";
	private static final String G2_ATTR = "g2";
	
	private LevelParser parser;
	
	public CollisionManagerTag(LevelParser parser) {
		this.parser = parser;
	}
	
	@Override
	public String getTagName() {
		return TAG_NAME;
	}
	
	@Override
	public void parse(Parser context, Element xmlElement) {
		// Grab the name of the first and second sprite groups, and the class to
		// instantiate.
		String className = xmlElement.getAttribute(CLASS);
		String group1 = xmlElement.getAttribute(G1_ATTR);
		String group2 = xmlElement.getAttribute(G2_ATTR);
		
//		FIXME: Add support for other kinds of constructors?
		CollisionManager collisionManager = (CollisionManager) Reflection.createInstance(className);
		collisionManager.setCollisionGroup(parser.getLevel().getSpriteGroup(group1),
		                                   parser.getLevel().getSpriteGroup(group2));
		parser.getLevel().addCollisionManager(collisionManager);
	}

}
