# Introduction #

In the VOOGA game engine, a sprite is (at the most basic level) a movable image. However, sprites can be used for many different purposes and are the essential components in designing and playing a game. Sprites serve as the core objects in any variety of game or gameplay that is in need of components beyond a frame and a background i.e. nearly every game!






# The Component System #

> The component system is, in essence, a system designed to allow dynamic exchange of qualities within an object that eliminates the need for a deep and convoluted class or interface hierarchy. The goal of the system is to allow for design of a small, shallow hierarchy that is modified at runtime by components that are placed into a buildable object. It allows for generation of a pseudo-hierarchy without restrictions such as single-class extension or having to re-implement various interface methods in only slightly different classes.

> ## I. What is a component? ##
Basically, a component is anything that implements the IComponent interface. This interface contributes essential activity to a given class, making it easily modifiable through simple use of reflection and retrieval by class names. Here is the code:

```
package vooga.util.buildable.components;


public interface IComponent extends Comparable<IComponent>
{

  
    public abstract void setTo (IComponent c);
    

    public abstract void setTo (Object ... args);


    public abstract boolean equals (IComponent c);

}
```

As you can see, there are 3 essential parts to being an IComponent -
  1. It must be able to have its qualities set by another component or by a list of generic objects
  1. It must implement an equals function
  1. It must implement a comparable function

Each of these can be defined component-specifically, allowing implementation of a wide range of comparative definition. Each of these methods is absolutely ESSENTIAL for the function of components, and must be defined appropriated in every component created.

> ## II. Component Set ##

Inside every buildable object is a component set:
```
package vooga.util.buildable.components;

public class ComponentSet<IComponent> extends TreeSet<IComponent>
{

    public ComponentSet ()
    {
        super(new Comparator<IComponent>()
        {

            @Override
            public int compare (IComponent o1, IComponent o2)
            {
                if (o1.getClass().equals(o2.getClass()))
                    return 0;
                
                return o1.getClass().toString().compareTo(o2.getClass().toString());
            }}
            );
    }


}
```

As you can see, this component set extends TreeSet and defines the comparator to be by the IComponent class names rather than their individual comparable implementations. Therefore, this set prevents multiple iterations of the same component from being present in a given buildable object at a given time.

This set carries all components within an object an you must access the members of this set to gain access to the qualities that each IComponent holds.

Adding components to anything using a component set is a as simple as:

```
  ComponentSet<IComponent> compSet = new ComponentSet<IComponent>();
  //Add a Name to this set of components.
  compSet.add(new NameC());
```

However, you will typically want to specify a buildable object with multiple different components while still maintaining the struction of that object for future use. In this case, you will need to use a Builder!



> ## III. Using a Component Builder ##

Component builders are essentially factories through which one can create and reuse a "Pseudo-Constructor" for the building of an object containing a ComponentSet.

Component Builders are designed to be initialized once, and then to have the same set of IComponents associated with their pseudo constructor, allowing replication of buildable objects with identical sets of components. Therefore a component builder could be thought of a as a factory into which you input initial blueprints, and subsequently just through in parameters and it uses those to create no buildable objects.

The constructor for a Component Builder looks like this:

```
    private TreeSet<Class<? extends IComponent>> myConstructors;
    
    public ComponentBuilder(Class<? extends IComponent>...classes){ //pass in a series of component class that will define what this Builder churns out

        myConstructors = new TreeSet<Class<? extends IComponent>>(Arrays.asList(classes));

    }
```

This creates a list of classes from which the second method in the Component Builder determines the constructors and such necessary for actually initializing each of the IComponents in the Pseudo-Constructor

Here is an example of the BuildSprite method from the VOOGA game engine. This is called each time you would want to make a new sprite of the type defined by this Component Builder:

```
public T buildSprite(T bs, Object ...in){
        Iterator<Class<? extends IComponent>> iter1 = myConstructors.iterator();
        Queue<Object> input = new LinkedList<Object>(Arrays.asList(in));

        while(iter1.hasNext()){
            if (!iter1.hasNext())
                throw new BuildException(BuildException.BAD_INPUT);
            
            try
            {
               Constructor<? extends IComponent> c = ComponentResources.findConstructor(iter1.next(), input); //find constructor
               Object[] args = getConstructorArgs(c, input); //get arguements for constructor
               bs.addComponent((IComponent) c.newInstance(args));
            }
            catch (Exception e)
            {
                throw new BuildException(BuildException.BUILDING_ERROR);
            }
            
        }
        
        
        return bs;
        
        
    }
```

The key methods are hidden here, the methods that find the constructor and determine which arguments to use, but you can see a couple important things:

  1. he buildable object you wish to create must have an instance passed into the builder...This cannot be a null object.
  1. he arguments passed into this method must be the order they are intended to be used to initialize the components in the pseudo-constructor.
  1. f the proper constructor is not found, or there are too few arguments, an error will be thrown

> ## IV. 	Designing your own components ##

# Sprite Hierarchy #

# Sprite Builder #

# Basic Walkthrough for sprite creation #

# Benefits of this system? #