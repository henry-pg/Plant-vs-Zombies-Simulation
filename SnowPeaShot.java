import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * This is the Projectile that the Snowpea shoots.
 * 
 * @author Ethan
 * @version April 2022
 */
public class SnowPeaShot extends Projectile
{
    /**
     * Constructor for the SnowPeaShot. Sets the damage.
     */
    public SnowPeaShot () 
    {
        damage = 10;
        firstTime = false;
    }
 
    /**
     * Act method for SnowPeaShot. Calls upon the Projectile superclass' act method.
     */
    public void act()
    {
        super.act();
    }
 
    /**
     * Method to hit a zombie and deal damage to it.
     * Detects a zombie and does damage to it using the takeDamage method.
     * Once it hits the zombie, it removes itself from the world. Also calls
     * upon the slowMeDown method in the Zombie class to slow the Zombie down.
     * 
     */
    public void hitZombie () 
    {
        // Does damage to the Zombie that it is touching
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        z.takeDamage(damage);
        
        // Slows the Zombie down
        z.slowMeDown();
        
        //Removes itself
        getWorld().removeObject(this);
        inWorld = false;
        
        //Plays the splat sound effect
        playSplat();
    }
}