# Event System Design Document #

#### Author: Ethan Yong-Hui Goh ####

## Disclaimer: ##
Some of this information might be slightly inaccurate. If there is a gaping issue that one is still unclear on after reading this wiki, feel free to email Ethan or Michael with more specific questions directly. We are trying to make this system as easy to understand and use as possible. Thank you for your patience.



# Introduction #

## Background and Definitions ##
The Game Engine is designed to be event driven, meaning that specific triggering of events and invocation of their respective callbacks change the state of the Game, which in turn leads to another set of states based on other internal or external chain reactions. The important concept of designing an event system involves the thorough understanding of **Event Registration, Event Listeners** and **Event Callbacks**. As part of the preliminary steps before partaking in a detailed discussion of the design of the Event System, we will proceed to provide definitions for this terminology.

**Event Registration** is the act of keeping track of an Event, namely by attaching an 'unbiased' observer to it. This 'unbiased' observer is known as the **Event Listener**, which notifies a reactionary force that a signal has been received. This reactionary force is often called the **Event Handler** or the **Event Callback**.

## Examples of Event Driven Systems ##
A physical example will be presented to accurately portray this concept. Imagine you were going to the bathroom. As you enter the bathroom, the lights turn on. The act of you entering the bathroom (the Event firing), caused the lights to turn on (Event Handling). In between this process, there was most likely a motion sensor installed at the entrance of the bathroom (Event Listener). Moreover, if one washes their hands at an automatic sink, the act of placing ones hand near is sensor is an event that causes the water to flow. These action-reaction/ cause-effect pairs are the crux of event driven systems.

As another example, Event driven systems are exemplified by sensors such as trap doors or laser trip mines . In this case, event registration is the act of setting up a laser trip mine whose beam spans across the room. The trip mine listens on any moving object that comes in between the path of the mine. When this event is triggered, the mine handles this swiftly by exploding in the face of the intruder.

From this example, we have a basic design concept for a video game. One could easily imagine a maze game that requires one to navigate through trap filled room, whose traps are all registered in exact same fashion as described above. Explosions of laser mines could set of other adjacent mines, and lead to various other chain reactions that violently changes the state of the game.

## What this really means ##
Classically, one could think of games as a temporal progression (time-driven engine) - i.e after a fixed amount of time, spawn enemies in top left corner; after the player's weapon charges up again, fire the weapon. All these things happen after a timer expires, and the forward progression in time drives the changes in game state.

However, the key conceptual jump is abstracting time-driven engines by another level, noting and understanding that a the ticks of timers are merely specific events. By containment, we see that a time-driven engine is a subset of an event-based engine, making the event-driven system potentially much more powerful and extensible than the time-driven system, which is the justification for using an event-based system to drive this game engine.

Unfortunately, an event-system is not the panacea for all game design - the cost one pays for using an event-system is the multiple orders of increased complexity that comes with this additional design power. In order to bring order to the chaos that might ensue by making interconnections between events, this event system API strives to simplify, organize and process the events in a manner that is debuggable within reasonable time to an average human being.

### Benefits of an Event Driven System ###

Shifting from a temporal game engine concept to an event driven concept can sometimes be rather hard to grasp. Often times, you may find yourself reverting to trying to make a game based on a global game timer. It is import to not lose sight of the event driven paradigm - to think of the game in terms of cause and effect pairs, that is, the occurring of certain events trigger other actions that follow logically.

The event driven system makes communication between classes extremely easy and extensible. In the overarching game class, instead of holding references to the every single object that the class would need to make a call to, all of that information is now contained and isolated within the event handler. This allows for much cleaner looking code, and lets one define "chain reactions" very easily. For example, similar to the Observer-Observable pattern, one can have multiple events (Observers) linked to the same event trigger (Observable), which transfers work to within a class that waits on an event to make a change to itself.

All object-oriented programmers will agree that having large decision trees to drive code is considered extremely poor design. With the event based model, when used appropriately, it removes the necessity of such clumsy blocks of code, which now is handled by the registration of events.  Moreover, work in now processed in a queue rather than a stack of function calls. In the earlier design, functions that occur logically in time would have to be nested or placed back to back.  Given this static call to the function, one is unable to interrupt the work flow until it is completed. If there were a method that made deep or computationally costly calls, this would reflect poorly on the performance of the engine, as the user would experience a "lag", or non-responsiveness in input. To avoid this, one would have to be consciously aware of the computational weight of each method call, and manually load balance minimize the latency.

However, events allow us to dynamically make function calls - the closest one can get in Java to function pointers in C/C++. By keeping a list of things to process in a queue, this allows the stream of work to be paused at any time, while maintaining the temporal order of the method calls without having to explicitly know the cost of each method call. The system can now poll for user input at any point in time in the event loop, by a simple swapping of the event reactor.


# Design #

## The Event Manager Class ##

The Event Manager Class is the core of the Event System. It processes all event related things, such as event registration, firing of events, and handling of events. The interface to the Event Manager allows registration of Events of three types - basic event handlers, timed events, and every-turn events.

## Event Registration ##

### Basic Event Handlers ###
Basic event handlers listen on a specific Event that will be fired in the future. Registration of this type requires the exact String name of the Event that it is listening on, and the callback method that is to be invoked upon receiving the event.

```
public void registerEventHandler(String eventName, IEventHandler eventHandler);
```

### Timed Events ###
There are two types of Timed Events - periodic and one-shot timer events. Periodic events are events that need to be fired at specific time intervals, for example, the activation of a player's weapon after a specified cooldown period. One-shot events on the other hand, are events that are only fired once at a specified time in the future, for example, starting a boss battle 30 seconds after the level has started.

In both cases, the interface two each type is similar. It requires the exact name of the Event to be fired, and the time delay between the firing of the event(s).
```
public Timer addTimer (String eventName, long delay);  // one shot
public PeriodicTimer addPeriodicTimer (String eventName, long interval) // periodic
```

### Every-Turn Events ###
Every-Turn Events are actions that need to be performed constantly by the game, for example, in collision detection or in the physics engine. Every-Turn events should not be explicitly fired by the user.

```
public void addEveryTurnEvent(String eventName, IEventHandler eventHandler)
```

## Event Grouping and Naming ##
Currently, groups of events are supported with globs, which serve as namespaces for the event group. This particular implementation may change in the later future, however, support of this feature will not disappear.

http://en.wikipedia.org/wiki/Glob_(programming)

Unfortunately, due to inherent tendency for Strings to be accidentally mistyped, we have come up with a list conventions in order to avoid accidental misuse.

  1. Separate each logical word chunk in the description with a period character '.'
  1. Use Upper Camel Case, that is, capitalize the first letter of every word.
  1. Do not use white space.

Examples: Level.Alien.MoveLeft, Menu.HeadsUpDisplay.ClickWeapon

There are also a few predefined namespaces that the user may choose to use when appropriate:
  * Input.`*`
  * Level.`*`
  * User.`*`

## Firing Events ##
The Event Manager currently supports firing events by specific name, or firing groups of events specified by globs. To fire an event, you can invoke one of the following methods in Event Manager:

```
public void fireEvent(final Object source, final String eventName)
public void fireEvent(final Object source, final String eventName, final Object arg)
```

The second method contains a third argument, in which one can pass parameters that are needed inside the event handler. The first method invokes the second method with null third argument.

## Example - Registering and Firing Events ##

```
package vooga.core.event.tutorial;
import vooga.core.event.EventManager;
import vooga.core.event.IEventHandler;

public class Tutorial1
{
	private EventManager eventManager;

	public Tutorial1()
	{
		eventManager = new EventManager();
	}

	public void createEventHandler()
	{
		eventManager.registerEventHandler("Message", new IEventHandler()
		{
			@Override
			public void handleEvent(Object o)
			{
				sayHi();
			}
		});
	}

	public void sayHi()
	{
		System.out.println("Hi!");
	}

	public void doWork()
	{
		eventManager.fireEvent(this, "Message");
		eventManager.update(0);
	}

	public static void main(String[] args)
	{
		Tutorial1 t1 = new Tutorial1();
		t1.createEventHandler();
		t1.doWork();
	}
}
```

## The Event Layer Stack ##

Any given Game State is contained in its own Event Layer, connecting all the necessary events and external forces/inputs (e.g user interactions). This encapsulates a given state of the game, making it independent and modular, allowing the game to dynamically connect or disconnect from states during a state change.

