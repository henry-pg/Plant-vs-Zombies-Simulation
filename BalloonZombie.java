import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * BalloonZombie is a Greenfoot Actor that changes between 2 lanes. If it gets hit while he has a balloon, he will not take damage, but instead lose his balloon instead, grounding him to walk on the lane he is on.
 * 
 * @author Johnathan
 * @version April 2022
 */
public class BalloonZombie extends Zombie
{
    private int yOffset = 35;
    private int yOfLane;
    private boolean awayFromCenter = false;
    private boolean firstSpawnedIn = true;
    private boolean goUp;
    private boolean haveBalloon = true;
    private int laneNumber = 0;
    private GreenfootSound pop;
 
    /**
     * Constructor for BalloonZombie's values
     */
    public BalloonZombie()
    {
        super(1, 150, 25);
        if (Greenfoot.getRandomNumber(2) == 1)
        {
            goUp = true;            
        }
        else
        {
            goUp = false;
        }
        
        pop = new GreenfootSound ("pop.wav");
        pop.setVolume(100);
    }
 
    /**
     * Will bob up and down 2 lanes, and has a balloon that acts as a shield 
     * for the first shot. The balloon will pop afterwards and will cause the 
     * zombie to stop bobbing and start walking.
     */
    public void act()
    {
        if(firstSpawnedIn)
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
        
        if (getX() <= 200 && haveBalloon)
        {
            setImage("balloonlessnew.png");
            haveBalloon = false;
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
        if(getBalloon())
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
        else if(!getBalloon())
        {
            if (actCounter % 15 == 0)
            {
                if (rotateRight)
                {
                    setRotation(10);
                    rotateRight = false;
                }
                else
                {
                    setRotation(-10);
                    rotateRight = true;
                }
            }
            setLocation (getX() - speed, yOfLane);
        }
    }
 
    /**
     * Zombie will take damage or lose balloon
     * 
     * @param damage    The amount of health it may lose (if it has no balloon)
     */
    public void takeDamage (int damage)
    {
        if (alive && health == maxHealth && haveBalloon)
        {
            setImage("balloonlesszombie.png");
            setLocation (getX(), getY() + 5);
            pop.play();
            haveBalloon = false;
        }
        else if (!haveBalloon && alive)
        {
            health -= damage;
        }
    }
 
    /**
     * Returns the balloon state
     * 
     * @return boolean      Returns true if it has a balloon and false if not.
     */
    public boolean getBalloon()
    {
        if(!haveBalloon)
        {
            return false;
        }
        return true;
    }
 
    /**
     * Chews the plant. It will rock back and forth almost like it is taking a big munch every time. The zombie will cause the plant's health to diminish. When it has a balloon, it will lower itself when chewing to prevent it's hitboxes from going to the next lane.
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
                if (haveBalloon)
                {
                    // displaces the zombie to prevent the hitbox from going to another lane
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
                else
                {
                    if (!chewRotationLeft)
                    {
                        setRotation(-25);
                        plant.munched (chewDamage);
                        chewRotationLeft = true;
                    }
                    else
                    {
                        setRotation(0);
                        chewRotationLeft = false;
                    }
                }
            }
        }
        else
        {
            if (actCounter % 25 == 0)
            {
                setRotation (0);
                if (haveBalloon)
                {
                    // displaces the zombie to prevent the hitbox from going to another lane
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
                else
                {
                    if (!chewRotationLeft)
                    {
                        setRotation(-25);
                        plant.munched (chewDamage);
                        chewRotationLeft = true;
                    }
                    else
                    {
                        setRotation(0);
                        chewRotationLeft = false;
                    }
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
        if (haveBalloon)
        {
            return "balloonzombie";
        }
        else
        {
            return "balloonlessnew";
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
            ZombieWorld z = (ZombieWorld)getWorld();
            z.addObject (new MoreDeadBalloonless(), getX(), getY()+20);
            alive = false;
            getWorld().removeObject(this);
        }
    }
}