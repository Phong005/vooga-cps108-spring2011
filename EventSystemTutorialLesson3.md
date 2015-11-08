# Event System Tutorial: Lesson 3:  One-Shot Timer Events #

#### Author: Ethan Yong-Hui Goh ####
Welcome Back to Event System Tutorial Lesson 3! In this tutorial you will be learning how to register a One-Shot Timer Event. This event will be scheduled to be queued in the Event Manager a user-specified amount of time in the future.

Once again we'll start off with Tutorial 1 code from before as show below.

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

We first be making adjustments to the doWork() method and the main method.

In the doWork() method, remove the line `eventManager.fireEvent(this, "Message");`, and change it to `while(true)` (yes an infinite while loop).

In main(), after `t1.createEventHandler();` add a new call: `t1.oneShotTimer();`, which we will now create.

### Registering the One-Shot Timer ###
To register the one-shot timer, we will make the call addTimer(), which takes 3 parameters. The first parameter is the name of the Timer you want to register (keep this name in case you want to remove it before it is fired). The second parameter is the time in milliseconds later in the future that you want this event to be fired. Finally, the third paramter is the Event Name that you want to call. In this example, we registered an event named "Message" in createEventHandler, so we will just reuse that.

```
	public void oneShotTimer()
	{
		int timeInMilli = 1000;
		eventManager.addTimer("OneTime", timeInMilli, "Message");
	}
```

When we run the program, notice that the output is delayed by 1000 milliseconds = 1 second. Yipppppeeeeee~.