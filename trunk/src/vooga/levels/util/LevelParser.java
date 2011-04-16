package vooga.levels.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.golden.gamedev.object.background.ColorBackground;

import vooga.levels.AbstractLevel;
import vooga.levels.util.tags.BackgroundTag;
import vooga.levels.util.tags.SpriteTag;
import vooga.resources.xmlparser.Parser;
import vooga.resources.xmlparser.XMLTag;

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
	public LevelParser(AbstractLevel level) {
		super();
		
		this.level = level;
		
		spriteFactoryMap = new HashMap<String, SpriteConstructor>();
		converterRack = new ConverterRack();
		
		super.addDefinitions(new LevelTag(), new BackgroundTag(this),
								new SpriteTag(this));
	}
	
	public void parse(String filename) throws LevelParserException {
		File xmlFile = new File(filename);
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc = db.parse(xmlFile);
	        super.parse(doc);
		} catch(IOException e) {
			throw LevelParserException.IO_ERROR;
		} catch (SAXException e) {
			throw LevelParserException.SYNTAX_ERROR;
		} catch (ParserConfigurationException e) {
			throw LevelParserException.SYSTEM_ERROR;
		}

	}
	
	public void setBackground(String background) {
		//FIXME
		level.setBackground(new ColorBackground(Color.black));
	}
	
	public void addSprite(String name, SpriteConstructor factory) {
		spriteFactoryMap.put(name, factory);
	}

	public ConverterRack getConverterRack() {
		return converterRack;
	}
}