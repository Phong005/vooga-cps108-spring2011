# Event System Tutorial: Lesson 2: Every Turn Events #

#### Author: Ethan Yong-Hui Goh ####
Welcome Back to Event System Tutorial Lesson 2! In this tutorial you will be learning how to register
Every Turn Events, that is, events that will run as fast as they can, whenever the Event Manager gets a chance.

Lets start off with Tutorial 1 code from before, show below for convenience.
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

### Registering the every turn event ###
Registering the Every turn event is extremely simple. It is merely a simple method call change. Go into create Event Handler and change ".registerEventHandler" to ".addEveryTurnEvent". The parameters are exactly the same! Woot for consistent APIs!

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

Like before, the API call is made to EventManager.addEveryTurnEvent, which takes a String name for the Event to be called, and an inline declaration of an anonymous inner class of type IEventHandler. This second parameter contains the method that will be called whenever this particular event is fired.

Running the program, we see that the sayHi method gets called continuously, like an infinite while loop! Hit Ctrl-C or the stop button to quit the program.