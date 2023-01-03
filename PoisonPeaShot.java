import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * This is the Projectile that the Poisonpea shoots.
 * 
 * @author Ethan
 * @version April 2022
 */
public class PoisonPeaShot extends Projectile
{
    // Declare variables
    private int poisonDamage;
 
    /**
     * Constructor for the PoisonPeaShot. Sets the PoisonPeaShot's damage and 
     * the poison damage.
     */
    public PoisonPeaShot ()
    {
        // Set damage and poison damage
        damage = 10;
        poisonDamage = 5;
        firstTime = false;
    }
 
    /**
     * Act method for PoisonPeaShot. The PoisonPeaShot moves horozontially at a
     * constant speed until it either hits a Zombie or exits the world. It also
     * poisons the Zombie that it hits.
     */
    public void act()
    {
        move(speed);
        if(checkHitZombie())
        {
            // Poisons the Zombie when it hits.
            poisonZombie();
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
 
    /**
     * Method to poison zombies. Calls the poison method in the Zombie class.
     */
    public void poisonZombie()
    {
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        z.poison(poisonDamage);
    }
}