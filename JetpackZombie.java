import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Refer to the Zombie super class. JetpackZombie is a Greenfoot Actor that changes between 2 lanes. .
 * 
 * @author Johnathan
 * @version April 2022
 */
public class JetpackZombie extends Zombie
{
    private int yOffset = 35;
    private int yOfLane;
    private boolean awayFromCenter = false;
    private boolean firstSpawnedIn = true;
    private boolean goUp;
    private int laneNumber = 0;
    
    /**
     * Constructor for JetpackZombie's values
     */
    public JetpackZombie()
    {
        super(1.5, 200, 25);
        if (Greenfoot.getRandomNumber(2) == 1)
        {
            goUp = true;            
        }
        else
        {
            goUp = false;
        }
    }
 
    /**
     * Will bob up and down 2 lanes and is faster than a BalloonZombie. Refer to the Zombie act method.
     */
    public void act()
    {
        if (firstSpawnedIn)
        {
            yOfLane = getY();
            if (yOfLane == 100)
            {
                laneNumber = 1;
                goUp = false;
            }
            else if (yOfLane == 172)
            {
                laneNumber = 2;
            }
            else if (yOfLane == 245)
            {
                laneNumber = 3;
            }
            else if (yOfLane == 320)
            {
                laneNumber = 4;
            }
            else if (yOfLane == 385)
            {
                laneNumber = 5;
                goUp = true;
            }
        }
 
        super.act();
    }
 
    /**
     * Changes position between 2 lanes if it has a balloon, otherwise it will walk as a normal zombie.
     * 
     * @param speed     The speed of which the zombie will travel
     */
    public void walk (double speed)
    {
        if (actCounter % 25 == 0)
        {
            if(!firstSpawnedIn)
            {
                if(goUp)
                {
                    setLocation (getX() - speed, getY() - yOffset);
                    goUp = false;
                }
                else
                {
                    setLocation (getX() - speed, getY() + yOffset);
                    goUp = true;
                }
                firstSpawnedIn = true;
            }
 
            if(goUp)
            {
                setLocation (getX() - speed, getY() - 2 * yOffset);
                goUp = false;
            }
            else
            {
                setLocation (getX() - speed, getY() + 2 * yOffset);
                goUp = true;
            }
        }
        setLocation (getX() - speed, getY());
    }
    
    /**
     * Chews the plant. It will rock back and forth almost like it is taking a big munch every time. The zombie will cause the plant's health to diminish. It will lower itself when chewing to prevent it's hitboxes from going to the next lane.
     * 
     * @param plant         The plant that will be taking the damage
     * @param chewDamage    The amount of damage per chew
     */
    public void chew (Plant plant, int chewDamage)
    {
        if (!snowPeaSlowed)
        {
            if (actCounter % 15 == 0)
            {
                setRotation (0);
                if (!chewRotationLeft)
                {
                    setLocation (getX(), getY() + 15);
                    setRotation(-25);
                    plant.munched (chewDamage);
                    chewRotationLeft = true;
                }
                else
                {
                    setLocation (getX(), getY() - 15);
                    setRotation(0);
                    chewRotationLeft = false;
                }
            }
        }
        else
        {
            if (actCounter % 25 == 0)
            {
                setRotation (0);
                if (!chewRotationLeft)
                {
                    setLocation (getX(), getY() + 15);
                    setRotation(-25);
                    plant.munched (chewDamage);
                    chewRotationLeft = true;
                }
                else
                {
                    setLocation (getX(), getY() - 15);
                    setRotation(0);
                    chewRotationLeft = false;
                }
            }
        }
    }
    
    /**
     * Will return the type of zombie
     * 
     * @return String   returns the type of the zombie 
     */
    public String getName()
    {
        return "jetpackzombie";
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
            z.addObject (new MoreDeadJetpack(), getX(), getY() + 25);
            alive = false;
            getWorld().removeObject(this);
        }
    }
}