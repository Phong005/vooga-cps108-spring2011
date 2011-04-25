package vooga.sprites.spritebuilder.components.physics;

import vooga.physics.collisionBehavior.EmptyCollisionBehavior;
import vooga.physics.collisionBehavior.PhysicalCollisionBehavior;
import vooga.physics.fieldBehavior.EmptyFieldBehavior;
import vooga.physics.fieldBehavior.NewtonianFieldBehavior;
import vooga.physics.forceBehavior.EmptyForceBehavior;
import vooga.physics.forceBehavior.NewtonianForceBehavior;
import vooga.physics.util.VectorField;
import vooga.physics.util.Velocity;
import vooga.sprites.improvedsprites.Sprite;
import vooga.util.buildable.components.BasicComponent;

/**
 * Newtonian Physics Component of a Sprite. Force behavior is Newtonian, 
 * Field behavior is Newtonian, and Collision behavior is Physical.
 * 
 * @author Anne Weng
 * @author Nathan Klug
 * 
 */
public class NewtonianPhysicsC extends EmptyPhysicsC{
    
    /**
     * Constructor has been made private to mask the parent constructor.
     */
    @SuppressWarnings("unused")
    private NewtonianPhysicsC() {
    }
    
    /**
     * Constructor has been made private to mask the parent constructor.
     */
    @SuppressWarnings("unused")
    private NewtonianPhysicsC(boolean isOn) {
    }

    /**
     * Creates a NewtonianPhysicsC with given velocity and mass and the default 'on' state.
     * @param velocity
     * @param mass
     */
    public NewtonianPhysicsC(Velocity velocity, double mass, VectorField field) {
        this(velocity, mass, field, true);
    }
    
    /**
     * Creates a NewtonianPhysicsC with given velocity, mass, and state.
     * @param velocity
     * @param mass
     * @param isOn
     */
    public NewtonianPhysicsC(Velocity velocity, double mass, VectorField field, boolean isOn) {
        this(new NewtonianForceBehavior(mass),new NewtonianFieldBehavior(field), new PhysicalCollisionBehavior(velocity, mass), isOn);
    }
    
    /**
     * Constructor has been made private to mask the parent constructor.
     */
    @SuppressWarnings("unused")
    private NewtonianPhysicsC(EmptyForceBehavior forceBehavior, EmptyFieldBehavior fieldBehavior, EmptyCollisionBehavior collisionBehavior){
        this(forceBehavior, fieldBehavior, collisionBehavior, true);
    }
    
    /**
     * Constructor has been made private to mask the parent constructor.
     */
    private NewtonianPhysicsC(EmptyForceBehavior forceBehavior, EmptyFieldBehavior fieldBehavior, EmptyCollisionBehavior collisionBehavior, boolean isOn){
        super(forceBehavior, fieldBehavior, collisionBehavior, isOn);
    }

    @Override
    protected int compareTo(BasicComponent o) {
        // TODO: do we use this to compare whether a component is more specific.
        // for physics than another
        return 0;
    }

    @Override
    public void update(Sprite s, long elapsedTime) {
        super.update(s, elapsedTime);
        //Updates the current velocity for the collision behavior
        super.myCollisionBehavior.updateBehavior(getSpriteVelocityForPhysics(s));
    }
}
