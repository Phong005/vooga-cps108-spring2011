package vooga.collisions.collisionManager;

import java.util.ArrayList;


import vooga.collisions.intersections.IntersectionFactory;
import vooga.collisions.shapes.ShapeFactory;
import vooga.collisions.shapes.collisionShapes.BoundingBox;
import vooga.collisions.shapes.collisionShapes.ICollisionShape;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritegroups.SpriteGroup;

public abstract class BasicCollisionGroup<T extends Sprite, S extends Sprite> extends CollisionManager<T,S>
{
	
	public BasicCollisionGroup() {
		super();
	}


	public BasicCollisionGroup(SpriteGroup<T> s1, SpriteGroup<S> s2) {
		super(s1, s2);
	}

	private ShapeFactory shapeFactory = new ShapeFactory();
	
	public boolean pixelPerfectCollision;
	
	/** ************************************************************************* */
    /** ****************** MAIN-METHOD: CHECKING COLLISION ********************** */
    /** ************************************************************************* */
    
    public void checkCollision() {
        for(T s1: Group1.getSprites()){
            for (S s2: Group2.getSprites()){
            	if(s1.isActive() && s2.isActive() && this.areCollide(s1, s2) ){
                    this.collided(s1, s2);
                }
            }
        }
    }
    

    /**
     * Performs collision check between Sprite <code>s1</code> and Sprite
     * <code>s2</code>, and returns true if the sprites (<code>shape1</code>,
     * <code>shape2</code>) is collided.
     * <p>
     * 
     * Note: this method do not check active state of the sprites.
     * 
     * @param s1 sprite from group 1
     * @param s2 sprite from group 2
     * @param shape1 bounding box of sprite 1
     * @param shape2 bounding box of sprite 2
     * @return true, if the sprites is collided one another.
     */
    public boolean areCollide(T s1, S s2){
        if (!this.pixelPerfectCollision) {
            return (IntersectionFactory.areIntersecting(s1.getCollisionShape(), s2.getCollisionShape()));
            
        }
        else {
            if (IntersectionFactory.areIntersecting(s1.getCollisionShape(), s2.getCollisionShape())) {
                return CollisionManager.isPixelCollide(s1.getX(), s1.getY(), s1
                        .getImage(), s2.getX(), s2.getY(), s2.getImage());
            }
            
            return false;
        }
    }
    
    /**
     * Get the intersection angle between two points.
     */
    public double getIntersectionAngle(T s1, S s2) {
    	return IntersectionFactory.getIntersectionAngle(s1.getCollisionShape(), s2.getCollisionShape());
    }
    
    /**
     * Notified when <code>sprite1</code> from group 1 collided with
     * <code>sprite2</code> from group 2.
     * 
     * @param s1 sprite from group 1
     * @param s2 sprite from group 2
     */
    public abstract void collided(T s1, S s2);
    
}
