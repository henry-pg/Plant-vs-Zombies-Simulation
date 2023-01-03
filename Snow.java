import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This object is used to simulate snow for the Snowstorm object.
 * 
 * @author (Henry)
 * @version (April 2022)
 */
public class Snow extends Actor
{   
    /**
     * Act method that makes snow fall and removes itself if it is outside of the world.
     */
    public void act()
    {
        //makes the snow fall
        setLocation(getX(),getY()+2);
        
        //removes snow if it is outside of the visible world
        if (getY() > 440)
        {
            getWorld().removeObject(this);
        }
    }
}
