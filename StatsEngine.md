## Introduction ##

Basically any kind of information can be statistics. For purpose of extensibility, there is an abstract class, _AbstractStat_, by which you can define your own statistical information for your game. Besides getting statistical information, there is a _cheat_ method for setting statistical information manually. When you extends _AbstractStat_ you must implement _update_ method and register it with event manager or _update_ method in game loop.

## Numerical Statistics ##

For any numerical statistics, such as scores, health, and number of monsters killed, _NumStat_ class is for usage. There are three necessary elements for each _NumStat_: initial value, step and operator. All of them must be defined before using _update_ method. In constructer, a _NumStat_ can be defined as following ways:

```
NumStat<T> num1 = new NumStat<T>(initialValue);
NumStat<T> num2 = new NumStat<T>(initialValue, step);
NumStat<T> num3 = new NumStat<T>(initialValue, step, operatorToken);
NumStat<T> num4 = new NumStat<T>(initialValue, step, operatorToken, operatorMap);
```

_operatorToken_ is a string that represents arithmetic operation. There are seven kinds of operations: **PLUS**, **MINUS**, **TIMES**, **DIVIDE**, **MIN**, **MAX**, and **REPLACE**. They are all predefined keywords. For example, if addition is needed in statistics, you can replace _operationToken_ with  **NumStat.PLUS**.

After defining all these parameters, all you need to do is to register update method with corresponding event or _update_ in game loop. No need to write your _update_ method here, only if you want to have your own arithmetic operation.

## Manage More Than One Numerical Statistics ##

The _NumDisplayCreator_ and _DisplayTracker_ are very useful tools for management of many numerical statistics. While _NumDisplayCreator_ reads statistical properties from xml file and store them in _DisplayTracer_, all stored statistics can be updated and read in _DisplayTracker_.
If we define score in a game as integers starting at zero with step 1000, and health for player starting at 100000 with step 1000, core of xml file is written as

```
<NumStat type="Integer" value="0" name="score" operator="+" step="1000"></NumStat>
<NumStat type="Double" value="100000" name="health" operator="-" step="1000"></NumStat>
```

By passing in the location of this xml, _NumStatCreator_ creates two instances of _NumStat_ containing integer and double for score and health, respectively. This statistical information is stored in a _DisplayTracker_ which can be returned from _NumStatCreator_. _NumStats_ are distinguished by their names in _DisplayTracker_. Now you can update all statistics and get them in your game.

## Example ##

Following example shows how to use stat package in a game loop. In this example, statistic of score is added 1000 while health is subtracted by 1000 whenever mouse is clicked.

```
public class TrickyGame extends Game{
	private NumDisplayCreator myCreator;
	private DisplayTracker myTracker;
	int x1 = 100;
	int x2 = 200;
	int y = 50;
	int old_y = y;
	
	public void initResources() {
        myCreator = new NumDisplayCreator("src/vooga/stats/resources/statsNumDisplay.xml");
        myTracker = myCreator.getTracker();
    }

    public void update(long elapsedTime) {
        if (click())
        {
        	((NumStat) myTracker.getStat("score")).update();
        	((NumStat) myTracker.getStat("health")).update();
        	old_y = y;
        	y += 20;
        }
        
    }

    public void render(Graphics2D g) {
    	if (y!=old_y)
    	{
    		g.drawString(myTracker.getStat("score").toString(), x1, y);
    		g.drawString(myTracker.getStat("health").toString(), x2, y);
    	}
    }
}
```

The corresponding xml file (statsNumDisplay.xml) would be as following.

```
<NumDisplay>

<NumStat type="Integer" value="0" name="score" operator="+" step="1000"></NumStat>
<NumStat type="Double" value="100000" name="health" operator="-" step="1000"></NumStat>

<DisplayGroup name="DisplayGroup">
<DisplayString x="90" y="100" fontName="Times New Roman" label="Health: " fontSize="36"></DisplayString>
<DisplayBar x="100" y="100" max="100" stat="health" color="red" backColor="white"></DisplayBar>
</DisplayGroup>

</NumDisplay>
```