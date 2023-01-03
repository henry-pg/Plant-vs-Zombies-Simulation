import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Effect is a Greenfoot Actor that creates a world-wide event.
 * 
 * @author Mr. Cohen
 * @version April 2022
 */
public class Effect extends Actor
{
    protected int totalActs, actCounter;
    protected GreenfootImage image;   
    
    /**
     * Constructor for Effect
     */
    public Effect (int totalActs)
    {
        this.totalActs = totalActs;   
        actCounter = totalActs;
    }
    
    /**
     * Will count down the acts while changing the image transparency and removes object if the act counter reaches a certain number
     */
    public void act()
    {
        if (actCounter > 0)
        {
            actCounter--;
            if (actCounter < 60) // last second
            {
                image.setTransparency (actCounter * 2);
            }
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
}