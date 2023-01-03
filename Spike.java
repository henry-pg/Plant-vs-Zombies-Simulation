import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;

/**
 * This is the Projectile that the Cactus shoots.
 * 
 * @author Ethan, Johnathan
 * @version April 2022
 */
public class Spike extends Projectile
{
    // Declare variables and array
    private int zombieCounter;
    private Zombie[] zombiesHit = new Zombie[3];
    private int size;

    /**
     * Constructor for the Spike. Sets the damage, speed, and zombie counter variables.
     */
    public Spike ()
    {
        speed = 8;
        damage = 10;
        firstTime = false;
        size = 0;
        zombieCounter = 0;
    }

    /**
     * Act method for Spike. Calls upon the Projectile superclass' act method.
     */
    public void act()
    {
        super.act();
    }

    /**
     * Method to hit a zombie and deal damage to it.
     * Detects a zombie and does damage to it using the takeDamage method.
     * Does not remove itself until it has hit three Zombies.
     */
    public void hitZombie()
    {
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        // If the Spike hasn't hit three Zombies yet, it does not remove itself.
        if (zombieCounter < 3 && !gotHit(z))
        {
            z.takeDamage(damage);
            zombieCounter ++;
        } 
        // If the Spike hits its third Zombie, it removes itself from the world.
        else if (zombieCounter == 3) 
        {
            z.takeDamage(damage);
            inWorld = false;
            getWorld().removeObject(this);
        }
    }

    /**
     * Method to check if a Zombie has been hit in order to prevent the same 
     * Zombie from being hit multiple times.
     * 
     * @param z         The Zombie that is being checked
     * @return boolean  Returns true if the Zombie has already been hit. Returns
     *                  false if the Zombie has not been hit yet.
     */
    public boolean gotHit (Zombie z)
    {
        if (zombiesHit==null||zombiesHit.length > 0)
        {
            // If there is nothing in the zombiesHit array, add z to the array
            if (zombiesHit[0] == null)
            {
                zombiesHit[0] = z;
                size = 1;
                return false;
            }
            
            // Runs through each Zombie in the array and returns true if any of
            // them match z.
            for (int i = 0; i < size; i++)
            {
                if (zombiesHit[i].equals(z))
                {
                    return true;
                }
            }
            
            // Add z to the array
            if (zombiesHit[1] == null)
            {
                zombiesHit[1] = z;
                size = 2;
            }
            else if (zombiesHit[2] == null)
            {
                zombiesHit[2] = z;
                size = 3;
            }
        }
        return false;
    }
}