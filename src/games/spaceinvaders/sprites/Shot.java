package games.spaceinvaders.sprites;

import java.awt.image.BufferedImage;

public class Shot extends Sprites
{
//    private final int H_SPACE = 6;
//    private final int V_SPACE = 1;

    public Shot(BufferedImage image, int x, int y) 
    {
        super(image, x, y);
        setX(x - getWidth()/2);
        setY(y - getHeight());
        setAbsoluteSpeed(BULLET_SPEED);
    }

//    public Shot(BufferedImage image, int x, int y) 
//    {
//        this(image);
//        setX(x + H_SPACE);
//        setY(y - V_SPACE);
//    }
    
    public void die()
    {
        //visiable = false;
    }
}