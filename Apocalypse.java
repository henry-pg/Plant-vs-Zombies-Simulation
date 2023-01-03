import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Apocalypse is a Greenfoot Actor that sets a world-wide event. It will slowly disappear over the course of a few seconds. Apocalypse makes zombies spawn at a quickened pace and other than the fog, another indication is the flag zombie on the top right corner.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class Apocalypse extends Effect
{
    private boolean firstTime;
     
    /**
     * Initializes Apocalypse's values
     */
    public Apocalypse()
    {
        super(540);
        this.getImage().setTransparency(180);
        firstTime = true;
    }
    
    /**
     * Spawns in flag indication along with reducing the transparency over time. Will remove from world (and set apocalypse to false after the duration has ended).
     */
    public void act()
    {
        // Spawns in the flag indication.
        if (firstTime)
        {
            ZombieWorld z = (ZombieWorld)getWorld();
            z.addObject (new Flag(), 700, 50);
        }
        
        // Reduces transparency over time.
        if (actCounter > 0)
        {
            actCounter--;
            if (actCounter % 3 == 0)
            {
                this.getImage().setTransparency(getImage().getTransparency() - 1);
            }
        }
        // Sets apocalypse to false after the duration has ended
        else
        {
            ZombieWorld z = (ZombieWorld)getWorld();
            z.setApocalypse(false);
            z.removeObject(this);
        }
    }
}