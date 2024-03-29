package games.pacman.sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.golden.gamedev.util.ImageUtil;

import vooga.sprites.improvedsprites.Sprite;

public  class Wall extends Sprite{

	public Wall(BufferedImage image, int x, int y) {
		super(image,x*image.getWidth(),y*image.getHeight());
	}
	

	@Override
	public Double getArbitraryRotate() {
		return 0D;
	}
	
	@Override
		public void render(Graphics2D g,int x,int y) {
		AffineTransform aTransform = new AffineTransform();
	    aTransform.translate((int) this.getX() +width/2, 
	                         (int) this.getY()+height/2);
	    aTransform.rotate(Math.toRadians(this.getAngle()+90));
	    
	    aTransform.translate((int) -width/2, 
	                         (int) -height/2);
	   
	   
	    g.drawImage(ImageUtil.resize(image, width, height),aTransform,null);
	    super.renderComponents(g, x, y);
	    g.setColor(Color.WHITE);
	    this.getCollisionShape().render(g);
	    g.setColor(Color.BLACK);
	}
	
	
}
