package games.patterson_game.refactoredVooga.sprites.spritebuilder.builder;

import games.patterson_game.refactoredVooga.sprites.improvedsprites.Sprite;
import games.patterson_game.refactoredVooga.util.buildable.BuildException;
import games.patterson_game.refactoredVooga.util.buildable.components.ComponentResources;
import games.patterson_game.refactoredVooga.util.buildable.components.IComponent;
import games.patterson_game.refactoredVooga.util.buildable.components.predefined.basic.IntegerC;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class SpriteBuilder<T extends Sprite>
{

    
    private TreeSet<Class<? extends IComponent>> myConstructors;
    
    public SpriteBuilder(Class<? extends IComponent>...classes){
        myConstructors = new TreeSet<Class<? extends IComponent>>(Arrays.asList(classes));
    }
    
    public T buildSprite(T spriteBase, Object ...in){
        Iterator<Class<? extends IComponent>> iter1 = myConstructors.iterator();
        Queue<Object> input = new LinkedList<Object>(Arrays.asList(in));

        while(iter1.hasNext()){
            if (!iter1.hasNext())
                throw new BuildException(BuildException.BAD_INPUT);
            
            try
            {
               Constructor<? extends IComponent> c = ComponentResources.findConstructor(iter1.next(), input); //find constructor
               Object[] args = getConstructorArgs(c, input); //get arguements for constructor
               spriteBase.addComponent((IComponent) c.newInstance(args));
            }
            catch (Exception e)
            {
                throw new BuildException(BuildException.BUILDING_ERROR);
            }
            
        }
        
        
        return spriteBase;
        
        
    }

   

    /**
     * Finds all of the objects required to fill the input constructor. If the constructor requires an 
     * object that is not present in the input, fills in a null.
     * @param c
     * @param input
     * @return
     * @throws BuildException
     */
    private Object[] getConstructorArgs (Constructor<? extends IComponent> c, Queue<Object> input) throws BuildException
    {

        Class[] params = c.getParameterTypes();
        ArrayList<Object> args = new  ArrayList<Object>();
        
        for (Class<?> p: params){
           System.out.println(input.peek().getClass() + " " + p);
            if (p.isInstance(input.peek()))
                args.add(input.poll());
            else
                args.add(null);
            
        }
        System.out.println(args.size());
        return args.toArray();
    }
    
    public static boolean carrySameComponents(Sprite s1, Sprite s2){
        return ComponentResources.carrySameComponent(s1.getComponents(), s2.getComponents());
    }

}
