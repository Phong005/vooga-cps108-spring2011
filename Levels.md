# Introduction #

In the VOOGA game engine, you have the option to use a pre-built Level system to facilitate use of multiple levels for your game. VOOGA provides a basic definition of a level, but this definition can be extended to support additional features for any type of game. **LevelManager** serves as the forward-facing API for the level package.




---


# The LevelManager #
The LevelManager serves as the forward-facing API for the level package and is already integrated with VoogaGame. The LevelManager provides a construct to easily manage and move between different levels in your game. Initial level order is defined via the _resources.xml_ file using the ` <level> ` tag and is automatically set via the ResourceManager in VoogaGame

```
<level id="0" type="games.asteroids.Level">src/games/asteroids/resources/levels/level0.xml</level> 
```

Each level in _resources.xml_ requires 3 parameters: a numeric id, a class type, and a filepath to the individual level XML file. The id is used to refer to level within the LevelManager, which in turn allows the level order to be defined dynamically and gives the developer freedom to name the XML file as they see fit. The class type refers to the Level class that should be used with that particular XML file. Levels of a similar type would likely use the same Level class. The filepath gives the location of the level's XML file, which contains the definition of the level itself.

As an alternative to defining levels in the _resources.xml_ file, the developer can choose to manually specify the level order by creating a map of numeric level ids to the level's specific information:

```
Map<Integer, String[]> levelOrder = new HashMap<Integer, String[]>();

levelOrder.put(0, new String[]{"src/games/asteroids/resources/levels/level0.xml", "games.asteroids.Level"});

LevelManager.setLevelOrder(levelOrder);
```


## Players ##
Vooga defines a level as a group of sprites, sounds, backgrounds, and initial conditions for the level, but does not include players as part of that definition as they are considered objects that are inserted into the level and can persist from level to level, or rather, be inserted into another level. As such, players should be defined in code and added to the LevelManager using the ` addPlayer() ` method. You have the option to define the name of the player group or use the default group name (player). Players are defined as a sprite group and can contain multiple sprites that are considered part of the same player.


# Defining A Level #
In order to define a level, you need to first design a Level class. A level class should extend **AbstractLevel**. The developer must override the loadLevel() method within this level class to reflect the initial conditions of the level. Several convenience methods are provided as part of the LevelManager API to load your level in a variety of ways.

## Level XML File ##
To efficiently utilize the level package, you should define the contents of your level via an XML file. This provides an easy and convenient way to link pre-existing classes to the level system. An example XML file is given below and each of the tags are explained in detail. Note that you can either use standard tags (` <tag> ... </tag> `) or self-closing tags (` <tag ... /> `) and that all class types must be specified as fully-qualified class names.

```
<level>
        <background type="color">red</background>

        <collision_manager class="games.asteroids.collisions.AsteroidBulletCollision" g1="asteroid" g2="player" />
        
        <sprite class="games.asteroids.Asteroid" group="asteroid" type="asteroid" image="asteroid">
                 <component class="games.asteroids.BasicPhysicsC">
                            <velocity>10</velocity>
                            <mass>1</mass>
                 </component>
        </sprite>
        <sprite class="games.asteroids.Bullet" group="bullet" type="bullet" image="bullet" />

        <goal class="games.asteroids.AllAsteroidsDestroyed" />

        <instance type="asteroid">
                <x>100</x>
                <y>100</y>
                <damage>true</damage>
        </instance>

        <strings>
                <totalAsteroids>50</totalAsteroids>
        </strings>

</level>
```

The level XML file is wrapped by a ` <level> ` tag. Any content outside of this tag is ignored. Likewise, any tag that the XML parser does not have a definition for is ignored as well. Currently all supported tags are shown above in the code block.


