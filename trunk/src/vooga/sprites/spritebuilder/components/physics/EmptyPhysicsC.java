package vooga.sprites.spritebuilder.components.physics;

import java.awt.Point;
import java.util.List;
import vooga.physics.VoogaPhysicsMediator;
import vooga.physics.collisionBehavior.EmptyCollisionBehavior;
import vooga.physics.fieldBehavior.EmptyFieldBehavior;
import vooga.physics.forceBehavior.EmptyForceBehavior;
import vooga.physics.newtonianProperties.INewtonianPhysical;
import vooga.physics.util.Force;
import vooga.physics.util.IPhysicsToggle;
import vooga.physics.util.VectorField;
import vooga.physics.util.Velocity;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.improvedsprites.interfaces.ISpriteUpdater;
import vooga.util.buildable.components.BasicComponent;
import vooga.util.math.Angle;

/**
 * Physics Component of a Sprite.
 * 
 * @author Nathan Klug
 * 
 */
public class EmptyPhysicsC extends BasicComponent implements ISpriteUpdater, IPhysicsToggle{

    private boolean isOn;
    private Velocity deltaVelocity;
    
    private EmptyForceBehavior forceBehavior;
    private EmptyFieldBehavior fieldBehavior;
    private EmptyCollisionBehavior collisionBehavior;

    public EmptyPhysicsC() {
        this(true);
    }

    public EmptyPhysicsC(boolean isOn) {
        this.isOn = isOn;
        forceBehavior = new EmptyForceBehavior();
        fieldBehavior = new EmptyFieldBehavior();
        collisionBehavior = new EmptyCollisionBehavior();
    }

    @Override
    protected int compareTo(BasicComponent o) {
        // TODO: do we use this to compare whether a component is more specific.
        // for physics than another
        return 0;
    }

    @Override
    protected Object[] getFieldValues() {
        return new Object[] {forceBehavior, fieldBehavior, collisionBehavior, isOn()}; // TODO: this is not
                                                          // going to return the
                                                          // field values, just
                                                          // the fields
    }

    @Override
    protected void setFieldValues(Object... fields) {
        forceBehavior = (EmptyForceBehavior) fields[0];
        fieldBehavior = (EmptyFieldBehavior) fields[1];
        collisionBehavior = (EmptyCollisionBehavior) fields[2];
        if (fields.length > 3)
            turnPhysicsOnOff((Boolean) fields[3]);
        else
            turnPhysicsOnOff(true);
    }

    public void applyForce(Force force, long lengthOfApplication){
        deltaVelocity.addVector(forceBehavior.forceToVelocityChange(force, lengthOfApplication));
    }
    
    public void applyForces(List<Force> forces, long lengthOfApplication) {
        for (Force force : forces) {
            applyForce(force, lengthOfApplication);
        }
    }
    
    public void applyField(VectorField field, long lengthOfApplication){
        deltaVelocity.addVector(fieldBehavior.fieldToVelocityChange(field, lengthOfApplication));
    }
    
    public void applyFields(List<VectorField> fields, long lengthOfApplication){
        for (VectorField field : fields){
            applyField(field, lengthOfApplication);
        }
    }
    
    public void applyCollision(EmptyCollisionBehavior otherCollisionBehavior, Angle angleOfImpact, Point pointOfImpact, double coefficientOfRestitution){
        collisionBehavior.collisionToVelocityChange(otherCollisionBehavior, angleOfImpact, pointOfImpact, coefficientOfRestitution);
    }

    @Override
    public void update(Sprite s, long elapsedTime) {
        Velocity oldVelocity = getSpriteVelocityForPhysics(s);
        oldVelocity.addVector(deltaVelocity);
        setSpriteVelocityForPhysics(s, oldVelocity);
        deltaVelocity = new Velocity(0,0);
    }
    
    public Velocity getSpriteVelocityForPhysics(Sprite sprite){
        return new Velocity(sprite.getHorizontalSpeed(), -sprite.getVerticalSpeed());
    }
    
    public void setSpriteVelocityForPhysics(Sprite sprite, Velocity newVelocity){
        sprite.setHorizontalSpeed(newVelocity.getXComponent());
        sprite.setVerticalSpeed(-newVelocity.getYComponent());
    }

    @Override
    public void turnPhysicsOnOff(boolean isOn) {
        this.isOn = isOn;
        
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
