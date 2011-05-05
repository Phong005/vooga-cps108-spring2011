package games.jumper.collisions;

import games.jumper.sprites.Avatar;
import games.jumper.sprites.Block;
import games.jumper.sprites.Door;
import vooga.collisions.collisionManager.BasicCollisionGroup;
import vooga.resources.Direction;

public class AvatarToBlock extends BasicCollisionGroup<Avatar, Block >{

   
	@Override
	public void collided(Avatar avatar, Block block) {
		avatar.stopMoving();
	}


	
	
	
}