# Introduction #

Based on our discussion, I have created the packages below to serve as the basis for our engine that will support making games more easily. However, I do not necessarily expect that this will be the final breakdown of these responsibilities. As we flesh out the overall design, some packages may be merged and new ones may be created --- thus communication between the teams working on these packages is essential, even from the start.

Claiming a package below will be done on a first come first serve basis this week in your current teams, but for the next round it will be based on interest and knowledge of the area (so we will shuffle teams). To claim a package, a team must **edit this page** to fill out the information requested for the package. If your team cannot complete this basic information, then it may not be appropriate for you to work on that package.

You may add new packages to the list below, but they must have the same basic information as the other packages and be substantially different from the existing packages. You may also change the name of a package if you have something more descriptive or general in mind.


# Details #

  1. Players
    * Claimed by Kevin Tao
    * Description: includes managing score and other attributes
    * Design Goals:  Create a flexible player class that can hold different types of information about itself and have uniquely defined behavior by means of extension rather than modification.
    * Core Responsibilities: Create a player class with methods that allow the user to customize its behavior/reactions to events and set/update information about itself.
    * Collaborates With: Events; Stats; Game Resources; Levels; Network Engine
    * Extensions: Clone; History
  1. Levels
    * Claimed by Andrew Patterson, Wes Brown
    * Description: Includes defining a level and its content, and initializing and managing all levels.
    * Design Goals: Define a flexible file format that can be used to represent and read in various types of levels
    * Core Responsibilities: Provide a file format to represent a level and provide a method to import and initialize game levels.
    * Collaborates With: States, Players, Resources, Level Editor
    * Extensions:
  1. Views
    * Claimed by Alex Daniel, Andrew Miller
    * Description: views of game space, additional window features for game
    * Design Goals: Create a game space that allows for image wrapping/reflecting
    * Core Responsibilities: Define edge behavior and imaging
    * Collaborates With: Physics (setting coordinate standards)
    * Extensions: z-axis
  1. Collisions & Advanced Sprites
    * Claimed by Max Egan, Julian Genkins
    * Description: Creating more intelligent, efficient collision manager and collision system. Allow for a wider variety of collisions.
    * Design Goals: Include both a standard set of collision guidelines as well as an API that allows extension into game-specific collisions.
    * Core Responsibilities: A user friendly collision core that is strong enough for ease of basic use but flexible enough to be extended into environments  which might require implementation of new collision rules. Adding functionality to the basic sprite class including customizable collision shapes, collision direction and physical linkage.
    * Collaborates With: Physics Engine, Player
    * Extensions: Physics based collisions, easily implementable varieties of shapes
  1. Events
    * Claimed by Michael Ansel, Ethan Goh
    * Description: Provides the control necessary for an event driven system.  Manages the timely processing and synchronization of events.
    * Design Goals: Create an efficient and extensible system that allows users to easily register and swap out sets of event handlers.
    * Core Responsibilities: Provide the framework for which all the components make requests (fire/register events) to the engine to be processed.
    * Collaborates With: Game Loop, States/Modes, Physics Engine, Game Resources, Any other team that potentially requires a set of controls to drive their component.
    * Extensions:
  1. States/Modes
    * Claimed by
    * Description: To coordinate flow between different game states in a Game (ie. running, paused, loading, etc.) - "kind of a state-machine kind of style"
    * Design Goals: To create a very easy way to add different "GameStates" to a game and organize Game Loop coding accordingly
    * Core Responsibilities: Implementing multiple GameStates within a hierarchy system, holding/managing respective keyEvents for each GameState
    * Collaborates With: Game Loop, Event Team, Network Engine, and LevelTeam
    * Extensions: tbd.
  1. Game Resources
    * Claimed by Misha Lavrov, Sterling Dorminey
    * Description: path-based sprite movement, XML image loading, simple sprite-based GUI, customizable keyboard input, advanced randomization
    * Design Goals: Create lightweight utility classes that can be used with GTGE tools without imposing a strict framework
    * Core Responsibilities: Provide framework for loading non-level resources and other utility functions.
    * Collaborates With: maybe Events, Level & Level Editor, for certain functionality
    * Extensions:
  1. Stats
    * Claimed by Yin Xiao, Chao Chen
    * Description: view game statistics separate from game play
    * Design Goals: To help game designer record any statistical or historical information for a game.
    * Core Responsibilities: By using observer update statistical information. Can return, save or write this information.
    * Collaborates With: Mainly Events, any group that potentially needs game stats.
    * Extensions:
  1. Game Control
    * Claimed by Shun Fan
    * Description: Help coordinate the other teams so they'll fit together into a real game!
    * Design Goals: Make sure a game can be built on top of this.
    * Core Responsibilities: Communicate with other teams so they understand how they fit into the bigger system.
    * Collaborates With: Players, Levels, Game Resources, Collisions
    * Extensions:
  1. Physics Engine
    * Claimed by Nathan Klug, Anne Weng
    * Description: Allow game designers to easily use standard physical reactions or define their own style of physics for a game.
    * Design Goals: Model basic Newtonian physics (F = ma, conservation of momentum, etc), allow users to choose which elements are acting, and allow for easy additions of new forces
    * Core Responsibilities: Extensible way to add forces like gravity, manage the changes to the state of game objects after a collision.
    * Collaborates With: Events, Collisions, (possibly Level/Level Editor for the currently active forces), View (setting coordinate standards)
    * Extensions:
  1. Networking Engine
    * Claimed by Lingzhao Xie, Roman Zhang
    * Description: Allow users to sit at different computers and connect to a common game session. An update at any one computer should be visible immediately to all connected users.
    * Design Goals: Provide an API so users can create a game and become visible on the local area network, other users can join/exit the game. Users can send out any serializable object, the network engine will generate an event upon receive.
    * Core Responsibilities:
    * Collaborates With: Events, Players
    * Extensions:
  1. Replay
    * Claimed by Josue Mirtil, Chris James
    * Description:  Allow users to save, resume, or re-view previously played games.
    * Design Goals: Creating Class that links changes to game elements by time elapsed between changes. This "StateTable" would then be used to replay/save/resume play. StateTable will be stored as serialized objects. Save files in txt format will save StateTables and rebuild as tree structure, based on resume point and save point.
    * Core Responsibilities: Create a way in which the user can save and resume gameplay even after closing the game. Also allow user to replay game.
    * Collaborates With: ???
    * Extensions: Allow user to compare replays using tree traversal. Allow for fast-forward, rewind, and skipping through replays.
  1. Level Editor
    * Claimed by Charlie Hatcher, Alex Lee
    * Description: Allow the users to load in a xml level file into a swing GUI and edit the level through drag and drop rather than hand editing the xml file.
    * Design Goals:Create an efficient and general model that allows users to create/modify levels for all types of games (panning, static etc).
    * Core Responsibilities: Develop a user interface that allows users to load, save, and alter level files graphically.
    * Collaborates With:Level, Game Resources, Standard XML file format
    * Extensions:
  1. Debugger (Development Environment)
    * Claimed by Troy Ferrell, Austin Benesh, Ethan Goh
    * Description: Allow designers greater control over development of their games by allowing them to easily trace their system during testing
    * Design Goals: Create a debugging framework that allows for the reading & setting of variables in the system
    * Core Responsibilities: Develop user interface for manipulation of values. Also develop UI for visual representation of variable state over time(plot graph)
    * Collaborates With: everyone(configuring debugger to display game engine classes from other teams)
    * Extensions:
  1. User Interface Design
    * Claimed by David Colon-Smith, David Crowe
    * Description: Create user interface components- menus, "pop-overs", and display components.
    * Design Goals: Create re-usable parts that are extendable and easy to use- that can be used to structure the game into a coherent and impressive final product
    * Core Responsibilities: UI Objects
    * Collaborates With:
    * Extensions:
  1. Artificial Intelligence Engine
    * Claimed by: Shun Fan
    * Description: Allow game designers to create smart enemies to oppose the player.
    * Design Goals: Create an engine to control a player either in single or multi-player mode
    * Core Responsibilities:
    * Collaborates With: Players, Events, Collisions and Advanced Sprites
    * Extensions:
  1. Standard XML Resources
    * Claimed by: Sterling Dorminey, Alex Daniel
    * Description: Design XML file(s) rather than Java code that can be used to initialize your game (i.e., describing game level configurations, game resources, and other game specific data as appropriate).
    * Design Goals:
    * Core Responsibilities:
    * Collaborates With:
    * Extensions:
  1. Arcade
    * Claimed by Kevin Wang, Ethan Goh, Andrea Scripa
    * Description: Players can pick a game to play and play it repeatedly without quitting and restarting the program. Each game should display its own splash screen, instructions, any setup options it might need, and keep track of a high-score list through successive runs of the program until the player manually clears it. Games can be searched, tagged, or view what's being played right now.
    * Design Goals: Make it easy for user to navigate and search through several games and choose the game they want to play.
    * Core Responsibilities: record high scores and pertinent data regarding each game in catalog. Allow for easy navigation between each game.
    * Collaborates With: User, level, player, event
    * Extensions: Allow extension for other means of navigation through different games.
  1. Users
    * Claimed by: Conrad Haynes
    * Description: Allow users to log in, choose an avatar to be used within the arcade (or even individual games), view personal high scores, and save their preferences (e.g., name, password, image, age (if parental controls are implemented), and favorite games, players, colors, etc). Offer social aspects by allowing players to rate, recommend, chat, or generally collaborate while playing games. Additionally, keep track of social aspects of game playing, such as most popular, recent, and highly reviewed games played.
    * Design Goals: To create a simple log-in / preference setting user-interface in the game that allow game designers to easily set their preferences, stats, and ratings.
    * Core Responsibilities: Develop multi-faceted user interface with back and front end, allow users to choose an avatar with saved preferences, and create easy log-in system for players to reach certain avatars
    * Collaborates With: Stats Team, Level Editor & Menu Team (for Swing GTGE interface advice, Replay Team (for info about the resume game table), Rouge, and Arcade Team
    * Extensions: Possible Extension of a Database
  1. Program Manager
    * Claimed by: DJ Sharkey, Ethan Goh
    * Description: Trying to make this system fit together.
    * Design Goals: Build a game
    * Core Responsibilities: Ugh.
    * Collaborates With: Everyone.
    * Extensions:
  1. Mod Environment
    * Claimed by
    * Description: Game companies encourage this practice because different versions of a game add extra play value and interest for purchasing the original game. Basic characteristics of the look and feel of the game should be able to be easily changed: the graphical icons used in game (e.g., to turn a SciFi game into a political statement); the keys used for interaction (e.g., to accommodate multiple players on the same keyboard); and the point values of game objectives (e.g., to make a bonus level). Extend it further by allowing users to change variables of game play or even add new rules or behaviors to a game.
    * Design Goals:
    * Core Responsibilities:
    * Collaborates With:
    * Extensions: