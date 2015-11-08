# Game Flow #

**Motivation**:
To create “templates” that will make it much easier for game developers to create games. The goal is to create a working game with very little code.

**Approach**:
We are going to use events as “states”, meaning that when events are fired, it indicates that a new state has be reached. Thus by registering a set of functions with each “state transition”/events, game developer can have a running game by just implementing several functions.

**Example**:

(This is still very rough, but hopefully it will give you an idea how it will work.)
In a very simple game, the flow of the game may just be:

Slash screen -> start level -> cleanup/display score

Thus what the game flow team will provide is three empty methods for the game developer to fill out. The template will handle the registering of the event handlers and the event firing, and all the game developer needs to fill out is spashScreen(), levelInit(), and endLevel(). For a simple game, all it will take is three methods to make the game.

//TODO: Talk to each team to figure out what is the most general set of events/states that we can provide for the game developer. The game flow may not end up having a lot of code but hopefully we will have the right set of states/events that will make coding a game much easier for everyone.

//TODO: For all groups please  start thinking whether your system will benefit from this structure. We definitely welcome more feedback and suggestion so that we can make coding up games easier for everyone.

P.S. At this point we actually don’t have any code at all and this is because we are still trying to figure out a good set of events to fire. However, most groups should be able to continue development without knowing our implementation. Please let us know if you have any questions. We will try to keep updating this wiki as our template takes shape.

Peace
Game Flow team

