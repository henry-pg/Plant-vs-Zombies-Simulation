import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * An indication on whether the apocalypse event has spawned in
 * 
 * @author Johnathan 
 * @version April 2022
 */
public class Flag extends Actor
{
    /**
     * Will check if Apocalypse is in the world, if it is not, then it will remove itself from the world
     */
    public void act()
    {
        if (getWorld().getObjects(Apocalypse.class).size() < 1)
        {
            getWorld().removeObject(this);
        }
    }
}