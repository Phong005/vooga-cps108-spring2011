package games.patterson_game.refactoredVooga.sprites.spritebuilder.components.collisions;


import games.patterson_game.refactoredVooga.collisions.shapes.collisionShapes.ICollisionShape;
import games.patterson_game.refactoredVooga.sprites.improvedsprites.Sprite;
import games.patterson_game.refactoredVooga.sprites.improvedsprites.interfaces.IRotation;
import games.patterson_game.refactoredVooga.sprites.improvedsprites.interfaces.ISpriteUpdater;
import games.patterson_game.refactoredVooga.util.buildable.components.BasicComponent;

public class CollisionShapeC<T extends ICollisionShape> extends BasicComponent implements IRotation, ISpriteUpdater{

    private T myCollisionShape;

    public  T getCollisionShape() {
        return myCollisionShape;
    }


    public void setCollisionShape(T cs) {
        this.myCollisionShape = cs;
    }


    public CollisionShapeC(T cs){
        myCollisionShape = cs;
    }


    @Override
    protected int compareTo(BasicComponent o) {
        //nobody cares
        return 0;
    }

    @Override
    protected Object[] getFieldValues() {
        return new Object[]{myCollisionShape};
    }

    @Override
    protected void setFieldValues(Object... fields) {
        myCollisionShape = (T) fields[0];
    }


    @Override
    public Double setAngle(double angle) {
        return myCollisionShape.setAngle(angle);
    }


    @Override
    public Double getAngle() {
        return myCollisionShape.getAngle();
    }


    @Override
    public Double rotate(double dAngle) {
        return myCollisionShape.rotate(dAngle);
    }


    @Override
    public void update(Sprite s, long elapsedTime) {

        myCollisionShape.setAngle(s.getAngle()+90);
        myCollisionShape.move(s.getX()-s.getOldX(),s.getY()-s.getOldY());
    }

}
