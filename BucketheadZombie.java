import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Zombie super class. BucketheadZombie is a Greenfoot Actor that is a tanky zombie.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class BucketheadZombie extends Zombie
{
    /**
     * Constructor for BucketheadZombie's values
     */
    public BucketheadZombie()
    {
        super(0.5, 270, 25);
    }
    
    /**
     * Refer to the Zombie act method.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public String getName()
    {
        return "bucketheadzombie";
    }
    
    /**
     * Checks if its health is below or equal to 0 and will remove the 
     * zombie from the world if it fulfills the requirement.
     */
    public void die()
    {
        if (health <= 0)
        {
            ZombieWorld z = (ZombieWorld)getWorld();
            z.addObject (new MoreDeadBuckethead(), getX(), getY() + 20);
            alive = false;
            getWorld().removeObject(this);
        }
    }
}