import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Includes all projectiles shot by plants, as well as Crazy Dave's bullets.
 * Projectiles travel until they either hit a zombie, deal damage to it, and 
 * remove themselves.
 * 
 * @author Ethan 
 * @version April 2022
 */
public class Projectile extends SuperSmoothMover
{
    // Declare variables
    protected int damage;
    protected int speed;
    protected boolean inWorld;
    protected static boolean gameOver = false;
    protected boolean firstTime;
    protected GreenfootSound splat[] = new GreenfootSound[20];
    protected int splatNumber;

    /**
     * This is the main constructor for Projectile - sets speed and sound effects.
     * It alse creates an array of sounds that the Projectile makes when it hits
     * a Zombie so that multiple Projectiles can make sounds at the same time.
     */
    public Projectile () 
    {
        // Set the nessecary variables
        speed = 4;
        inWorld = true;
        firstTime = false;
        
        // Populate the sound effect array
        for (int i=0; i<splat.length; i++)
        {
            splat[i] = new GreenfootSound ("splat.wav");
            splat[i].setVolume(70);
        }
        splatNumber = 0;
    }

    /**
     * Act method for Projectile. The Projectile moves horozontially at a
     * constant speed until it either hits a Zombie or exits the world. 
     */
    public void act() 
    {
        // Set the damage for each Projectile
        if (firstTime == false)
        {
            damage = (int)(damage *((ZombieWorld)(getWorld())).getPlantDamage());
            firstTime = true;
        }
        
        if (!gameOver)
        {
            // Moves until it either hits a zombie or exits the world
            move(speed);
            if(checkHitZombie())
            {
                hitZombie();
            }

            if (inWorld)
            {
                if (checkEdge())
                {
                    getWorld().removeObject(this);
                    inWorld = false;
                }
            }
        }
    }

    /**
     * Method to hit a zombie and deal damage to it.
     * Detects a zombie and does damage to it using the takeDamage method.
     * Once it hits the zombie, it removes itself from the world
     * 
     */
    public void hitZombie () 
    {
        // Does damage to the Zombie that it is touching
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        z.takeDamage(damage);
        
        //Removes itself
        getWorld().removeObject(this);
        inWorld = false;
        
        //Plays the splat sound effect
        playSplat();
    }

    /**
     * Checks if the Projectile has hit a Zombie.
     * 
     * @return boolean  True if the Projectile is touching a zombie, otherwise
     *                  false
     */
    public boolean checkHitZombie ()
    {
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        if (z != null) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    /**
     * Checks whether the Projectile is within the area of the world.
     * 
     * @return boolean  True if the Projectile is outside the bounds of the world,
     *                  false if the Projectile is still inside the world
     */
    public boolean checkEdge ()
    {
        if (this.getX() >= 749 || this.getY() <= 1 || this.getY() >= 447)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Plays the splat sound effect when a Projectile hits a Zombie.
     */
    public void playSplat()
    {
        splat[splatNumber].play();
        splatNumber++;
        if (splatNumber >= splat.length)
        {
            splatNumber = 0;
        }
    }
    
    /**
     * Sets the gameOver boolean to true.
     */
    public static void gameOverTrue()
    {
        gameOver = true;
    }
    
    /**
     * Sets the gameOver boolean to false.
     */
    public static void gameOverFalse()
    {
        gameOver = false;
    }
    
}