package vooga.collisions.collisionManager.boundaries;

import vooga.collisions.collisionManager.BasicCollisionGroup;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritegroups.SpriteGroup;
import vooga.collisions.collisionManager.boundaries.*;

public abstract class EdgeCollisionGroup extends BasicCollisionGroup<EdgeSprite, Sprite> {


	@Override
	public void setCollisionGroup(SpriteGroup<EdgeSprite> group1,
			SpriteGroup<Sprite> group2) {
		super.setCollisionGroup(new EdgeSpriteGroup("edgeGroup", group2.getBackground()), group2);
	}

	@Override
	public void collided(EdgeSprite s1, Sprite s2) {
		
		switch(s1.getEdgeID()){
			case EdgeSpriteGroup.RECTANGLE_TOP: collidedTop(s2);break;
			case EdgeSpriteGroup.RECTANGLE_RIGHT: collidedRight(s2);break;
			case EdgeSpriteGroup.RECTANGLE_LEFT: collidedLeft(s2);break;
			case EdgeSpriteGroup.RECTANGLE_BOTTOM: collidedBottom(s2);break;
		}
	}

	public abstract void collidedTop(Sprite s);
	public abstract void collidedRight(Sprite s);
	public abstract void collidedLeft(Sprite s);
	public abstract void collidedBottom(Sprite s);
	
	public EdgeCollisionGroup() {
		super();
	}

	public EdgeCollisionGroup(SpriteGroup<Sprite> s2) {
		super(new EdgeSpriteGroup("edgeGroup", s2.getBackground()), s2);
	}



}
