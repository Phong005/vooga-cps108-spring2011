# Event System Tutorial: Lesson 4: Periodic Timer Events #

#### Author: Ethan Yong-Hui Goh ####
Welcome to Event System Tutorial Lesson 4! In this tutorial you will be learning how to register a Periodic Timer Event. This event is a type of event that will be scheduled and run every x-seconds (user specified).

This time, we will start off with Lesson 3 Code.

```
package vooga.core.event.tutorial;
import vooga.core.event.EventManager;
import vooga.core.event.IEventHandler;

public class Tutorial4
{
	private EventManager eventManager;

	public Tutorial4()
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

	public void oneShotTimer()
	{
		int timeInMilli = 1000;
		eventManager.addTimer("OneTime", timeInMilli, "Message");
	}
	
	public void sayHi()
	{
		System.out.println("Hi!");
	}

	public void doWork()
	{
		while(true)
			eventManager.update(0);
	}

	public static void main(String[] args)
	{
		Tutorial4 t1 = new Tutorial4();
		t1.createEventHandler();
		t1.oneShotTimer();
		t1.doWork();
	}
}
```

Just like between regular events and every turn events, the change from a One-Shot Timer to a Periodic-Timer is a single method call change - easy as 3.14159265358979323846264338....

### Registering the Periodic Timer ###
Go into oneShotTimer() and change the method name to periodicTimer() for consistency. Make sure you change it in the main method (or use Eclipse refactor->rename feature). Change eventManager.addTimer() to eventManager.addPeriodicTimer(). Keep the parameters the same.

```
	public void periodicTimer()
	{
		int timeInMilli = 1000;
		eventManager.addPeriodicTimer("OneTime", timeInMilli, "Message");
	}
```

Simple! "Hi!" is printed every second!