package vooga.sprites.spritebuilder.components;

import java.awt.Point;
import vooga.physics.interfaces.IPhysics;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritebuilder.components.basic.PhysicsC;
import vooga.util.math.Angle;

/**
 * TODO: Make sure this gets integrate with collisions
 * @author Nathan Klug
 *
 */
public interface ISpriteCollider extends IPhysics{

    public void collisionOccurred(PhysicsC physicsC, Angle angleOfImpact, Point pointOfCollision, double coefficientOfRestitution);
}