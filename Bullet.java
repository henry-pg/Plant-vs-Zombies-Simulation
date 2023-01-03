import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the Projectile that Crazy Dave shoots.
 * 
 * @author Ethan
 * @version April 2022
 */
public class Bullet extends Projectile
{
    /**
     * Constructor for Bullet. Sets the Bullet's speed and damage
     * 
     * @param rotation  Crazy Dave's rotation when he fires the Bullet. The Bullet
     *                  inherits this rotation so that it will move towards the Zombie that Crazy
     *                  Dave is facing.
     */
    public Bullet (double rotation)
    {
        // Set rotation, speed and damage
        this.setRotation(rotation);
        speed = 15;
        damage = 35;
    }
    
    /**
     * Act method for Bullet. Calls upon the Projectile superclass' act method. 
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Bullet's hitZombie method that does the same thing as the superclass'
     * method but does not play the splat sound effect.
     */
    public void hitZombie () 
    {
        // Does damage to the Zombie that it is touching
        Zombie z = (Zombie)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Zombie.class);
        z.takeDamage(damage);
        
        // Removes itself
        getWorld().removeObject(this);
        inWorld = false;
    }
}