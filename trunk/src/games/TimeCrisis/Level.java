package games.TimeCrisis;

import java.util.Collection;
import vooga.core.VoogaGame;
import vooga.levels.AbstractLevel;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritegroups.SpriteGroup;

/**
 * @author Troy Ferrell
 * Dummy Level
 */
public class Level extends AbstractLevel
{
    public Level (Collection<SpriteGroup<Sprite>> players, VoogaGame game)
    {
        super(players, game);
    }

    @Override
    public void loadLevel ()
    {
    }
}
