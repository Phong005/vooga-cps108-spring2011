# Event System Tutorial: Lesson 1: Basic Events #

#### Author: Ethan Yong-Hui Goh ####

Hi! Welcome to the Vooga Event System tutorial! This tutorial is meant to be an extremely user friendly document which will explain the use of the Events System through example. If the design document was too much to digest at once, here is another tutorial that may or may not help. Without much ado, lets get started!

### Event Basics...Again ###
Hopefully, by now, one understands the basic concepts of events. If not, think of events in terms of a cause and effect pair. The Event Trigger is the 'cause', and the Event Handler is the 'effect' (or response). Hopefully, you are familiar with the basics of Java, so we will jump right into how to set up things.

### Understanding the Event Manager ###
The event driven system is run entirely by the EventManager, which isessentially a master list of tasks to do. When one fires an event, the event adds itself to the EventManager queue where it waits to be processed.  Without and EventManager, the program cannot take advantage of any of the benefits of using events. Given the skeleton class below, we will first declare an instance of the EventManager.

```
package vooga.core.event.tutorial;
import vooga.core.event.EventManager;

public class Tutorial1
{
	private EventManager eventManager;

	public Tutorial1()
	{
		eventManager = new EventManager();
	}

	public static void main(String[] args)
	{
		Tutorial1 t1 = new Tutorial1();
	}
}
```

### Registering your first event ###
Now that we have an Event Manager, we will learn to register your first event, as shown in the code below.
```
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
```

The API call is made to EventManager.registerEventHandler, which takes a String name for the Event to be called, and an inline declaration of an anonymous inner class of type IEventHandler. This second parameter contains the method that will be called whenever this particular event is fired.

### Firing your first event ###
Firing and event queues a reqest to run this Event when the Event Manager gets a chance, and its a simple method call:
```
	public void doWork()
	{
		eventManager.fireEvent(this, "Message");
		eventManager.update(0);
	}
```

We will also tell the eventManager to process its requests in its queue, so we call update on it. The parameter 0 represents the time elapsed since the last update call, which may be useful for time-dependent events.

### Putting it together ###
Just dump these methods into the class above and call them in main to get:

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

Run the program, and voila! Hi!