package games.pacman;

import games.pacman.sprites.players.PacMan;
import games.pacman.sprites.players.enemies.Chaser;

import java.awt.Dimension;
import vooga.core.VoogaGame;
import vooga.core.event.EventManager;
import vooga.core.event.IEventHandler;
import vooga.resources.images.ImageLoader;
import vooga.sprites.spritebuilder.components.collisions.CollisionCircleC;
import vooga.sprites.spritegroups.SpriteGroup;

/**
 * Chase food while avoiding ghost(s).
 * 
 * @author DJ Sharkey
 *
 */
public class PacManGame extends VoogaGame
{
    {distribute = true;}
    
    /*
     * We need these global variables because the level loader doesn't allow
     * sprites to access the game class. 
     */
    public static EventManager eventManager;
    public static ImageLoader imageLoader;
    
    public static void main (String[] args)
    {
        launchGame(new PacManGame(), new Dimension(48*15, 10*48), false);
    }

    @Override
    public void updatePlayField (long elapsedTime) {}

    @Override
    public void initResources ()
    {
        eventManager = getEventManager();
        imageLoader = getImageLoader();
        
        PacMan pacman = new PacMan(this, getWidth()/2, getHeight()/2);
        
        getLevelManager().addPlayer(new SpriteGroup<PacMan>("pacman", pacman));
        Chaser chaser = new Chaser(this.getImageLoader().getImage("dumbie"), 0,0,pacman);
        getLevelManager().addPlayer(new SpriteGroup<Chaser>("chaser", chaser));
        getLevelManager().loadLevel(0);

        eventManager.registerEventHandler("GameOver", new IEventHandler()
        {
            @Override
            public void handleEvent (Object o)
            {
            	finish();
            }    
        });
    }

}
