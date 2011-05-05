package games.patterson_game.collisions;

import games.patterson_game.AbstractFloatingObject;
import games.patterson_game.AbstractWeapon;
import games.patterson_game.refactoredVooga.collisions.collisionManager.BasicCollisionGroup;
import games.patterson_game.refactoredVooga.util.buildable.components.predefined.basic.HealthC;

/**
 * Performs damage a floating object when hit by a weapon
 * @author Andrew
 *
 */
public class WeaponFloatingObjectCollision extends BasicCollisionGroup<AbstractWeapon, AbstractFloatingObject>
{
    @Override
    public void collided (AbstractWeapon weapon, AbstractFloatingObject floatingObject)
    {
        double weaponStrength = weapon.getComponent(HealthC.class).getCurrent();
        floatingObject.takeDamage(weaponStrength);
        weapon.setActive(false);
    }
}
