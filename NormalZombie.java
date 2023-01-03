import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Zombie super class. NormalZombie is a Greenfoot Actor that is a default zombie.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class NormalZombie extends Zombie
{
    private int rareHeadless;
    private boolean headless;
    
    /**
     * Constructor for NormalZombie's values, has a chance to be a HeadlessZombie (same stats but different looks)
     */
    public NormalZombie()
    {
        super(0.5, 125, 25);
        rareHeadless = Greenfoot.getRandomNumber(20);
        headless = false;
        if (rareHeadless == 19)
        {
            headless = true;
            setImage("headlesszombie.png");
        }
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
        if (headless)
        {
            return "headlesszombie";
        }
        else
        {
            return "normalzombie";
        }
    }
    
    /**
     * Checks if its health is below or equal to 0 and will remove the 
     * zombie from the world if it fulfills the requirement.
     */
    public void die()
    {
        if (health <= 0)
        {
            if (!headless)
            {
                ZombieWorld z = (ZombieWorld)getWorld();
                z.addObject (new MoreDeadNormal(), getX(), getY() + 20);
                alive = false;
                getWorld().removeObject(this);
            }
            else
            {
                ZombieWorld z = (ZombieWorld)getWorld();
                z.addObject (new MoreDeadHeadless(), getX(), getY() + 20);
                alive = false;
                getWorld().removeObject(this);
            }
        }
    }
}