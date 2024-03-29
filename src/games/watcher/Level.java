package games.watcher;

import java.util.Collection;
import vooga.core.VoogaGame;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritegroups.SpriteGroup;
import vooga.levels.AbstractLevel;

/**
 * Conrad
 *
 */
public class Level extends AbstractLevel
{
    public Level (Collection<SpriteGroup<Sprite>> players, VoogaGame game)
    {
        super(players, game);
    }

    /**
     * Load the level by loading all the available blocks.
     */
    @Override
    public void loadLevel ()
    {
        addAllSpritesFromPool();
        addBackground();
    }

}
