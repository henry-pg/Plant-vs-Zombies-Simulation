import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MoreDeadZombie is a Greenfoot Actor that displays a dead version of a zombie. It will slowly disappear over the course of a few seconds.
 * 
 * @author Johnathan 
 * @version April 2022
 */
public abstract class MoreDeadZombie extends Actor
{
    protected SimpleTimer timer = new SimpleTimer();
    protected SimpleTimer transparencyTimer = new SimpleTimer();
    protected boolean timerStarted = false;
    protected boolean xSecondsPassed = false;
    protected int transparency = 255;
    
    /**
     * Will start a timer once and will slowly reduce the transparency over time. After 3 seconds, it will remove the object.
     */
    public void act()
    {
        // starts timer once
        if (!timerStarted)
        {
            timer.mark();
            transparencyTimer.mark();
            timerStarted = true;
        }
        
        // Every 12 milliseconds will reduce transparency by 1 
        if (transparencyTimer.millisElapsed() > 12)
        {
            transparencyTimer.mark();
            this.getImage().setTransparency(transparency - 1);
            transparency--;
        }
        
        // delete after 3 seconds
        if (timer.millisElapsed() > 3000)
        {
            getWorld().removeObject(this);
        }
    }
}