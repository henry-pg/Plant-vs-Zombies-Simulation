import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Zombie super class. DancerZombie is a Greenfoot Actor that is a weak zombie. It spawns from the DiscoZombie's groovy dance.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class DancerZombie extends Zombie
{
    /**
     * Constructor for DancerZombie's values
     */
    public DancerZombie()
    {
        super(0.75, 45, 25);
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
        return "dancerzombie";
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
            z.addObject (new MoreDeadDancer(), getX(), getY() + 20);
            alive = false;
            getWorld().removeObject(this);
        }
    }
}