import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * CrazyDot is a Greenfoot Actor that will hide under CrazyDave. It checks if it is touching a zombie to create a nicer hitbox for CrazyDave (to check if he gets hit by a zombie)
 * 
 * @author Johnathan
 * @version April 2022
 */
public class CrazyDot extends Actor
{
    /**
     * Checks if it is touching a zombie and if CrazyDave is still alive. If true, it will set CrazyDave's touchingZombie to true.
     */
    public void act()
    {
        if (isTouching(Zombie.class) && ((CrazyDave)(getOneIntersectingObject(CrazyDave.class))).getAlive())
        {
            ((CrazyDave)(getOneIntersectingObject(CrazyDave.class))).setTouchingZombie(true);
        }
    }
}