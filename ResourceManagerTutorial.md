# Introduction #

ResourceManager is responsible for reading the central resource.xml file for each game, interpreting its contents and dispatching subparsers to load appropriate resources.

# Syntax #

```
<resources>
    <strings>
        <foo>bar</foo>
    </strings>
    <images>
        <source>images.xml</source>
    </images>
    <level id=0>level0.xml</level>
    <level id=1>level1.xml</level>
    <source>keymap.xml</source>
</resources>
```

In the above example, you see that the root tag is `<resources>`, and it includes all other tags. `<images>` is the enclosing tag for image resources, and the contents of this tag define image resources.

The `<source>...</source>` tag is essentially an include directive.

The `<level>` tag defines levels in the game. the _id_ attribute is the id of the given level, and the enclosed string is the filename of the level.

The `<strings>` tag encloses key-value pairs of strings. For instance, _foo_ would be the key to value _bar_ in the example above. These are accessed via the Bundle object returned by the `getBundle()` method of ResourceManager.


## Example images.xml (from Asteroids) ##
```
<images>
	<image name="ship">
		<path>src/games/asteroids/resources/images/ship.gif</path>
	</image>
	<image name="bullet">
		<path>src/games/asteroids/resources/images/green_bullet.gif</path>
	</image>
	<image name="asteroid">
		<path>src/games/asteroids/resources/images/asteroid.gif</path>
	</image>
</images>
```

In the above images.xml file, the _name_ attribute specifies the common name of the image resource, and the path its path.

## Example keymap.xml (from Asteroids) ##
```
<keymap>
	<key_event event="Input.User.TurnLeft" sensitivity="Level" key="LEFT"/>
	<key_event event="Input.User.TurnRight" sensitivity="Level" key="RIGHT"/>
	<key_event event="Input.User.Shoot" sensitivity="Edge" key="SPACE"/>
</keymap>
```

So the keymap defines key\_events, which have an event to fire, a sensitivity (whether to fire only once or as long as the button is pressed down), and the associated key (from KeyEvent.VK