### ` <background> ` ###
The ` <background> ` tag provides a way to add backgrounds to be used on the level. It requires a type (presently only _Color_ and _Image_ are supported via the XML file) and an argument based on the type. For a color background, specify any Java recognized static color ([Java Colors](http://download.oracle.com/javase/1.4.2/docs/api/java/awt/Color.html)). For an image background, specify an image name (if using the image-mapping feature of the ResourceManager) or an image filepath. It is also possible to manually create a background (required for unsupported types, such as Parallax) in the Level class:

```
Background background = new ParallaxBackground(new Background[]{bg1, bg2});
addToBackgroundQueue(background);
```

To use one of these backgrounds on the level, add the convenience method ` LevelManager.useNextBackground() ` within the ` loadLevel() ` method of your Level class.


### ` <collision_manager> ` ###
The ` <collision_manager> ` tag has a dual function: specifying both Sprite Groups as well as Collision Managers. It requires a class type for the collision manager that provides behavior for when two sprites collide. It also requires the developer to specify two sprite groups that are used in the collision manager. If the group does not yet exist, it is created and can be referenced again later. You also have the option to use the existing player group, as is shown above.


### ` <sprite> ` ###
The ` <sprite> ` tag defines an archetype constructor for a specific type of sprite. It is recommended that you define archetype constructors for any sprite (except a player sprite) that you may use during the course of the level, though it is not required. Two sprite archetypes are defined above: an asteroid and a bullet. Archetypes are required to have 4 attributes and may optionally contain components.

An archetype is tied an existing class that extends **Sprite**. Two are specified above, one with components and one without. Each sprite archetype defined is required to implement a standard constructor:

```
ExampleSprite(BufferedImage image, int x, int y)
```

It also requires a sprite group for which sprites created using the archetype will automatically be added. The archetype is referred to by its type when used to create a sprite:

```
LevelManager.addArchetypeSprite(String type, int x, int y, IComponent[] components); 
```

The archetype also includes an image reference if using the image feature of the ResourceManager or a filepath if not.


### ` <component> ` ###
The ` <component> ` tag defines individual components to be attached to specific sprite archetype constructors. Archetypes defined with a component will automatically be constructed with an instance of the component and its parameters. It is tied to an existing class that defines the behavior of the component. If a component requires arguments, they are required to be specified as child tags and their respective values. All arguments must be defined in order as they would in a standard constructor. Multiple components can be specified for a single archetype sprite, but they must be specified for each individual archetype sprite.


### ` <goal> ` ###
The ` <goal> ` tag defines a link to a class that contains the conditions to complete the level and what happens once the current level is complete (end game, next level, etc). A goal class must implement the interface **IGoal** and the methods ` checkCompletion() ` and ` progress() ` appropriately defined.


### ` <instance> ` ###
The ` <instance> ` tag refers to a specific instance of a sprite you would like to add to your level. These instances are stored in the **SpritePool** temporarily until you choose to add them to your level via one of the convenience methods provided in LevelManager. An instance requires a type matching one of the sprite archetype constructors to be specified as well as the respective constructor arguments for that type of sprite. Instances are not required, but may be useful in defining initial conditions for a level.


### ` <strings> ` ###
The ` <strings> ` tag is used to specify key-value pairs for data that may be used anywhere in the level, but don't particularly fit as part of any of the previous tags. Key-value pairs are stored in a resource bundle and may be accessed using the method ` AbstractLevel.getBundle() ` and any of the associated methods provided by the bundle object.


## Level Class ##
A game using the VOOGA engine must contain at least one level class that extends **AbstractLevel**. You should override the ` loadLevel() ` method using any of the convenience methods to meet your needs. In theory, similar levels should be able to reuse the same level class - for example: a game may have 5 levels in which you shoot aliens that enter the screen from many directions; each of these levels can share a single level class that defines the basic functionality of the alien levels. However, for a game which has levels that behave considerably different, you should define an individual level class for each distinct type of level.

The level class used to create each level when using level XML files is defined in the _resources.xml_ file as described [above](#LevelManager.md).


## Goals ##
Goals provide a mechanism for you to determine when and how a level is completed and what action the game should take afterward. Goals are intentionally under-defined to allow you to design a goal to fit your needs. A goal class should implement the interface **IGoal** and require definitions for the ` checkCompletion() ` and ` progress() ` methods. These two methods define the level-ending conditions and post-ending-action respectively. Each goal class has access to both the game and the current level to give you as much or as little information needed to define a goal.


# Adding Additional Features to Your Level #
While the level package is fairly comprehensive, you may find that you would like to add some additional functionality to the level.


## Adding Additional Tags to the XML File ##
A tag class should extend **XMLTag** and override the methods ` getTagName() ` and ` parse() `. ` parse() ` should define what should be done with the content obtained from the tag and also pass that content to its final location. An example is included below:

```
public MusicTag(LevelParser parser) {
	this.parser = parser;
}

@Override
public String getTagName() {
	return MUSIC;
}

@Override
public void parse(Parser context, Element xmlElement) {
	String filename = xmlElement.getAttribute(FILE);
	
	parser.addToMusicQueue(filename);
}
```

Once you have defined a new tag, you can add it to the parser definition list using ` AbstractLevel.addParserDefinition(XMLTag) `. Note, however, that this must be done for each level class that may use the new tag definition and should be included as part of the ` loadLevel() ` method of the level class.


## Type Converter ##
Information in the XML file is stored as strings and must be converted as part of the parsing process. The level package includes converters for most of the primitive types as well as commonly used types in VOOGA, but you may find that a type you wish to use is not included. A conversion class should extend **Converter** and override the method ` convert() `. The ` convert() ` method should return a result in the target class type. An example conversion class is included below:

```
private class DoubleConverter extends Converter<Double> {
	@Override
	public Double convert(String input) {
		return Double.parseDouble(input);
	}
}
```

Once you have defined a new converter, you can add it to the **ConverterRack** using ` AbstractLevel.addTypeConverter(Class<?> target, Converter<?> converter) `. Note, however, that this must be done for each level class that may use the new type converter and should be included as part of the ` loadLevel() ` method of the level class.


# Help! I'm Still Lost... #
First, look at the asteroids example, then look at the design document in the downloads section (it's a bit outdated but it still should answer the majority of questions). If neither of those resources have answered your question, feel free to contact Wes (wab12@duke.edu) or Andrew (andrew.patterson@duke.edu) and we will do our best to answer your question as soon as possible.