The Event stack can be thought of as a Stack of individual Event Layer that represent the current state of the program. The Event Manager connects only to the top Layer, meaning that all the functionality of the game is defined by only the top state. However, it is conceivable that certain new states might want to inherit from previous states, or completely override the event handlers that are in the previous states. Rather than forcing the user to remake all the connections every time there is a state change, we have attempted to provide a more useable interface that assists in this context switch. This introduces the concept of Event Filters.

## Event Filters ##
In the Event Manager, there is a collection of Event Handlers that are active depending of what state of the game one is in, which is defined by the Event Layer as mentioned above. However, each Event Layer needs to know what events that it will inherit from the previous layer, and this is defined by a list of Event Filters. Event Filters behave like Matchers in Java Regular Expressions, where one will filter a given event String name based on a user-defined set of rules. The construction of the Event Layer takes a list of Event Filters, which upon construction will use this list to obtain the necessary handlers from the layer below.

### Remove-All Filter ###
A Blocking Filter is a type of Filter that inherits no event listeners from the previous state. It is a clean slate where the user must reconnect all the desired events.

Usage Example: A Pause screen might want to use this type of Filter when layering a new game state. In a pause screen, all motion of the game in stopped, and control in limited to selection in the menu screen. It is more convenient to redefine all the keymappings and mouseover events from a clean state.

### Keep-All Filter ###
On the other extreme, a Keep-All Filter is a type of Filter that inherits ALL event listeners from the previous state. It is a duplicate copy of the previous state where users might want to add additional functionality or override a few, not most of the the events.

Usage Example: A layered heads up display might use this type of Filter. In a game that uses the keyboard to control the player, one might want to add mouse support within the heads up display region where one could select items or weapons to use. This additional layer of mouse control is easier to add onto the existing state.

### Custom Filter ###
It is conceivable that one would want to remove a certain namespace of events. Using Globs, one can use wildcards and other modifiers to specify the globs to remove or keep. Refer to the wikipedia page on Globs for more information on their usage.

### Caveats ###
There might exist a temptation for a Game creator to abuse the creation of Event Layers states for some reason or another. Not every single state change in the game should deserve its own event Layer, as the true power of this setup comes with the fact that one can restore a previous state. If an enemy fires a bullet at the player, the new addition of a sprite and movement control should not deserve a whole new Event Layer. Instead, the individual event should be added to the loop and deactivated when the bullet collides and/or is destroyed.

In this sense, one must use his or her discretion when deciding when to create a new Event Layer. In most cases, an Event Layer should have a distinct new state that one would want to return to at a later point in time, for example, collecting a time dependent weapon powerup could warrant the a new state. Another example would be in game-freezing menus, for example, a pause screen, or a level complete screen. Do keep in mind that constant state changing could potentially be a costly operation, and that excessive and abusive use would lead to decreased performance of the game.

## Vooga Game Implementation ##
The Events API is currently aligned to serve two distinct groups of users. The Event Manager class contains several methods that dip into the core of the event manager, which grants the user direct access to the states that control the reactor engine. For an average game designer, incorrectly connecting components or making unjustified methods calls to the Event Manager might result in poor performance of the Engine. However, we cannot completely close this power down to the more experienced coders (more so groups in 108 that will be using the Events API). To cater to these two audiences, we have created the ISimpleEventManager interface, whose purpose is to narrow the interface to the Events Manager to cater to 99\% of the needs of game programmers.

VoogaGame, the entry point into using this entire Game Engine API, extends the original GoldenT engine, and has access to the Events System API through the ISimpleEventManager.

For those groups that are working directly with the EventManager class, please try to fully understand how the Event System works. Event driven systems tend to be extremely complex and have a decently steep learning curve. Please do not hesitate to ask questions or run concepts by Ethan or Michael, as this would benefit both your component, but also give us an idea of what aspects of this API could be improved.

## The Reactor Pattern ##
Reactor Pattern: http://en.wikipedia.org/wiki/Reactor_patternhttp://en.wikipedia.org/wiki/Reactor_pattern

# Key Takeaways #

  1. Events, Event Listeners, Event Handlers, Event Registration.
  1. Event Types
    1. Timed Events
    1. Basic handlers
    1. Every-Turn Events
  1. Event Layers
  1. Event Filters
    1. Keep-All Filter
    1. Remove-All Filter
    1. Custom Filters
  1. Registering and Unregistering Events
  1. Naming and Grouping Conventions