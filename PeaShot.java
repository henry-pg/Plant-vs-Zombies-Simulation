import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * This is the Projectile that the Peashooter and Repeater shoots.
 * 
 * @author Ethan Lin
 * @version April 2022
 */
public class PeaShot extends Projectile
{
    /**
     * Constructor for the Peashooter's and Repeater Projectile.
     */
    public PeaShot () 
    {
        damage = 20;
        firstTime = false;
    }
 
    /**
     * Act method for PeaShot. Calls upon the Projectile superclass' act method.
     */
    public void act()
    {
        super.act();
    }
}